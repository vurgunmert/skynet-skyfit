package com.vurgun.skyfit.feature_auth.ui.viewmodel

import com.vurgun.skyfit.core.data.models.NetworkResponseWrapper
import com.vurgun.skyfit.feature_auth.domain.model.AuthLoginResult
import com.vurgun.skyfit.feature_auth.domain.usecases.AuthLoginUseCase
import com.vurgun.skyfit.feature_auth.domain.usecases.AuthRequestOTPCodeUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

sealed class OTPVerificationEvent {
    data object GoToRegister : OTPVerificationEvent()
    data object GoToDashboard : OTPVerificationEvent()
    data class ShowError(val message: String) : OTPVerificationEvent()
}

class MobileOTPVerificationViewModel(
    private val authLoginUseCase: AuthLoginUseCase,
    private val authRequestOTPCodeUseCase: AuthRequestOTPCodeUseCase
) : ViewModel() {

    val otpLength = 4

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _enteredOtp = MutableStateFlow("")
    val enteredOtp: StateFlow<String> = _enteredOtp.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isResendEnabled = MutableStateFlow(false)
    val isResendEnabled: StateFlow<Boolean> = _isResendEnabled.asStateFlow()

    private val _countdownTime = MutableStateFlow(30)
    val countdownTime: StateFlow<Int> = _countdownTime.asStateFlow()

    private val _events = MutableSharedFlow<OTPVerificationEvent>()
    val events = _events.asSharedFlow()

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
        startResendCooldown()
    }

    fun onOtpChanged(otp: String) {
        _enteredOtp.value = otp
        if (otp.length == otpLength) {
            onConfirmClicked()
        }
    }

    fun onConfirmClicked() {
        if (_enteredOtp.value.length != otpLength || _isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            val result = authLoginUseCase.execute(_phoneNumber.value, _enteredOtp.value)
            _isLoading.value = false

            when (result) {
                AuthLoginResult.Success -> _events.emit(OTPVerificationEvent.GoToDashboard)
                AuthLoginResult.AwaitingOTPLogin -> resendOTP()
                AuthLoginResult.AwaitingOTPRegister -> _events.emit(OTPVerificationEvent.GoToRegister)
                is AuthLoginResult.Error -> _events.emit(OTPVerificationEvent.ShowError(result.message))
            }
        }
    }

    fun resendOTP() {
        if (!_isResendEnabled.value) return
        _enteredOtp.value = ""

        viewModelScope.launch {
            _isLoading.value = true
            val result = authRequestOTPCodeUseCase.execute(_phoneNumber.value)
            _isLoading.value = false

            when (result) {
                is NetworkResponseWrapper.Success -> {
                    startResendCooldown()
                }

                is NetworkResponseWrapper.Error -> _events.emit(OTPVerificationEvent.ShowError(result.message))
            }
        }
    }

    private fun startResendCooldown() {
        viewModelScope.launch {
            _isResendEnabled.value = false
            _countdownTime.value = 30

            while (_countdownTime.value > 0) {
                delay(1000L)
                _countdownTime.value -= 1
            }

            _isResendEnabled.value = true
        }
    }
}
