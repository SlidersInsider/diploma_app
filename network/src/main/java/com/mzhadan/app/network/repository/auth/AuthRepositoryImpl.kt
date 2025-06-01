package com.mzhadan.app.network.repository.auth

import com.mzhadan.app.network.api.AuthApi
import com.mzhadan.app.network.models.auth.AuthResponse
import com.mzhadan.app.network.models.auth.LoginRequest
import com.mzhadan.app.network.models.auth.RegisterRequest
import retrofit2.Response
import java.security.PublicKey
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun register(username: String, password: String, roleId: Int, publicKey: String): Response<AuthResponse> {
        val request = RegisterRequest(username, password, roleId, publicKey)
        return authApi.register(request)
    }

    override suspend fun login(username: String, password: String): Response<AuthResponse> {
        val request = LoginRequest(username, password)
        return authApi.login(request)
    }
}