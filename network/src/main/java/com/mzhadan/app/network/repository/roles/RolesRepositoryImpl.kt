package com.mzhadan.app.network.repository.roles

import com.mzhadan.app.network.api.RolesApi
import javax.inject.Inject

class RolesRepositoryImpl @Inject constructor(
    private val rolesApi: RolesApi
) : RolesRepository {

}