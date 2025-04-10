package com.mzhadan.app.network.di

import android.content.Context
import android.content.SharedPreferences
import com.mzhadan.app.network.api.AuthApi
import com.mzhadan.app.network.api.FilesApi
import com.mzhadan.app.network.api.ProjectsApi
import com.mzhadan.app.network.api.RolesApi
import com.mzhadan.app.network.api.UsersApi
import com.mzhadan.app.network.api.UsersProjectsApi
import com.mzhadan.app.network.repository.auth.AuthRepository
import com.mzhadan.app.network.repository.auth.AuthRepositoryImpl
import com.mzhadan.app.network.repository.files.FilesRepository
import com.mzhadan.app.network.repository.files.FilesRepositoryImpl
import com.mzhadan.app.network.repository.prefs.PrefsRepository
import com.mzhadan.app.network.repository.prefs.PrefsRepositoryImpl
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
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository {
        return AuthRepositoryImpl(authApi)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePrefsRepository(sharedPreferences: SharedPreferences): PrefsRepository {
        return PrefsRepositoryImpl(sharedPreferences)
    }
}