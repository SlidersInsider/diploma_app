package com.mzhadan.app.network.repository.prefs

interface PrefsRepository {
    fun saveToken(token: String)
    fun getToken(): String?
    fun isUserLoggedIn(): Boolean
    fun logout()

    fun saveUsername(username: String)
    fun getUsername(): String?
}