package com.mzhadan.app.diploma.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.network.repository.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    fun login(username: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = authRepository.login(username, password)
                if (response.isSuccessful) {
                    response.body()?.let {
                        saveToken(it.access_token)
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
        viewModelScope.launch {
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

    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("access_token", token).apply()
    }

    fun getToken(): String? = sharedPreferences.getString("access_token", null)

    fun isUserLoggedIn(): Boolean = getToken() != null

    fun logout() {
        sharedPreferences.edit().remove("access_token").apply()
    }
}