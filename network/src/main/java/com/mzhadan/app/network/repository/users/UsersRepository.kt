package com.mzhadan.app.network.repository.users

import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.roles.Role
import com.mzhadan.app.network.models.users.User
import com.mzhadan.app.network.models.users.UserResponse
import retrofit2.Response

interface UsersRepository {
    suspend fun getUsers(): Response<List<UserResponse>>
    suspend fun getUserById(userId: Int): Response<UserResponse>
    suspend fun getUserByUsername(username: String): Response<UserResponse>
    suspend fun addUser(user: User): Response<UserResponse>
    suspend fun removeUser(userId: Int): Response<ApiResponse>
    suspend fun updateUserRole(userId: Int, role: Role): Response<UserResponse>
}