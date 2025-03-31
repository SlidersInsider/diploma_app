package com.mzhadan.app.network.models.users

import com.mzhadan.app.network.models.roles.RoleResponse

data class UserResponse(
    val id: Int,
    val username: String,
    val role: RoleResponse
)