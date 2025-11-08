package com.elixirgym.elixir.presentation.viewmodels

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.elixirgym.elixir.data.repository.ISessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class AccountCreationState(
    val name: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val idNumber: String = "",
    val address: String = "",
    val medicalInfo: String = "",
    val agreedToTerms: Boolean = false,
    val otpCode: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val passwordError: String? = null,
    val idNumberError: String? = null
)

class AccountCreationViewModel(
    private val sessionManager: ISessionManager
) : ScreenModel {
    private val _state = MutableStateFlow(AccountCreationState())
    val state: StateFlow<AccountCreationState> = _state.asStateFlow()

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name, nameError = null)
    }

    fun updateEmail(email: String) {
        _state.value = _state.value.copy(email = email, emailError = null)
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _state.value = _state.value.copy(phoneNumber = phoneNumber, phoneError = null)
    }

    fun updatePassword(password: String) {
        _state.value = _state.value.copy(password = password, passwordError = null)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _state.value = _state.value.copy(confirmPassword = confirmPassword, passwordError = null)
    }

    fun updateIdNumber(idNumber: String) {
        _state.value = _state.value.copy(idNumber = idNumber, idNumberError = null)
    }

    fun updateAddress(address: String) {
        _state.value = _state.value.copy(address = address)
    }

    fun updateMedicalInfo(medicalInfo: String) {
        _state.value = _state.value.copy(medicalInfo = medicalInfo)
    }

    fun updateOtpCode(otpCode: String) {
        if (otpCode.length <= 6 && otpCode.all { it.isDigit() }) {
            _state.value = _state.value.copy(otpCode = otpCode)
        }
    }

    fun validateSignUpForm(): Boolean {
        var isValid = true
        val currentState = _state.value

        // Name validation
        if (currentState.name.isBlank()) {
            _state.value = _state.value.copy(nameError = "Name is required")
            isValid = false
        } else if (currentState.name.length < 2) {
            _state.value = _state.value.copy(nameError = "Name must be at least 2 characters")
            isValid = false
        }

        // Email validation
        if (currentState.email.isBlank()) {
            _state.value = _state.value.copy(emailError = "Email is required")
            isValid = false
        } else if (!isValidEmail(currentState.email)) {
            _state.value = _state.value.copy(emailError = "Please enter a valid email address")
            isValid = false
        }

        // Phone validation
        if (currentState.phoneNumber.isBlank()) {
            _state.value = _state.value.copy(phoneError = "Phone number is required")
            isValid = false
        } else if (currentState.phoneNumber.length < 10) {
            _state.value = _state.value.copy(phoneError = "Please enter a valid phone number")
            isValid = false
        }

        // Password validation
        if (currentState.password.isBlank()) {
            _state.value = _state.value.copy(passwordError = "Password is required")
            isValid = false
        } else if (currentState.password.length < 8) {
            _state.value = _state.value.copy(passwordError = "Password must be at least 8 characters")
            isValid = false
        } else if (currentState.password != currentState.confirmPassword) {
            _state.value = _state.value.copy(passwordError = "Passwords do not match")
            isValid = false
        }

        // ID Number validation
        if (currentState.idNumber.isBlank()) {
            _state.value = _state.value.copy(idNumberError = "ID number is required")
            isValid = false
        }

        return isValid
    }

    fun agreeToTerms() {
        _state.value = _state.value.copy(agreedToTerms = true)
    }

    fun verifyOtp(onSuccess: () -> Unit) {
        screenModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, errorMessage = null)

            // Simulate OTP verification
            kotlinx.coroutines.delay(1500)

            if (_state.value.otpCode == "123456" || _state.value.otpCode.length == 6) {
                // Generate a mock authentication token
                val mockToken = "mock_token_${Random.nextLong()}"
                val mockUserId = "user_${Random.nextInt(1000, 9999)}"

                // Save the session
                sessionManager.saveSession(
                    token = mockToken,
                    userId = mockUserId,
                    userEmail = _state.value.email,
                    userName = _state.value.name
                )

                _state.value = _state.value.copy(isLoading = false)
                onSuccess()
            } else {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Invalid OTP code. Please try again."
                )
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
        return emailRegex.matches(email)
    }

    fun resetState() {
        _state.value = AccountCreationState()
    }
}
