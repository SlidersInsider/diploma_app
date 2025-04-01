package com.mzhadan.app.network.api

import com.mzhadan.app.network.models.auth.AuthResponse
import com.mzhadan.app.network.models.auth.LoginRequest
import com.mzhadan.app.network.models.auth.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}