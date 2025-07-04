package com.vurgun.skyfit.feature.access.login

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.auth.model.AuthorizationOTPResult
import com.vurgun.skyfit.core.data.v1.domain.auth.model.SendOTPResult
import com.vurgun.skyfit.core.data.v1.domain.auth.repository.AuthRepository
import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.utility.UiEventDelegate
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface LoginOTPVerificationEvent {
    data object GoToRegister : LoginOTPVerificationEvent
    data object GoToDashboard : LoginOTPVerificationEvent
    data object GoToOnboarding : LoginOTPVerificationEvent
    data class ShowError(val message: String?) : LoginOTPVerificationEvent
}

class LoginOTPVerificationViewModel(
    private val authRepository: AuthRepository,
    private val userManager: ActiveAccountManager,
    storage: Storage
) : ScreenModel {

    val otpLength = 6

    val phoneNumber = storage.getAsFlow(AuthRepository.UserPhoneNumber).map { it.orEmpty() }

    private val _enteredOtp = MutableStateFlow("")
    val enteredOtp: StateFlow<String> = _enteredOtp

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isResendEnabled = MutableStateFlow(true)
    val isResendEnabled: StateFlow<Boolean> = _isResendEnabled

    private val _countdownTime = MutableStateFlow(30)
    val countdownTime: StateFlow<Int> = _countdownTime

    private val _eventDelegate = UiEventDelegate<LoginOTPVerificationEvent>()
    val eventFlow = _eventDelegate.eventFlow

    fun onOtpChanged(otp: String) {
        _enteredOtp.value = otp
        if (otp.length == otpLength) submitCode()
    }

    fun submitCode() {
        if (_isLoading.value || _enteredOtp.value.length != otpLength) return

        screenModelScope.launch {
            _isLoading.value = true

            runCatching {
                val event = when (val result = authRepository.verifyLoginOTP(_enteredOtp.value)) {
                    is AuthorizationOTPResult.Error -> LoginOTPVerificationEvent.ShowError(result.message)
                    AuthorizationOTPResult.RegistrationRequired -> LoginOTPVerificationEvent.GoToRegister
                    AuthorizationOTPResult.OnboardingRequired -> LoginOTPVerificationEvent.GoToOnboarding
                    AuthorizationOTPResult.LoginSuccess -> {
                        userManager.getActiveUser(true).getOrThrow()
                        LoginOTPVerificationEvent.GoToDashboard
                    }
                }
                _eventDelegate.send(event)
                _isLoading.value = false
            }.onFailure { error ->
                _eventDelegate.send(LoginOTPVerificationEvent.ShowError(error.message))
                _isLoading.value = false
            }
        }
    }

    fun resendOTP() {
        if (!_isResendEnabled.value) return
        _enteredOtp.value = ""

        screenModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = authRepository.sendOTP()) {
                    is SendOTPResult.Error -> _eventDelegate.send(LoginOTPVerificationEvent.ShowError(result.message))
                    SendOTPResult.Success -> startResendCooldown()
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun startResendCooldown() {
        screenModelScope.launch {
            _isResendEnabled.value = false
            _countdownTime.value = 30

            while (_countdownTime.value > 0) {
                delay(1000L)
                _countdownTime.update { it - 1 }
            }

            _isResendEnabled.value = true
        }
    }
}