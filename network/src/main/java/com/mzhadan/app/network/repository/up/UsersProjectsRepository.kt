package com.mzhadan.app.network.repository.up

import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.up.UserProject
import com.mzhadan.app.network.models.up.UserProjectResponse
import retrofit2.Response

interface UsersProjectsRepository {
    suspend fun getUsersFromProjectById(projectId: Int): Response<UserProjectResponse>
    suspend fun addUserToProject(up: UserProject): Response<ApiResponse>
    suspend fun removeUserFromProject(up: UserProject): Response<ApiResponse>
}