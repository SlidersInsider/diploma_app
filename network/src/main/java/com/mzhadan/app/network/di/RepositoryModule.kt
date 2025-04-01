package com.mzhadan.app.network.di

import com.mzhadan.app.network.api.FilesApi
import com.mzhadan.app.network.api.ProjectsApi
import com.mzhadan.app.network.api.RolesApi
import com.mzhadan.app.network.api.UsersApi
import com.mzhadan.app.network.api.UsersProjectsApi
import com.mzhadan.app.network.repository.files.FilesRepository
import com.mzhadan.app.network.repository.files.FilesRepositoryImpl
import com.mzhadan.app.network.repository.projects.ProjectsRepository
import com.mzhadan.app.network.repository.projects.ProjectsRepositoryImpl
import com.mzhadan.app.network.repository.roles.RolesRepository
import com.mzhadan.app.network.repository.roles.RolesRepositoryImpl
import com.mzhadan.app.network.repository.up.UsersProjectsRepository
import com.mzhadan.app.network.repository.up.UsersProjectsRepositoryImpl
import com.mzhadan.app.network.repository.users.UsersRepository
import com.mzhadan.app.network.repository.users.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUsersRepository(usersApi: UsersApi): UsersRepository {
        return UsersRepositoryImpl(usersApi)
    }

    @Provides
    @Singleton
    fun provideProjectsRepository(projectsApi: ProjectsApi): ProjectsRepository {
        return ProjectsRepositoryImpl(projectsApi)
    }

    @Provides
    @Singleton
    fun provideRolesRepository(rolesApi: RolesApi): RolesRepository {
        return RolesRepositoryImpl(rolesApi)
    }

    @Provides
    @Singleton
    fun provideUsersProjectsRepository(usersProjectsApi: UsersProjectsApi): UsersProjectsRepository {
        return UsersProjectsRepositoryImpl(usersProjectsApi)
    }

    @Provides
    @Singleton
    fun provideFilesRepository(filesApi: FilesApi): FilesRepository {
        return FilesRepositoryImpl(filesApi)
    }
}