package com.mzhadan.app.diploma.ui.pages.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.network.models.projects.ProjectResponse
import com.mzhadan.app.network.repository.prefs.PrefsRepository
import com.mzhadan.app.network.repository.projects.ProjectsRepository
import com.mzhadan.app.network.repository.users.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val prefsRepository: PrefsRepository,
    private val projectsRepository: ProjectsRepository
): ViewModel() {

    private val _projects = MutableLiveData<List<ProjectResponse>>()
    val projects: LiveData<List<ProjectResponse>> get() = _projects

    fun getUserByName(onError: (String) -> Unit) {
        val name = prefsRepository.getUsername()
        if (name != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = usersRepository.getUserByUsername(name)
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            prefsRepository.saveUID(response.body()!!.id)
                            prefsRepository.saveRoleId(2)
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        onError("Ошибка: $e")
                    }
                }
            }
        }
    }

    fun isUserNamedIn(): Boolean {
        return prefsRepository.isUserNamedIn()
    }

    fun isUserUidedIn(): Boolean {
        return prefsRepository.isUserUidedIn()
    }

    fun getAllProjects(onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = projectsRepository.getProjectsByUser(prefsRepository.getUID()!!)
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
}