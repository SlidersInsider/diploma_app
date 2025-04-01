package com.mzhadan.app.network.models.auth

data class LoginRequest(
    val username: String,
    val password: String
)