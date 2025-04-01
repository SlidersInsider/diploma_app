package com.mzhadan.app.network.models.users

data class UserModel(
    val id: Int,
    val username: String,
    val password: String,
    val roleId: Int
)
