package com.mzhadan.app.diploma.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzhadan.app.network.repository.auth.AuthRepository
import com.mzhadan.app.network.repository.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.security.PublicKey
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
                        withContext(Dispatchers.Main) {
                            onSuccess()
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Ошибка: ${response.message()}")
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    onError("Ошибка сети: ${e.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Неизвестная ошибка: ${e.localizedMessage}")
                }
            }
        }
    }

    fun register(
        username: String,
        password: String,
        roleId: Int,
        publicKey: String,
        alias: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                prefsRepository.saveAlias(alias)
                val response = authRepository.register(username, password, roleId, publicKey)
                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        onSuccess()
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onError("Ошибка: ${response.message()}")
                    }
                }
            } catch (e: HttpException) {
                withContext(Dispatchers.Main) {
                    onError("Ошибка сети: ${e.message}")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    onError("Неизвестная ошибка: ${e.localizedMessage}")
                }
            }
        }
    }

    fun isUserLoggedIn(): Boolean {
        return prefsRepository.isUserLoggedIn()
    }
}