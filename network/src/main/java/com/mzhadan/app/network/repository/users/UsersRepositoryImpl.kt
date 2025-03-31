package com.mzhadan.app.network.repository.users

import com.mzhadan.app.network.api.UsersApi
import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.users.RoleModel
import com.mzhadan.app.network.models.users.User
import com.mzhadan.app.network.models.users.UserResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class UsersRepositoryImpl @Inject constructor(
    private val usersApi: UsersApi
) : UsersRepository {
    override suspend fun getAllUsers(): Response<List<UserResponse>> {
        return usersApi.getUsers()
    }

    override suspend fun getUserByUsername(username: String): Response<UserResponse> {
        return usersApi.getUserByName(username)
    }

    override suspend fun getUserById(userId: Int): Response<UserResponse> {
        return usersApi.getUserById(userId)
    }

    override suspend fun addUser(user: User): Response<UserResponse> {
        return usersApi.addUser(user)
    }

    override suspend fun removeUser(userId: Int): Response<ApiResponse> {
        return usersApi.removeUser(userId)
    }

    override suspend fun updateUserRole(userId: Int, role: RoleModel): Response<UserResponse> {
        return usersApi.updateUserRole(userId, role)
    }
}