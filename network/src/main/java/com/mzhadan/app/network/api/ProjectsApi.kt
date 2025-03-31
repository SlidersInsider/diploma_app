package com.mzhadan.app.network.api

import com.mzhadan.app.network.models.projects.Project
import com.mzhadan.app.network.models.projects.ProjectResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProjectsApi {
    @GET("projects/")
    suspend fun getProjects(): Response<List<ProjectResponse>>

    @GET("projects/{project_id}")
    suspend fun getProjectById(@Path("project_id") projectId: Int): Response<ProjectResponse>

    @GET("projects/{project_name}")
    suspend fun getProjectByName(@Path("project_name") name: String): Response<ProjectResponse>

    @POST("projects/")
    suspend fun addProject(@Body project: Project): Response<ProjectResponse>

    @DELETE("projects/{project_id}")
    suspend fun removeProject(@Path("project_id") projectId: Int): Response<ProjectResponse>
}