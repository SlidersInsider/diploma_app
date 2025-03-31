package com.mzhadan.app.network.models.users

data class User(
    val username: String,
    val password: String,
    val role: String
)

data class RoleModel(
    val name: String
)
