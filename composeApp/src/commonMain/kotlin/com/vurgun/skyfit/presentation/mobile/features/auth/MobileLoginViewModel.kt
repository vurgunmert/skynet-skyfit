package com.vurgun.skyfit.presentation.mobile.features.auth

import com.vurgun.skyfit.domain.usecases.auth.AuthenticatePhoneNumberUseCase
import com.vurgun.skyfit.domain.usecases.auth.AuthLoginResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

sealed class MobileLoginNavigation {
    data object GoToDashboard : MobileLoginNavigation()
    data object GoToOTPVerification : MobileLoginNavigation()
    data class ShowError(val message: String) : MobileLoginNavigation()
}

class MobileLoginViewModel(
    private val authenticatePhoneNumberUseCase: AuthenticatePhoneNumberUseCase
) : ViewModel() {

    private val _phoneNumber = MutableStateFlow("5555555555")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    val isLoginEnabled: StateFlow<Boolean> = _phoneNumber.map { it.length == 10 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<MobileLoginNavigation>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun onPhoneNumberChanged(newValue: String) {
        _phoneNumber.value = newValue
    }

    fun onLoginClicked() {
        if (_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            val result = authenticatePhoneNumberUseCase.execute(_phoneNumber.value)
            _isLoading.value = false

            when (result) {
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
        }
    }
}
