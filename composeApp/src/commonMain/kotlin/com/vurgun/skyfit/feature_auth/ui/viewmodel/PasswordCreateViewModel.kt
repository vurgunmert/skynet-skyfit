package com.vurgun.skyfit.feature_auth.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.feature_auth.domain.model.CreatePasswordResult
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

sealed class CreatePasswordViewEvent {
    data object GoToOnboarding : CreatePasswordViewEvent()
    data class Error(val message: String?) : CreatePasswordViewEvent()
}

class PasswordCreateViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> = _username
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password
    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uiEvents = MutableSharedFlow<CreatePasswordViewEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    val isRegisterEnabled: StateFlow<Boolean> =
        combine(username, password, confirmPassword, isLoading) { name, pass, confirmPass, loading ->
            name.isNotBlank() && pass.length >= 6 && !loading
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setFullName(name: String) {
        _username.value = name
    }

    fun setPassword(pass: String) {
        _password.value = pass
    }

    fun setConfirmPassword(confirmPass: String) {
        _confirmPassword.value = confirmPass
    }

    fun onRegisterClicked() {
        if (!isRegisterEnabled.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = authRepository.createPassword(
                    username = username.value,
                    password = password.value,
                    againPassword = confirmPassword.value
                )
                when (result) {
                    is CreatePasswordResult.Error -> _uiEvents.emit(CreatePasswordViewEvent.Error(result.message))
                    CreatePasswordResult.Success -> _uiEvents.emit(CreatePasswordViewEvent.GoToOnboarding)
                }
            } finally {
                _isLoading.value = false
            }
        }
    }
}
