package com.vurgun.skyfit.feature.access.forgotpassword

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.ForgotPasswordResult
import com.vurgun.skyfit.core.data.domain.repository.AuthRepository
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import com.vurgun.skyfit.feature.access.login.LoginEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ForgotPasswordEffect {
    data object GoToOTPVerification : ForgotPasswordEffect()
    data class ShowError(val message: String?) : ForgotPasswordEffect()
}

class ForgotPasswordViewModel(
    private val authRepository: AuthRepository
) : ScreenModel {

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _effect = SingleSharedFlow<ForgotPasswordEffect>()
    val effect: SharedFlow<ForgotPasswordEffect> = _effect

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Derived State
    val isSubmitEnabled: StateFlow<Boolean> = _phoneNumber.map { it.length == 10 }
        .stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), false)

    // Event Handlers
    fun onPhoneNumberChanged(value: String) {
        _phoneNumber.value = value
    }

    fun submitForgotPassword() {
        if (_isLoading.value) return

        screenModelScope.launch {
            _isLoading.value = true
            try {
                val event = when (val result = authRepository.forgotPassword(_phoneNumber.value)) {
                    is ForgotPasswordResult.Error -> ForgotPasswordEffect.ShowError(result.message)
                    ForgotPasswordResult.Success -> ForgotPasswordEffect.GoToOTPVerification
                }
                _effect.emitOrNull(event)
            } finally {
                _isLoading.value = false
            }
        }
    }
}