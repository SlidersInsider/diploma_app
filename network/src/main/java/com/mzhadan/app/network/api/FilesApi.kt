package com.mzhadan.app.network.api

import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.files.FileResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Streaming

interface FilesApi {
    @GET("files/")
    suspend fun getFiles(): Response<List<FileResponse>>

    @GET("files/project/{project_id}")
    suspend fun getFilesByProject(@Path("project_id") projectId: Int): Response<List<FileResponse>>

    @GET("files/user/{user_id}")
    suspend fun getFilesByUser(@Path("user_id") userId: Int): Response<List<FileResponse>>

    @Multipart
    @POST("files/")
    suspend fun addFile(
        @Part file: MultipartBody.Part,
        @Part("project_id") projectId: Int,
        @Part("user_id") userId: Int,
        @Part("public_key") publicKey: String
    ): Response<ApiResponse>

    @DELETE("files/{file_id}")
    suspend fun removeFile(@Path("file_id") fileId: Int): Response<ApiResponse>

    @FormUrlEncoded
    @POST("files/download/{file_id}")
    @Streaming
    suspend fun downloadFile(
        @Path("file_id") fileId: Int,
        @Field("user_id") userId: Int,
        @Field("private_key") privateKey: String
    ): Response<ResponseBody>
}