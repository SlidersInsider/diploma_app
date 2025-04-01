package com.mzhadan.app.network.repository.roles

import com.mzhadan.app.network.api.RolesApi
import com.mzhadan.app.network.models.roles.Role
import com.mzhadan.app.network.models.roles.RoleResponse
import retrofit2.Response
import javax.inject.Inject

class RolesRepositoryImpl @Inject constructor(
    private val rolesApi: RolesApi
) : RolesRepository {
    override suspend fun getRoles(): Response<List<RoleResponse>> {
        return rolesApi.getRoles()
    }

    override suspend fun getRoleById(roleId: Int): Response<RoleResponse> {
        return rolesApi.getRoleById(roleId)
    }

    override suspend fun addRole(role: Role): Response<RoleResponse> {
        return rolesApi.addRole(role)
    }

    override suspend fun removeRole(roleId: Int): Response<RoleResponse> {
        return rolesApi.removeRole(roleId)
    }
}