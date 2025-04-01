package com.mzhadan.app.network.api

import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.up.UserProject
import com.mzhadan.app.network.models.up.UserProjectResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsersProjectsApi {
    @GET("up/project/{project_id}")
    suspend fun getUsersFromProjectById(@Path("project_id") projectId: Int): Response<UserProjectResponse>

    @POST("up/")
    suspend fun addUserToProject(@Body up: UserProject): Response<ApiResponse>

    @DELETE("up/")
    suspend fun removeUserFromProject(@Body up: UserProject): Response<ApiResponse>
}