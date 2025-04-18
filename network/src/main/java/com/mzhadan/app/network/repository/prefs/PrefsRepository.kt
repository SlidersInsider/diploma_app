package com.mzhadan.app.network.repository.prefs

interface PrefsRepository {
    fun saveToken(token: String)
    fun getToken(): String?
    fun isUserLoggedIn(): Boolean
    fun logout()

    fun saveUsername(username: String)
    fun getUsername(): String?
    fun isUserNamedIn(): Boolean

    fun saveUID(uid: Int)
    fun getUID(): Int?
    fun isUserUidedIn(): Boolean
}