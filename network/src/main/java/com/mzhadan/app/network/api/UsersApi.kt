package com.mzhadan.app.network.api

import com.mzhadan.app.network.models.ApiResponse
import com.mzhadan.app.network.models.users.RoleModel
import com.mzhadan.app.network.models.users.User
import com.mzhadan.app.network.models.users.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

private const val PATH = "users"

interface UsersApi {
    @GET("users/")
    suspend fun getUsers(): Response<List<UserResponse>>

    @GET("users/{user_id}")
    suspend fun getUserById(@Path("user_id") userId: Int): Response<UserResponse>

    @GET("users/{username}")
    suspend fun getUserByName(@Path("username") username: String): Response<UserResponse>

    @POST("users/")
    suspend fun addUser(@Body user: User): Response<UserResponse>

    @DELETE("users/{user_id}")
    suspend fun removeUser(@Path("user_id") userId: Int): Response<ApiResponse>

    @PUT("users/{user_id}/role")
    suspend fun updateUserRole(@Path("user_id") userId: Int, @Body role: RoleModel): Response<UserResponse>
}