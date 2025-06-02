package com.mzhadan.app.diploma.ui.pages.create_project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.network.models.projects.Project
import com.mzhadan.app.network.repository.prefs.PrefsRepository
import com.mzhadan.app.network.repository.projects.ProjectsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateProjectViewModel @Inject constructor(
    private val prefsRepository: PrefsRepository,
    private val projectsRepository: ProjectsRepository
): ViewModel() {

    fun createProject(name: String, description: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userId = prefsRepository.getUID()
                if (userId != null) {
                    val response = projectsRepository.addProject(Project(name, description, userId))
                    if (response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            onError("Ошибка: ${response.code()}")
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Пользователь не руководитель")
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