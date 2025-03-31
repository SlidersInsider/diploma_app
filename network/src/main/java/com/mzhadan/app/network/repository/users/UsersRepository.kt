package com.mzhadan.app.network.repository.users

import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.users.RoleModel
import com.mzhadan.app.network.models.users.User
import com.mzhadan.app.network.models.users.UserResponse
import retrofit2.Response

interface UsersRepository {
    suspend fun getAllUsers(): Response<List<UserResponse>>
    suspend fun getUserById(userId: Int): Response<UserResponse>
    suspend fun getUserByUsername(username: String): Response<UserResponse>
    suspend fun addUser(user: User): Response<UserResponse>
    suspend fun removeUser(userId: Int): Response<ApiResponse>
    suspend fun updateUserRole(userId: Int, role: RoleModel): Response<UserResponse>
}