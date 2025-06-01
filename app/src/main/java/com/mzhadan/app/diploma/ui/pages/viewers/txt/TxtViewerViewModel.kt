package com.mzhadan.app.diploma.ui.pages.viewers.txt

import android.util.Base64
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.diploma.crypto.AesUtils
import com.mzhadan.app.diploma.crypto.CryptoManager
import com.mzhadan.app.diploma.crypto.FileCryptoUtils
import com.mzhadan.app.diploma.crypto.RsaUtils
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
class TxtViewerViewModel @Inject constructor(
    private val filesRepository: FilesRepository,
    private val prefsRepository: PrefsRepository
): ViewModel() {

    fun updateFile(fileId: Int, filePath: String, publicKey: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = File(filePath)
                if (!file.exists()) {
                    withContext(Dispatchers.Main) {
                        onError("Ошибка: файл не найден!")
                    }
                    return@launch
                }

                val privateKey = CryptoManager.getPrivateKey(prefsRepository.getAlias()!!)
                val encryptedAesKeyBytes = Base64.decode(publicKey, Base64.DEFAULT)
                val aesKeyBytes = RsaUtils.decryptKey(encryptedAesKeyBytes, privateKey)
                val aesKey = AesUtils.keyFromBytes(aesKeyBytes)

                val (iv, encryptedData) = FileCryptoUtils.encryptFile(file.inputStream(), aesKey)
                val combinedData = iv + encryptedData

                val requestFile = combinedData.toRequestBody("application/octet-stream".toMediaTypeOrNull())
                val multipart = MultipartBody.Part.createFormData("file", file.name, requestFile)

                val response = filesRepository.updateFile(fileId, multipart)

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Ошибка обновления файла: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Ошибка: ${e.message}")
                }
            }
        }
    }
}