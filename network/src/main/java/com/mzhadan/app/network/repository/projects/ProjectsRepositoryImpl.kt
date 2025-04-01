package com.mzhadan.app.network.repository.projects

import com.mzhadan.app.network.api.ProjectsApi
import com.mzhadan.app.network.models.projects.Project
import com.mzhadan.app.network.models.projects.ProjectResponse
import retrofit2.Response
import javax.inject.Inject

class ProjectsRepositoryImpl @Inject constructor(
    private val projectsApi: ProjectsApi
) : ProjectsRepository {
    override suspend fun getProjects(): Response<List<ProjectResponse>> {
        return projectsApi.getProjects()
    }

    override suspend fun getProjectById(projectId: Int): Response<ProjectResponse> {
        return projectsApi.getProjectById(projectId)
    }

    override suspend fun getProjectByName(name: String): Response<ProjectResponse> {
        return projectsApi.getProjectByName(name)
    }

    override suspend fun addProject(project: Project): Response<ProjectResponse> {
        return projectsApi.addProject(project)
    }

    override suspend fun removeProject(projectId: Int): Response<ProjectResponse> {
        return projectsApi.removeProject(projectId)
    }
}