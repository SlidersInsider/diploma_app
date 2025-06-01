package com.mzhadan.app.network.repository.files

import com.mzhadan.app.network.api.FilesApi
import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.files.DownloadFileResponse
import com.mzhadan.app.network.models.files.FileResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import javax.inject.Inject

class FilesRepositoryImpl @Inject constructor(
    private val filesApi: FilesApi
) : FilesRepository {
    override suspend fun getFiles(): Response<List<FileResponse>> {
        return filesApi.getFiles()
    }

    override suspend fun getFilesByProject(projectId: Int): Response<List<FileResponse>> {
        return filesApi.getFilesByProject(projectId)
    }

    override suspend fun getFilesByUser(userId: Int): Response<List<FileResponse>> {
        return filesApi.getFilesByUser(userId)
    }

    override suspend fun addFile(
        file: MultipartBody.Part,
        projectId: RequestBody,
        userId: RequestBody,
        publicKey: RequestBody
    ): Response<ApiResponse> {
        return filesApi.addFile(file, projectId, userId, publicKey)
    }

    override suspend fun removeFile(fileId: Int): Response<ApiResponse> {
        return removeFile(fileId)
    }

    override suspend fun downloadFile(fileId: Int, userId: Int): Response<DownloadFileResponse> {
        return filesApi.downloadFile(fileId, userId)
    }

    override suspend fun updateFile(fileId: Int, file: MultipartBody.Part): Response<ApiResponse> {
        return filesApi.updateFile(fileId, file)
    }
}