package com.mzhadan.app.network.repository.up

import com.mzhadan.app.network.api.UsersProjectsApi
import javax.inject.Inject

class UsersProjectsRepositoryImpl @Inject constructor(
    private val usersProjectsApi: UsersProjectsApi
) : UsersProjectsRepository {

}