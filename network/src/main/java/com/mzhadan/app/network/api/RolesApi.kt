package com.mzhadan.app.network.api

import com.mzhadan.app.network.models.roles.Role
import com.mzhadan.app.network.models.roles.RoleResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RolesApi {
    @GET("roles/")
    suspend fun getRoles(): Response<List<RoleResponse>>

    @GET("roles/{role_id}")
    suspend fun getRoleById(@Path("role_id") roleId: Int): Response<RoleResponse>

    @POST("roles/")
    suspend fun addRole(@Body role: Role): Response<RoleResponse>

    @DELETE("roles/{role_id}")
    suspend fun removeRole(@Path("role_id") roleId: Int): Response<RoleResponse>
}