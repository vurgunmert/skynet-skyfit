package com.vurgun.skyfit.feature.access.forgotpassword

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.v1.domain.auth.model.ResetPasswordResult
import com.vurgun.skyfit.core.data.v1.domain.auth.repository.AuthRepository
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitOrNull
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class PasswordResetViewEvent {
    data object GoToDashboard : PasswordResetViewEvent()
    data class Error(val message: String?) : PasswordResetViewEvent()
}

class PasswordResetViewModel(
    private val authRepository: AuthRepository
) : ScreenModel {

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _effect = SingleSharedFlow<PasswordResetViewEvent>()
    val effect: SharedFlow<PasswordResetViewEvent> = _effect

    val isSubmitEnabled: StateFlow<Boolean> = combine(password, confirmPassword, isLoading) { pass, confirmPass, loading ->
        pass.length >= 6 && !loading
    }.stateIn(screenModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setPassword(pass: String) {
        _password.value = pass
    }

    fun setConfirmPassword(confirmPass: String) {
        _confirmPassword.value = confirmPass
    }

    fun submitPasswordReset() {
        if (!isSubmitEnabled.value) return

        screenModelScope.launch {
            _isLoading.value = true
            try {
                val result = authRepository.resetPassword(
                    password = password.value,
                    againPassword = confirmPassword.value
                )
                when (result) {
                    is ResetPasswordResult.Error -> _effect.emitOrNull(PasswordResetViewEvent.Error(result.message))
                    ResetPasswordResult.Success -> _effect.emitOrNull(PasswordResetViewEvent.GoToDashboard)
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
