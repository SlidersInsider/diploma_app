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

    override fun saveUsername(username: String) {
        sharedPreferences.edit().putString("username", username).apply()
    }

    override fun getUsername(): String? = sharedPreferences.getString("username", null)
    override fun isUserNamedIn(): Boolean = getUsername() != null

    override fun saveUID(uid: Int) {
        sharedPreferences.edit().putInt("uid", uid).apply()
    }

    override fun getUID(): Int = sharedPreferences.getInt("uid", -1)

    override fun isUserUidedIn(): Boolean = getUID() != -1
}