package com.mzhadan.app.diploma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.network.models.projects.ProjectResponse
import com.mzhadan.app.network.models.roles.RoleResponse
import com.mzhadan.app.network.models.users.UserResponse
import com.mzhadan.app.network.repository.projects.ProjectsRepository
import com.mzhadan.app.network.repository.roles.RolesRepository
import com.mzhadan.app.network.repository.users.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val projectsRepository: ProjectsRepository,
    private val rolesRepository: RolesRepository
): ViewModel() {
    private val _users = MutableLiveData<List<UserResponse>>()
    val users: LiveData<List<UserResponse>> get() = _users

    private val _projects = MutableLiveData<List<ProjectResponse>>()
    val projects: LiveData<List<ProjectResponse>> get() = _projects

    private val _roles = MutableLiveData<List<RoleResponse>>()
    val roles: LiveData<List<RoleResponse>> get() = _roles

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
}