package com.mzhadan.app.network.repository.up

import com.mzhadan.app.network.api.UsersProjectsApi
import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.up.UserProject
import com.mzhadan.app.network.models.up.UserProjectResponse
import retrofit2.Response
import javax.inject.Inject

class UsersProjectsRepositoryImpl @Inject constructor(
    private val usersProjectsApi: UsersProjectsApi
) : UsersProjectsRepository {
    override suspend fun getUsersFromProjectById(projectId: Int): Response<UserProjectResponse> {
        return usersProjectsApi.getUsersFromProjectById(projectId)
    }
    override suspend fun addUserToProject(up: UserProject): Response<ApiResponse> {
        return usersProjectsApi.addUserToProject(up)
    }
    override suspend fun removeUserFromProject(up: UserProject): Response<ApiResponse> {
        return usersProjectsApi.removeUserFromProject(up)
    }
}