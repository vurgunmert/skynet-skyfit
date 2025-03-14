package com.vurgun.skyfit.feature_auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import com.vurgun.skyfit.feature_auth.domain.model.ForgotPasswordOTPResult
import com.vurgun.skyfit.feature_auth.domain.model.SendOTPResult
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class ForgotPasswordVerifyOTPViewEvent {
    data object GoToResetPassword : ForgotPasswordVerifyOTPViewEvent()
    data class ShowError(val message: String?) : ForgotPasswordVerifyOTPViewEvent()
}

class ForgotPasswordVerifyOTPViewModel(
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

    private val _events = MutableSharedFlow<ForgotPasswordVerifyOTPViewEvent>()
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
                    when (val result = authRepository.verifyForgotPasswordOTP(_enteredOtp.value)) {
                        is ForgotPasswordOTPResult.Error -> ForgotPasswordVerifyOTPViewEvent.ShowError(result.message)
                        ForgotPasswordOTPResult.Success -> ForgotPasswordVerifyOTPViewEvent.GoToResetPassword
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
                    is SendOTPResult.Error -> _events.emit(ForgotPasswordVerifyOTPViewEvent.ShowError(result.message))
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