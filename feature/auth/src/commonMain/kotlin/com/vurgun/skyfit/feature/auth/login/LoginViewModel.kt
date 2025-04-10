package com.vurgun.skyfit.feature.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.auth.domain.model.AuthLoginResult
import com.vurgun.skyfit.data.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class LoginViewEvent {
    data object GoToDashboard : LoginViewEvent()
    data object GoToOTPVerification : LoginViewEvent()
    data object GoToOnboarding : LoginViewEvent()
    data class ShowError(val message: String?) : LoginViewEvent()
}

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    // State
    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _uiEvents = MutableSharedFlow<LoginViewEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    // Derived State
    val isLoginEnabled: StateFlow<Boolean> = _phoneNumber.map { it.length == 10 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // Event Handlers
    fun onPhoneNumberChanged(value: String) {
        _phoneNumber.value = value
    }

    fun onPasswordChanged(value: String) {
        _password.value = value
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun submitLogin() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val event = when (val result = authRepository.login(_phoneNumber.value, _password.value)) {
                    is AuthLoginResult.Success -> LoginViewEvent.GoToDashboard
                    is AuthLoginResult.OTPVerificationRequired -> LoginViewEvent.GoToOTPVerification
                    is AuthLoginResult.OnboardingRequired -> LoginViewEvent.GoToOnboarding
                    is AuthLoginResult.Error -> LoginViewEvent.ShowError(result.message)
                }
                _uiEvents.emit(event)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
