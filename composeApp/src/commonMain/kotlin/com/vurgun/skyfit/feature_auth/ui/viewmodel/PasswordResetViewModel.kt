package com.vurgun.skyfit.feature_auth.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.feature_auth.domain.model.ResetPasswordResult
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class PasswordResetViewEvent {
    data object GoToDashboard : PasswordResetViewEvent()
    data class Error(val message: String?) : PasswordResetViewEvent()
}

class PasswordResetViewModel(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val password = savedStateHandle.getStateFlow("password", "")
    val confirmPassword = savedStateHandle.getStateFlow("confirmPassword", "")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uiEvents = MutableSharedFlow<PasswordResetViewEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    val isSubmitEnabled: StateFlow<Boolean> = combine(password, confirmPassword, isLoading) { pass, confirmPass, loading ->
        pass.length >= 6 && !loading
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setPassword(pass: String) {
        savedStateHandle["password"] = pass
    }

    fun setConfirmPassword(confirmPass: String) {
        savedStateHandle["confirmPassword"] = confirmPass
    }

    fun submitPasswordReset() {
        if (!isSubmitEnabled.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = authRepository.resetPassword(
                    password = password.value,
                    againPassword = confirmPassword.value
                )
                when (result) {
                    is ResetPasswordResult.Error -> _uiEvents.emit(PasswordResetViewEvent.Error(result.message))
                    ResetPasswordResult.Success -> _uiEvents.emit(PasswordResetViewEvent.GoToDashboard)
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
