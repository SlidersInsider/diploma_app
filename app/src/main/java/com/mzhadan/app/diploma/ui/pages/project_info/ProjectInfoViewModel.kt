package com.mzhadan.app.diploma.ui.pages.project_info

import android.content.Context
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

    fun downloadFile(context: Context, fileId: Int, filename: String, privateKey: String) {
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
                    }
                }
            }
        }
    }
}
