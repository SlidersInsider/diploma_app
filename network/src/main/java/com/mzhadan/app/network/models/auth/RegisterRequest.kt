package com.mzhadan.app.network.models.auth

data class RegisterRequest(
    val username: String,
    val password: String,
    val role_id: Int,
    val public_key: String
)