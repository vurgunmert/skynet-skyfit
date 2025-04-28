package com.vurgun.skyfit.feature.auth.forgotpassword

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.ForgotPasswordOTPResult
import com.vurgun.skyfit.core.data.domain.model.SendOTPResult
import com.vurgun.skyfit.core.data.domain.repository.AuthRepository
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class ForgotPasswordVerifyOTPEffect {
    data object GoToResetPassword : ForgotPasswordVerifyOTPEffect()
    data class ShowError(val message: String?) : ForgotPasswordVerifyOTPEffect()
}

class ForgotPasswordVerifyOTPViewModel(
    private val authRepository: AuthRepository,
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

    private val _effect = SingleSharedFlow<ForgotPasswordVerifyOTPEffect>()
    val effect: SharedFlow<ForgotPasswordVerifyOTPEffect> = _effect

    fun onOtpChanged(otp: String) {
        _enteredOtp.value = otp
        if (otp.length == otpLength) onConfirmClicked()
    }

    fun onConfirmClicked() {
        if (_isLoading.value || _enteredOtp.value.length != otpLength) return

        screenModelScope.launch {
            _isLoading.value = true
            try {
                _effect.emitOrNull(
                    when (val result = authRepository.verifyForgotPasswordOTP(_enteredOtp.value)) {
                        is ForgotPasswordOTPResult.Error -> ForgotPasswordVerifyOTPEffect.ShowError(result.message)
                        ForgotPasswordOTPResult.Success -> ForgotPasswordVerifyOTPEffect.GoToResetPassword
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

        screenModelScope.launch {
            _isLoading.value = true
            try {
                when (val result = authRepository.sendOTP()) {
                    is SendOTPResult.Error -> _effect.emitOrNull(ForgotPasswordVerifyOTPEffect.ShowError(result.message))
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