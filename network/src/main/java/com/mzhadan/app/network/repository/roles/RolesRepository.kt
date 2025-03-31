package com.mzhadan.app.network.repository.roles

import com.mzhadan.app.network.models.roles.Role
import com.mzhadan.app.network.models.roles.RoleResponse
import retrofit2.Response

interface RolesRepository {
    suspend fun getRoles(): Response<List<RoleResponse>>
    suspend fun getRoleById(roleId: Int): Response<RoleResponse>
    suspend fun addRole(role: Role): Response<RoleResponse>
    suspend fun removeRole(roleId: Int): Response<RoleResponse>
}