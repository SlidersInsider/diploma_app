package com.mzhadan.app.diploma.ui.pages.projects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.network.models.projects.ProjectResponse
import com.mzhadan.app.network.models.up.UserProject
import com.mzhadan.app.network.repository.prefs.PrefsRepository
import com.mzhadan.app.network.repository.projects.ProjectsRepository
import com.mzhadan.app.network.repository.up.UsersProjectsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProjectsViewModel @Inject constructor(
    private val projectsRepository: ProjectsRepository,
    private val usersProjectsRepository: UsersProjectsRepository,
    private val prefsRepository: PrefsRepository
): ViewModel() {

    private val _projects = MutableLiveData<List<ProjectResponse>>()
    val projects: LiveData<List<ProjectResponse>> get() = _projects

    fun getAllProjects(onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = projectsRepository.getProjects()
                if (response.isSuccessful) {
                    _projects.postValue(response.body())
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Ошибка: $e")
                }
            }
        }
    }

    fun joinProject(projectId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = usersProjectsRepository.addUserToProject(UserProject(user_id = prefsRepository.getUID()!!, project_id = projectId))
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Ошибка: $e")
                }
            }
        }
    }

    fun getRoleId(): Int = prefsRepository.getRoleId()
}