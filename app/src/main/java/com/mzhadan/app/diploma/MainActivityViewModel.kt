package com.mzhadan.app.diploma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.network.models.files.FileResponse
import com.mzhadan.app.network.models.projects.ProjectResponse
import com.mzhadan.app.network.models.roles.RoleResponse
import com.mzhadan.app.network.models.up.UserProjectResponse
import com.mzhadan.app.network.models.users.UserResponse
import com.mzhadan.app.network.repository.auth.AuthRepository
import com.mzhadan.app.network.repository.files.FilesRepository
import com.mzhadan.app.network.repository.projects.ProjectsRepository
import com.mzhadan.app.network.repository.roles.RolesRepository
import com.mzhadan.app.network.repository.up.UsersProjectsRepository
import com.mzhadan.app.network.repository.users.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val projectsRepository: ProjectsRepository,
    private val rolesRepository: RolesRepository,
    private val usersProjectsRepository: UsersProjectsRepository,
    private val filesRepository: FilesRepository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _users = MutableLiveData<List<UserResponse>>()
    val users: LiveData<List<UserResponse>> get() = _users

    private val _projects = MutableLiveData<List<ProjectResponse>>()
    val projects: LiveData<List<ProjectResponse>> get() = _projects

    private val _roles = MutableLiveData<List<RoleResponse>>()
    val roles: LiveData<List<RoleResponse>> get() = _roles

    private val _ups = MutableLiveData<UserProjectResponse>()
    val ups: LiveData<UserProjectResponse> get() = _ups

    private val _files = MutableLiveData<List<FileResponse>>()
    val files: LiveData<List<FileResponse>> get() = _files

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> get() = _token

    fun getAllUsers() {
        viewModelScope.launch {
            val response = usersRepository.getUsers()
            if (response.isSuccessful) {
                _users.postValue(response.body())
            }
        }
    }

    fun getAllProjects() {
        viewModelScope.launch {
            val response = projectsRepository.getProjects()
            if (response.isSuccessful) {
                _projects.postValue(response.body())
            }
        }
    }

    fun getAllRoles() {
        viewModelScope.launch {
            val response = rolesRepository.getRoles()
            if (response.isSuccessful) {
                _roles.postValue(response.body())
            }
        }
    }

    fun getAllUsersFromProject(projectId: Int) {
        viewModelScope.launch {
            val response = usersProjectsRepository.getUsersFromProjectById(projectId)
            if (response.isSuccessful) {
                _ups.postValue(response.body())
            }
        }
    }

    fun getAllFiles() {
        viewModelScope.launch {
            val response = filesRepository.getFiles()
            if (response.isSuccessful) {
                _files.postValue(response.body())
            }
        }
    }

    fun register(username: String, password: String, roleId: Int) {
        viewModelScope.launch {
            val response = authRepository.register(username, password, roleId)
            if (response.isSuccessful) {
                _token.postValue(response.body()?.access_token)
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val response = authRepository.login(username, password)
            if (response.isSuccessful) {
                _token.postValue(response.body()?.access_token)
            }
        }
    }
}