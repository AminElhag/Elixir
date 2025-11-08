package com.elixirgym.elixir.data.repository

import kotlinx.coroutines.flow.Flow

interface ISessionManager {
    val isAuthenticated: Flow<Boolean>

    fun saveSession(
        token: String,
        userId: String? = null,
        userEmail: String? = null,
        userName: String? = null,
        expiresAt: Long? = null
    )

    fun getToken(): String?
    fun getUserId(): String?
    fun getUserEmail(): String?
    fun getUserName(): String?
    fun isUserAuthenticated(): Boolean
    fun clearSession()
    fun isTokenExpired(): Boolean
}
