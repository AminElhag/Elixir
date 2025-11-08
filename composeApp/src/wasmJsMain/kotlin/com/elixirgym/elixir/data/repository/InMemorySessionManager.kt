package com.elixirgym.elixir.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * In-memory implementation of SessionManager for wasmJs
 * (SQLDelight is not supported in wasmJs)
 */
class InMemorySessionManager : ISessionManager {

    private val _isAuthenticated = MutableStateFlow(false)
    override val isAuthenticated: Flow<Boolean> = _isAuthenticated.asStateFlow()

    private var token: String? = null
    private var userId: String? = null
    private var userEmail: String? = null
    private var userName: String? = null
    private var expiresAt: Long? = null

    /**
     * Save authentication token and user information
     */
    fun saveSession(
        token: String,
        userId: String? = null,
        userEmail: String? = null,
        userName: String? = null,
        expiresAt: Long? = null
    ) {
        this.token = token
        this.userId = userId
        this.userEmail = userEmail
        this.userName = userName
        this.expiresAt = expiresAt
        _isAuthenticated.value = true
    }

    /**
     * Get the current authentication token
     */
    fun getToken(): String? = token

    /**
     * Get the current user ID
     */
    fun getUserId(): String? = userId

    /**
     * Get the current user email
     */
    fun getUserEmail(): String? = userEmail

    /**
     * Get the current user name
     */
    fun getUserName(): String? = userName

    /**
     * Check if user is authenticated
     */
    fun isUserAuthenticated(): Boolean {
        val isAuth = !token.isNullOrEmpty()
        _isAuthenticated.value = isAuth
        return isAuth
    }

    /**
     * Clear session (logout)
     */
    fun clearSession() {
        token = null
        userId = null
        userEmail = null
        userName = null
        expiresAt = null
        _isAuthenticated.value = false
    }

    /**
     * Check if token is expired
     */
    fun isTokenExpired(): Boolean {
        if (expiresAt == null) {
            return false // No expiration set
        }

        val currentTime = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
        return currentTime > expiresAt!!
    }
}
