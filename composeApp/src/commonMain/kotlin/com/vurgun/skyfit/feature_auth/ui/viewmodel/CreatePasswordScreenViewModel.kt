package com.vurgun.skyfit.feature_auth.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.feature_auth.domain.model.CreatePasswordResult
import com.vurgun.skyfit.feature_auth.domain.repositories.AuthRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class CreatePasswordViewEvent {
    data object GoToOnboarding : CreatePasswordViewEvent()
    data class Error(val message: String?) : CreatePasswordViewEvent()
}

class CreatePasswordScreenViewModel(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val username = savedStateHandle.getStateFlow("username", "")
    val password = savedStateHandle.getStateFlow("password", "")
    val confirmPassword = savedStateHandle.getStateFlow("confirmPassword", "")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _uiEvents = MutableSharedFlow<CreatePasswordViewEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    val isRegisterEnabled: StateFlow<Boolean> = combine(username, password, confirmPassword, isLoading) { name, pass, confirmPass, loading ->
        name.isNotBlank() && pass.length >= 6 && !loading
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setFullName(name: String) { savedStateHandle["username"] = name }
    fun setPassword(pass: String) { savedStateHandle["password"] = pass }
    fun setConfirmPassword(confirmPass: String) { savedStateHandle["confirmPassword"] = confirmPass }

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
