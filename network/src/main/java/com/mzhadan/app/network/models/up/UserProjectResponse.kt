package com.mzhadan.app.network.models.up

import com.mzhadan.app.network.models.users.UserModel

data class UserProjectResponse(
    val project: String,
    val users: List<UserModel>
)
