package com.mzhadan.app.network.repository.prefs

import android.content.SharedPreferences
import javax.inject.Inject

class PrefsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PrefsRepository {

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString("access_token", token).apply()
    }

    override fun getToken(): String? = sharedPreferences.getString("access_token", null)

    override fun isUserLoggedIn(): Boolean = getToken() != null

    override fun logout() {
        sharedPreferences.edit().remove("access_token").apply()
    }
}