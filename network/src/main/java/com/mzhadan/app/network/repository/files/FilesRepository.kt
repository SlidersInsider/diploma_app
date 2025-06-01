package com.mzhadan.app.network.repository.files

import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.files.DownloadFileResponse
import com.mzhadan.app.network.models.files.FileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response

interface FilesRepository {
    suspend fun getFiles(): Response<List<FileResponse>>
    suspend fun getFilesByProject(projectId: Int): Response<List<FileResponse>>
    suspend fun getFilesByUser(userId: Int): Response<List<FileResponse>>
    suspend fun addFile(
        file: MultipartBody.Part,
        projectId: RequestBody,
        userId: RequestBody,
        publicKey: RequestBody
    ): Response<ApiResponse>
    suspend fun removeFile(fileId: Int): Response<ApiResponse>
    suspend fun downloadFile(fileId: Int, userId: Int): Response<DownloadFileResponse>
    suspend fun updateFile(fileId: Int, file: MultipartBody.Part): Response<ApiResponse>
}