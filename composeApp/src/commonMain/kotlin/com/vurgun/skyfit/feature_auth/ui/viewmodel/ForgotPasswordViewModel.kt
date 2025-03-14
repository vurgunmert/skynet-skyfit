package com.vurgun.skyfit.feature_auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.feature_auth.domain.model.ForgotPasswordResult
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class ForgotPasswordViewEvent {
    data object GoToOTPVerification : ForgotPasswordViewEvent()
    data class ShowError(val message: String?) : ForgotPasswordViewEvent()
}

class ForgotPasswordViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber

    private val _uiEvents = MutableSharedFlow<ForgotPasswordViewEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Derived State
    val isSubmitEnabled: StateFlow<Boolean> = _phoneNumber.map { it.length == 10 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // Event Handlers
    fun onPhoneNumberChanged(value: String) {
        _phoneNumber.value = value
    }

    fun submitForgotPassword() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val event = when (val result = authRepository.forgotPassword(_phoneNumber.value)) {
                    is ForgotPasswordResult.Error -> ForgotPasswordViewEvent.ShowError(result.message)
                    ForgotPasswordResult.Success -> ForgotPasswordViewEvent.GoToOTPVerification
                }
                _uiEvents.emit(event)
            } finally {
                _isLoading.value = false
            }
        }
    }
}