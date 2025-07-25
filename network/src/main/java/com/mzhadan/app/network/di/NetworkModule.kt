package com.mzhadan.app.network.di

import com.mzhadan.app.network.api.AuthApi
import com.mzhadan.app.network.api.FilesApi
import com.mzhadan.app.network.api.ProjectsApi
import com.mzhadan.app.network.api.RolesApi
import com.mzhadan.app.network.api.UsersApi
import com.mzhadan.app.network.api.UsersProjectsApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

//    private val URL = "http://10.0.2.2:8000/"
    private val URL = "http://80.87.104.185/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi {
        return retrofit.create(UsersApi::class.java)
    }

    @Provides
    @Singleton
    fun provideProjectsApi(retrofit: Retrofit): ProjectsApi {
        return retrofit.create(ProjectsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRolesApi(retrofit: Retrofit): RolesApi {
        return retrofit.create(RolesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUsersProjectsApi(retrofit: Retrofit): UsersProjectsApi {
        return retrofit.create(UsersProjectsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFilesApi(retrofit: Retrofit): FilesApi {
        return retrofit.create(FilesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }
}
