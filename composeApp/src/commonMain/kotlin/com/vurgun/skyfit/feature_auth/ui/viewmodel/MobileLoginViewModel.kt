package com.vurgun.skyfit.feature_auth.ui.viewmodel

import com.vurgun.skyfit.core.data.models.NetworkResponseWrapper
import com.vurgun.skyfit.feature_auth.domain.model.AuthLoginResult
import com.vurgun.skyfit.feature_auth.domain.usecases.AuthLoginUseCase
import com.vurgun.skyfit.feature_auth.domain.usecases.AuthRequestOTPCodeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

sealed class MobileLoginNavigation {
    data object GoToDashboard : MobileLoginNavigation()
    data object GoToOTPVerification : MobileLoginNavigation()
    data class ShowError(val message: String) : MobileLoginNavigation()
}

class MobileLoginViewModel(
    private val authLoginUseCase: AuthLoginUseCase,
    private val authRequestOTPCodeUseCase: AuthRequestOTPCodeUseCase
) : ViewModel() {

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    val isLoginEnabled: StateFlow<Boolean> = _phoneNumber.map { it.length == 10 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _isPasswordEnabled = MutableStateFlow(false)
    val isPasswordEnabled: StateFlow<Boolean> = _isPasswordEnabled.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<MobileLoginNavigation>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun onPhoneNumberChanged(newValue: String) {
        _phoneNumber.value = newValue
    }

    fun onPasswordChanged(newValue: String) {
        _password.value = newValue
    }

    fun togglePassword() {
        _isPasswordEnabled.value = true
    }

    fun onLoginClicked() {
        if (_isLoading.value) return

        viewModelScope.launch {

            _isLoading.value = true
            if (_isPasswordEnabled.value) {

                when (val result = authLoginUseCase.execute(_phoneNumber.value, _password.value)) {
                    is AuthLoginResult.Success -> {
                        _navigationEvents.emit(MobileLoginNavigation.GoToDashboard)
                    }
                    is AuthLoginResult.AwaitingOTPRegister -> {
                        _navigationEvents.emit(MobileLoginNavigation.GoToOTPVerification)
                    }
                    is AuthLoginResult.AwaitingOTPLogin -> {
                        _navigationEvents.emit(MobileLoginNavigation.GoToOTPVerification)
                    }
                    is AuthLoginResult.Error -> {
                        _navigationEvents.emit(MobileLoginNavigation.ShowError(result.message))
                    }
                }

            } else {
                when (val result = authRequestOTPCodeUseCase.execute(_phoneNumber.value)) {
                    is NetworkResponseWrapper.Error -> {
                        _navigationEvents.emit(MobileLoginNavigation.ShowError(result.message))
                    }

                    is NetworkResponseWrapper.Success -> {
                        _navigationEvents.emit(MobileLoginNavigation.GoToOTPVerification)
                    }
                }
            }
            _isLoading.value = false
        }
    }
}
