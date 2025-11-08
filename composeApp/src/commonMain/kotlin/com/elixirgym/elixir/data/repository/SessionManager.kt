package com.elixirgym.elixir.data.repository

import com.elixirgym.elixir.database.ElixirDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.time.ExperimentalTime

class SessionManager(private val database: ElixirDatabase) : ISessionManager {

    private val _isAuthenticated = MutableStateFlow(checkAuthentication())
    override val isAuthenticated: Flow<Boolean> = _isAuthenticated.asStateFlow()

    /**
     * Save authentication token and user information
     */
    @OptIn(ExperimentalTime::class)
    override fun saveSession(
        token: String,
        userId: String?,
        userEmail: String?,
        userName: String?,
        expiresAt: Long?
    ) {
        val currentTime = kotlin.time.Clock.System.now().toEpochMilliseconds()

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
    override fun getToken(): String? {
        return database.authTokenQueries.getAuthToken().executeAsOneOrNull()?.token
    }

    /**
     * Get the current user ID
     */
    override fun getUserId(): String? {
        return database.authTokenQueries.getAuthToken().executeAsOneOrNull()?.userId
    }

    /**
     * Get the current user email
     */
    override fun getUserEmail(): String? {
        return database.authTokenQueries.getAuthToken().executeAsOneOrNull()?.userEmail
    }

    /**
     * Get the current user name
     */
    override fun getUserName(): String? {
        return database.authTokenQueries.getAuthToken().executeAsOneOrNull()?.userName
    }

    /**
     * Check if user is authenticated
     */
    override fun isUserAuthenticated(): Boolean {
        val isAuth = database.authTokenQueries.isAuthenticated().executeAsOne()
        _isAuthenticated.value = isAuth
        return isAuth
    }

    /**
     * Clear session (logout)
     */
    override fun clearSession() {
        database.authTokenQueries.clearAuthToken()
        _isAuthenticated.value = false
    }

    /**
     * Check if token is expired
     */
    @OptIn(ExperimentalTime::class)
    override fun isTokenExpired(): Boolean {
        val authToken = database.authTokenQueries.getAuthToken().executeAsOneOrNull()

        if (authToken?.expiresAt == null) {
            return false // No expiration set
        }

        val currentTime = kotlin.time.Clock.System.now().toEpochMilliseconds()
        return currentTime > authToken.expiresAt
    }

    private fun checkAuthentication(): Boolean {
        return try {
            val isAuth = database.authTokenQueries.isAuthenticated().executeAsOne()
            _isAuthenticated.value = isAuth
            return isAuth
        } catch (e: Exception) {
            false
        }
    }
}
