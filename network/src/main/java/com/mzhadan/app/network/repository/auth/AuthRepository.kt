package com.mzhadan.app.network.repository.auth

import com.mzhadan.app.network.models.auth.AuthResponse
import retrofit2.Response

interface AuthRepository {
    suspend fun register(username: String, password: String, roleId: Int): Response<AuthResponse>
    suspend fun login(username: String, password: String): Response<AuthResponse>
}