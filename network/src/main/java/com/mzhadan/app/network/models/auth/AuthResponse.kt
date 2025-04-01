package com.mzhadan.app.network.models.auth

data class AuthResponse(
    val access_token: String,
    val token_type: String
)