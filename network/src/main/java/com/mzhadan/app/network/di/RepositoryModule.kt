package com.mzhadan.app.network.di

import com.mzhadan.app.network.api.UsersApi
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
}