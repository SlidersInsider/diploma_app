package com.mzhadan.app.network.repository.prefs

import android.content.SharedPreferences
import javax.inject.Inject
import androidx.core.content.edit

class PrefsRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): PrefsRepository {

    override fun saveToken(token: String) {
        sharedPreferences.edit { putString("access_token", token) }
    }

    override fun getToken(): String? = sharedPreferences.getString("access_token", null)

    override fun isUserLoggedIn(): Boolean = getToken() != null

    override fun logout() {
        sharedPreferences.edit { remove("access_token") }
    }

    override fun saveUsername(username: String) {
        sharedPreferences.edit { putString("username", username) }
    }

    override fun getUsername(): String? = sharedPreferences.getString("username", null)
    override fun isUserNamedIn(): Boolean = getUsername() != null

    override fun saveUID(uid: Int) {
        sharedPreferences.edit { putInt("uid", uid) }
    }

    override fun getUID(): Int = sharedPreferences.getInt("uid", -1)

    override fun isUserUidedIn(): Boolean = getUID() != -1

    override fun saveAlias(alias: String) {
        sharedPreferences.edit { putString("alias", alias) }
    }

    override fun getAlias(): String? = sharedPreferences.getString("alias", null)

    override fun saveRoleId(roleId: Int) {
        sharedPreferences.edit { putInt("role", roleId) }
    }

    override fun getRoleId(): Int = sharedPreferences.getInt("role", -1)
}