package com.mzhadan.app.network.repository.projects

import com.mzhadan.app.network.models.projects.Project
import com.mzhadan.app.network.models.projects.ProjectResponse
import retrofit2.Response

interface ProjectsRepository {
    suspend fun getProjects(): Response<List<ProjectResponse>>
    suspend fun getProjectById(projectId: Int): Response<ProjectResponse>
    suspend fun getProjectByName(name: String): Response<ProjectResponse>
    suspend fun getProjectsByUser(userId: Int): Response<List<ProjectResponse>>
    suspend fun addProject(project: Project): Response<ProjectResponse>
    suspend fun removeProject(projectId: Int): Response<ProjectResponse>
}