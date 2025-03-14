package com.vurgun.skyfit.feature_auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import com.vurgun.skyfit.feature_auth.domain.model.AuthorizationOTPResult
import com.vurgun.skyfit.feature_auth.domain.model.SendOTPResult
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class LoginOTPVerificationViewEvent {
    data object GoToRegister : LoginOTPVerificationViewEvent()
    data object GoToDashboard : LoginOTPVerificationViewEvent()
    data class ShowError(val message: String?) : LoginOTPVerificationViewEvent()
}

class LoginOTPVerificationViewModel(
    private val authRepository: AuthRepository,
    localSettingsStore: LocalSettingsStore
) : ViewModel() {

    val otpLength = 6
    val phoneNumber = localSettingsStore.getPhoneNumber().orEmpty()

    private val _enteredOtp = MutableStateFlow("")
    val enteredOtp: StateFlow<String> = _enteredOtp

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isResendEnabled = MutableStateFlow(true)
    val isResendEnabled: StateFlow<Boolean> = _isResendEnabled

    private val _countdownTime = MutableStateFlow(30)
    val countdownTime: StateFlow<Int> = _countdownTime

    private val _events = MutableSharedFlow<LoginOTPVerificationViewEvent>()
    val events = _events.asSharedFlow()

    fun onOtpChanged(otp: String) {
        _enteredOtp.value = otp
        if (otp.length == otpLength) onConfirmClicked()
    }

    fun onConfirmClicked() {
        if (_isLoading.value || _enteredOtp.value.length != otpLength) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                _events.emit(
                    when (val result = authRepository.verifyAuthOTP(_enteredOtp.value)) {
                        is AuthorizationOTPResult.Error -> LoginOTPVerificationViewEvent.ShowError(result.message)
                        AuthorizationOTPResult.RegistrationRequired -> LoginOTPVerificationViewEvent.GoToRegister
                        AuthorizationOTPResult.LoginSuccess -> LoginOTPVerificationViewEvent.GoToDashboard
                    }
                )
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resendOTP() {
        if (!_isResendEnabled.value) return
        _enteredOtp.value = ""

        viewModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = authRepository.sendOTP()) {
                    is SendOTPResult.Error -> _events.emit(LoginOTPVerificationViewEvent.ShowError(result.message))
                    SendOTPResult.Success -> startResendCooldown()
                }
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun startResendCooldown() {
        viewModelScope.launch {
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
