package com.mzhadan.app.diploma.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.network.repository.auth.AuthRepository
import com.mzhadan.app.network.repository.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val prefsRepository: PrefsRepository
) : ViewModel() {

    fun login(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.login(username, password)
                if (response.isSuccessful) {
                    response.body()?.let {
                        prefsRepository.saveToken(it.access_token)
                        prefsRepository.saveUsername(username)
                        onSuccess()
                    }
                } else {
                    onError("Ошибка: ${response.message()}")
                }
            } catch (e: HttpException) {
                onError("Ошибка сети: ${e.message}")
            } catch (e: Exception) {
                onError("Неизвестная ошибка: ${e.localizedMessage}")
            }
        }
    }

    fun register(username: String, password: String, roleId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = authRepository.register(username, password, roleId)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError("Ошибка: ${response.message()}")
                }
            } catch (e: HttpException) {
                onError("Ошибка сети: ${e.message}")
            } catch (e: Exception) {
                onError("Неизвестная ошибка: ${e.localizedMessage}")
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return prefsRepository.isUserLoggedIn()
    }
}