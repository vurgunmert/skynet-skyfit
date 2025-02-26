package com.vurgun.skyfit.feature_settings.ui.user

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChangePasswordViewModel : ViewModel() {
    private val _currentPassword = MutableStateFlow("")
    val currentPassword: StateFlow<String> = _currentPassword

    private val _newPassword = MutableStateFlow("")
    val newPassword: StateFlow<String> = _newPassword

    private val _confirmedPassword = MutableStateFlow("")
    val confirmedPassword: StateFlow<String> = _confirmedPassword

    private val _isSaveEnabled = MutableStateFlow(false)
    val isSaveEnabled: StateFlow<Boolean> = _isSaveEnabled

    fun updateCurrentPassword(value: String) {
        _currentPassword.value = value
        validateForm()
    }

    fun updateNewPassword(value: String) {
        _newPassword.value = value
        validateForm()
    }

    fun updateConfirmedPassword(value: String) {
        _confirmedPassword.value = value
        validateForm()
    }

    private fun validateForm() {
        _isSaveEnabled.value = _currentPassword.value.isNotEmpty() &&
                _newPassword.value.isNotEmpty() &&
                _confirmedPassword.value.isNotEmpty() &&
                _newPassword.value == _confirmedPassword.value
    }

    fun saveChanged() {
        //TODO: Submit new password
    }
}
