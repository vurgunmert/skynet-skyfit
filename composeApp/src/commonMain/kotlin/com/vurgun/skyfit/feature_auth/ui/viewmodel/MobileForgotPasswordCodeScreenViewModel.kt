package com.vurgun.skyfit.feature_auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MobileForgotPasswordCodeScreenViewModel : ViewModel() {
    private val _otpState = MutableStateFlow(OtpState()) // Holds entire OTP state
    val otpState: StateFlow<OtpState> get() = _otpState

    fun updateOtp(index: Int, value: String) {
        if (value.length <= 1) {
            _otpState.update { state ->
                val newOtp = state.otp.toMutableList()
                newOtp[index] = value
                state.copy(otp = newOtp, isCodeReady = newOtp.all { it.isNotEmpty() })
            }
        }
    }

    fun deleteOtp(index: Int) {
        if (index >= 0) {
            _otpState.update { state ->
                val newOtp = state.otp.toMutableList()
                newOtp[index] = ""
                state.copy(otp = newOtp, isCodeReady = newOtp.all { it.isNotEmpty() })
            }
        }
    }

    fun submitCode() {
        if (_otpState.value.isCodeReady) {
            val enteredCode = _otpState.value.otp.joinToString("")
            println("Submitting OTP: $enteredCode")
            // Handle API call here
        }
    }
}

data class OtpState(
    val otp: List<String> = List(4) { "" },
    val isCodeReady: Boolean = false
)
