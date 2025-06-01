package com.mzhadan.app.diploma.ui.pages.project_info

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.diploma.crypto.AesUtils
import com.mzhadan.app.diploma.crypto.CryptoManager
import com.mzhadan.app.diploma.crypto.FileCryptoUtils
import com.mzhadan.app.diploma.crypto.RsaUtils
import com.mzhadan.app.network.models.files.FileResponse
import com.mzhadan.app.network.repository.files.FilesRepository
import com.mzhadan.app.network.repository.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ProjectInfoViewModel @Inject constructor(
    private val filesRepository: FilesRepository,
    private val prefsRepository: PrefsRepository
) : ViewModel() {

    private val _files = MutableLiveData<List<FileResponse>>()
    val files: LiveData<List<FileResponse>> = _files

    fun getFiles(projectId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = filesRepository.getFilesByProject(projectId)
            if (response.isSuccessful) {
                _files.postValue(response.body())
            }
        }
    }

    fun downloadFile(
        context: Context,
        fileId: Int,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = prefsRepository.getUID()
                if (userId != null) {
                    val response = filesRepository.downloadFile(fileId, userId)
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            val privateKey = CryptoManager.getPrivateKey(prefsRepository.getAlias()!!)
                            val encryptedAesKeyBytes = Base64.decode(body.encrypted_key, Base64.DEFAULT)
                            val aesKeyBytes = RsaUtils.decryptKey(encryptedAesKeyBytes, privateKey)
                            val aesKey = AesUtils.keyFromBytes(aesKeyBytes)
                            val encryptedFileBytes = Base64.decode(body.file_data, Base64.DEFAULT)
                            val iv = encryptedFileBytes.copyOfRange(0, 12)
                            val tag = encryptedFileBytes.copyOfRange(encryptedFileBytes.size - 16, encryptedFileBytes.size)
                            val ciphertext = encryptedFileBytes.copyOfRange(12, encryptedFileBytes.size - 16)
                            val outputFile = File(context.filesDir, "temp/${body.filename}")
                            outputFile.parentFile?.mkdirs()
                            FileCryptoUtils.decryptFile(outputFile, iv, ciphertext, tag, aesKey)
                            withContext(Dispatchers.Main) {
                                onSuccess(outputFile.absolutePath)
                            }
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Ошибка при получении uid!")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("ERRRROR", e.toString())
                    onError("Ошибка: ${e.localizedMessage}")
                }
            }
        }
    }

    fun uploadFile(
        context: Context,
        uri: Uri,
        projectId: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val publicKey = CryptoManager.getPublicKey(prefsRepository.getAlias()!!)
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return
        val fileName = getFileNameFromUri(contentResolver, uri) ?: "file"

        // Генерация AES-ключа
        val aesKey = AesUtils.generateAesKey()

        // Шифруем файл
        val (iv, encryptedData) = FileCryptoUtils.encryptFile(inputStream, aesKey)


        val combinedData = iv + encryptedData

        // Шифруем AES-ключ RSA-ключом
        val encryptedKey = RsaUtils.encryptKey(aesKey.encoded, publicKey)

        // Готовим multipart-запрос
        val requestFile = combinedData.toRequestBody("application/octet-stream".toMediaTypeOrNull())
        val multipart = MultipartBody.Part.createFormData("file", fileName, requestFile)

        val uid = prefsRepository.getUID()
        val projectPart = projectId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val userIdPart = uid.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val encryptedKeyPart = Base64.encodeToString(encryptedKey, Base64.NO_WRAP)
            .toRequestBody("text/plain".toMediaTypeOrNull())

        viewModelScope.launch(Dispatchers.IO) {
            val response = filesRepository.addFile(
                multipart, projectPart, userIdPart, encryptedKeyPart
            )
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError()
                }
            }
        }
    }


    private fun getFileNameFromUri(resolver: ContentResolver, uri: Uri): String? {
        val cursor = resolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            it.getString(nameIndex)
        }
    }

    fun getRoleId(): Int = prefsRepository.getRoleId()
}
