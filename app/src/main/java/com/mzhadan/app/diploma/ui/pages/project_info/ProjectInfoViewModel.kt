package com.mzhadan.app.diploma.ui.pages.project_info

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.network.models.files.FileResponse
import com.mzhadan.app.network.repository.files.FilesRepository
import com.mzhadan.app.network.repository.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        filename: String,
        privateKey: String,
        onSuccess: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
//            val userId = prefsRepository.getUID()
            val userId = 2
            if (userId != null) {
                val response = filesRepository.downloadFile(fileId, userId, privateKey)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        val file = File(context.filesDir.absolutePath + "/temp", "$filename")
                        file.parentFile?.mkdirs()
                        file.outputStream().use { output ->
                            body.byteStream().copyTo(output)
                        }
                        onSuccess(file.absolutePath)
                    }
                }
            }
        }
    }

    fun uploadFile(
        context: Context,
        uri: Uri,
        projectId: Int,
        publicKey: String,
        onSuccess: () -> Unit,
        onError: () -> Unit)
    {
        val contentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(uri) ?: return
        val fileName = getFileNameFromUri(contentResolver, uri) ?: "file"

        val fileBytes = inputStream.readBytes()
        val requestFile = fileBytes.toRequestBody("application/octet-stream".toMediaTypeOrNull())
        val multipart = MultipartBody.Part.createFormData("file", fileName, requestFile)

//        val uid = prefsRepository.getUID()
        val uid = 1
        val projectPart = projectId.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val userIdPart = uid.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val publicKeyPart = publicKey.toRequestBody("text/plain".toMediaTypeOrNull())

        viewModelScope.launch(Dispatchers.IO) {
            val response = filesRepository.addFile(multipart, projectPart, userIdPart, publicKeyPart)
            if (response.isSuccessful) {
                onSuccess()
            } else {
                onError()
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
}
