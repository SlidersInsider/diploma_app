package com.mzhadan.app.network.models.users

data class UserResponse(
    val id: Int,
    val username: String,
    val role: RoleResponse
)

data class RoleResponse(
    val id: Int,
    val name: String
)