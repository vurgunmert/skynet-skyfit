package com.vurgun.skyfit.settings.shared.changepassword

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.domain.auth.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class PasswordSettingsUiState {
    data object Idle : PasswordSettingsUiState()
    data object Loading : PasswordSettingsUiState()
    data class Error(val message: String?) : PasswordSettingsUiState()
}

sealed interface PasswordSettingsUiAction {
    data object OnClickBack : PasswordSettingsUiAction
    data object OnClickSubmit : PasswordSettingsUiAction
    data class OnCurrentPasswordChanged(val value: String) : PasswordSettingsUiAction
    data class OnNewPasswordChanged(val value: String) : PasswordSettingsUiAction
    data class OnAgainPasswordChanged(val value: String) : PasswordSettingsUiAction
}

sealed interface PasswordSettingsUiEffect {
    data object NavigateBack : PasswordSettingsUiEffect
}

class PasswordSettingsViewModel(
    private val authRepository: AuthRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<PasswordSettingsUiState>(PasswordSettingsUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private val _uiEffect = SingleSharedFlow<PasswordSettingsUiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private val _currentPassword = MutableStateFlow("")
    val currentPassword: StateFlow<String> = _currentPassword

    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword

    private val _confirmedPassword = MutableStateFlow("")
    val confirmedPassword: StateFlow<String> = _confirmedPassword

    private val _isSaveEnabled = MutableStateFlow(false)
    val isSaveEnabled: StateFlow<Boolean> = _isSaveEnabled

    fun onAction(action: PasswordSettingsUiAction) {
        when (action) {
            PasswordSettingsUiAction.OnClickBack ->
                _uiEffect.emitIn(screenModelScope, PasswordSettingsUiEffect.NavigateBack)

            PasswordSettingsUiAction.OnClickSubmit -> submitPasswordChange()
            is PasswordSettingsUiAction.OnCurrentPasswordChanged -> updateCurrentPassword(action.value)
            is PasswordSettingsUiAction.OnNewPasswordChanged -> updateNewPassword(action.value)
            is PasswordSettingsUiAction.OnAgainPasswordChanged -> updateConfirmedPassword(action.value)
        }
    }

    private fun updateCurrentPassword(value: String) {
        _currentPassword.value = value
        validateForm()
    }

    private fun updateNewPassword(value: String) {
        _newPassword.value = value
        validateForm()
    }

    private fun updateConfirmedPassword(value: String) {
        _confirmedPassword.value = value
        validateForm()
    }

    private fun validateForm() {
        _isSaveEnabled.value = _currentPassword.value.isNotEmpty() &&
                _newPassword.value.isNotEmpty() &&
                _confirmedPassword.value.isNotEmpty() &&
                _newPassword.value == _confirmedPassword.value
    }

    private fun submitPasswordChange() {
        _uiState.update(PasswordSettingsUiState.Loading)
        screenModelScope.launch {
            runCatching {
                authRepository.changePassword(
                    old = currentPassword.value,
                    new = newPassword.value,
                    again = confirmedPassword.value
                ).getOrThrow()
                _uiEffect.emitIn(screenModelScope, PasswordSettingsUiEffect.NavigateBack)
            }.onFailure { error ->
                _uiState.update(PasswordSettingsUiState.Error(error.message))
            }
        }
    }
}
