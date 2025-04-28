package com.vurgun.skyfit.feature.auth.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.AuthLoginResult
import com.vurgun.skyfit.core.data.domain.repository.AuthRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class LoginEffect {
    data object GoToDashboard : LoginEffect()
    data object GoToOTPVerification : LoginEffect()
    data object GoToOnboarding : LoginEffect()
    data class ShowError(val message: String?) : LoginEffect()
}

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val userManager: UserManager,
) : ScreenModel {

    // State
    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible: StateFlow<Boolean> = _isPasswordVisible

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _effect = SingleSharedFlow<LoginEffect>()
    val effect: SharedFlow<LoginEffect> = _effect

    val isLoginEnabled: StateFlow<Boolean> = _phoneNumber.map { it.length == 10 }
        .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun onPhoneNumberChanged(value: String) {
        _phoneNumber.value = value.trim()
    }

    fun onPasswordChanged(value: String) {
        _password.value = value.trim()
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun submitLogin() {
        if (_isLoading.value) return

        screenModelScope.launch {
            _isLoading.value = true
            try {
                val event = when (val result = authRepository.login(_phoneNumber.value, _password.value)) {
                    is AuthLoginResult.Success -> {
                        try {
                            userManager.getActiveUser(true).getOrThrow()
                            LoginEffect.GoToDashboard
                        } catch (e: Exception) {
                            LoginEffect.ShowError(e.message)
                        }
                    }

                    is AuthLoginResult.OTPVerificationRequired -> LoginEffect.GoToOTPVerification
                    is AuthLoginResult.OnboardingRequired -> LoginEffect.GoToOnboarding
                    is AuthLoginResult.Error -> LoginEffect.ShowError(result.message)
                }
                _effect.emitOrNull(event)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
