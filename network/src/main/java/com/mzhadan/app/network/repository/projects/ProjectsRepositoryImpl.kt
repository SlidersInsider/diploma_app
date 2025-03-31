package com.mzhadan.app.network.repository.projects

import com.mzhadan.app.network.api.ProjectsApi
import javax.inject.Inject

class ProjectsRepositoryImpl @Inject constructor(
    private val projectsApi: ProjectsApi
) : ProjectsRepository {

}