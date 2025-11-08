package com.elixirgym.elixir.presentation.viewmodels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.elixirgym.elixir.data.repository.ISessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlin.random.Random

data class LoginState(
    val emailOrPhone: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val emailOrPhoneError: String? = null,
    val passwordError: String? = null
)

class LoginViewModel(
    private val sessionManager: ISessionManager
) : ScreenModel {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun updateEmailOrPhone(emailOrPhone: String) {
        _state.value = _state.value.copy(
            emailOrPhone = emailOrPhone,
            emailOrPhoneError = null,
            errorMessage = null
        )
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(
            password = password,
            passwordError = null,
            errorMessage = null
        )
    }

    private fun validateLoginForm(): Boolean {
        var isValid = true
        val currentState = _state.value

        // Email or Phone validation
        if (currentState.emailOrPhone.isBlank()) {
            _state.value = _state.value.copy(emailOrPhoneError = "Email or phone number is required")
            isValid = false
        } else {
            // Check if it's a valid email or phone number
            val isEmail = isValidEmail(currentState.emailOrPhone)
            val isPhone = isValidPhone(currentState.emailOrPhone)

            if (!isEmail && !isPhone) {
                _state.value = _state.value.copy(
                    emailOrPhoneError = "Please enter a valid email address or phone number"
                )
                isValid = false
            }
        }

        // Password validation
        if (currentState.password.isBlank()) {
            _state.value = _state.value.copy(passwordError = "Password is required")
            isValid = false
        } else if (currentState.password.length < 8) {
            _state.value = _state.value.copy(passwordError = "Password must be at least 8 characters")
            isValid = false
        }

        return isValid
    }

    fun login(onSuccess: () -> Unit) {
        if (!validateLoginForm()) {
            return
        }

        screenModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            // Simulate API call with delay
            delay(1500)

            // TODO: Replace with actual authentication logic
            // For now, accept any valid email/phone with password length >= 8
            val currentState = _state.value

            // Simulating authentication - in production, this would call an API
            if (currentState.password.isNotEmpty()) {
                // Generate a mock authentication token
                val mockToken = "mock_token_${Random.nextLong()}"
                val mockUserId = "user_${Random.nextInt(1000, 9999)}"

                // Save the session
                sessionManager.saveSession(
                    token = mockToken,
                    userId = mockUserId,
                    userEmail = if (isValidEmail(currentState.emailOrPhone)) currentState.emailOrPhone else null,
                    userName = currentState.emailOrPhone.substringBefore("@").takeIf { isValidEmail(currentState.emailOrPhone) }
                )

                _state.value = _state.value.copy(isLoading = false)
                onSuccess()
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Invalid email/phone or password"
                )
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
        return emailRegex.matches(email)
    }

    private fun isValidPhone(phone: String): Boolean {
        // Remove common phone number characters
        val cleanedPhone = phone.replace(Regex("[\\s\\-()]+"), "")
        // Check if it's all digits and has at least 10 digits
        return cleanedPhone.all { it.isDigit() } && cleanedPhone.length >= 10
    }

    fun resetState() {
        _state.value = LoginState()
    }
}
