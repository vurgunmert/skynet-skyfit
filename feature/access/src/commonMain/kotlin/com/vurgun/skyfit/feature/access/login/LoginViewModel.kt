package com.vurgun.skyfit.feature.access.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.access.domain.model.AuthLoginResult
import com.vurgun.skyfit.core.data.access.domain.repository.AuthRepository
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.*
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
            runCatching {
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
            }.onFailure { error ->
                _effect.emitOrNull(LoginEffect.ShowError(error.message))
            }
        }
    }
}
