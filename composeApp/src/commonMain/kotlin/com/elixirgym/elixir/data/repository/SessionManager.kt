package com.elixirgym.elixir.data.repository

import com.elixirgym.elixir.database.ElixirDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.Clock

class SessionManager(private val database: ElixirDatabase) : ISessionManager {

    private val _isAuthenticated = MutableStateFlow(checkAuthentication())
    override val isAuthenticated: Flow<Boolean> = _isAuthenticated.asStateFlow()

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
        val currentTime = Clock.System.now().toEpochMilliseconds()

        database.authTokenQueries.saveAuthToken(
            token = token,
            userId = userId,
            userEmail = userEmail,
            userName = userName,
            expiresAt = expiresAt,
            createdAt = currentTime
        )

        _isAuthenticated.value = true
    }

    /**
     * Get the current authentication token
     */
    fun getToken(): String? {
        return database.authTokenQueries.getAuthToken().executeAsOneOrNull()?.token
    }

    /**
     * Get the current user ID
     */
    fun getUserId(): String? {
        return database.authTokenQueries.getAuthToken().executeAsOneOrNull()?.userId
    }

    /**
     * Get the current user email
     */
    fun getUserEmail(): String? {
        return database.authTokenQueries.getAuthToken().executeAsOneOrNull()?.userEmail
    }

    /**
     * Get the current user name
     */
    fun getUserName(): String? {
        return database.authTokenQueries.getAuthToken().executeAsOneOrNull()?.userName
    }

    /**
     * Check if user is authenticated
     */
    fun isUserAuthenticated(): Boolean {
        val isAuth = database.authTokenQueries.isAuthenticated().executeAsOne() > 0
        _isAuthenticated.value = isAuth
        return isAuth
    }

    /**
     * Clear session (logout)
     */
    fun clearSession() {
        database.authTokenQueries.clearAuthToken()
        _isAuthenticated.value = false
    }

    /**
     * Check if token is expired
     */
    fun isTokenExpired(): Boolean {
        val authToken = database.authTokenQueries.getAuthToken().executeAsOneOrNull()

        if (authToken?.expiresAt == null) {
            return false // No expiration set
        }

        val currentTime = Clock.System.now().toEpochMilliseconds()
        return currentTime > authToken.expiresAt
    }

    private fun checkAuthentication(): Boolean {
        return try {
            database.authTokenQueries.isAuthenticated().executeAsOne() > 0
        } catch (e: Exception) {
            false
        }
    }
}
