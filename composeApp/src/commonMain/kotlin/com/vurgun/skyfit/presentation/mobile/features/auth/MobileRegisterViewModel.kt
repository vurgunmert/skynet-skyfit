package com.vurgun.skyfit.presentation.mobile.features.auth

import com.vurgun.skyfit.domain.usecases.auth.RegisterUserUseCase
import com.vurgun.skyfit.domain.util.ResultWrapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

sealed class MobileRegisterNavigation {
    data object GoToOnboarding : MobileRegisterNavigation()
}

class MobileRegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _phoneNumber = MutableStateFlow("")
    val phoneNumber: StateFlow<String> = _phoneNumber.asStateFlow()

    private val _fullName = MutableStateFlow("")
    val fullName: StateFlow<String> = _fullName.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword: StateFlow<String> = _confirmPassword.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    private val _confirmPasswordError = MutableStateFlow<String?>(null)
    val confirmPasswordError: StateFlow<String?> = _confirmPasswordError.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _navigationEvents = MutableSharedFlow<MobileRegisterNavigation>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    val isRegisterEnabled: StateFlow<Boolean> = combine(
        fullName, password, confirmPassword, isLoading
    ) { name, pass, confirmPass, loading ->
        name.isNotBlank() &&
                pass.length >= 6 &&
                confirmPass == pass &&
                !loading
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun setFullName(name: String) {
        _fullName.value = name
    }

    fun setPassword(pass: String) {
        _password.value = pass
        validatePassword(pass)
    }

    fun setConfirmPassword(confirmPass: String) {
        _confirmPassword.value = confirmPass
        validateConfirmPassword(confirmPass)
    }

    private fun validatePassword(pass: String) {
        _passwordError.value = if (pass.length < 6) "Şifre en az 6 karakter olmalıdır" else null
    }

    private fun validateConfirmPassword(confirmPass: String) {
        _confirmPasswordError.value = if (confirmPass != _password.value) "Şifreler eşleşmiyor" else null
    }

    fun onRegisterClicked() {
        if (isRegisterEnabled.value) {
            _isLoading.value = true

            viewModelScope.launch {
                val result = registerUserUseCase.execute(
                    phoneNumber = _phoneNumber.value,
                    fullName = _fullName.value,
                    password = _password.value
                )

                _isLoading.value = false

                when (result) {
                    is ResultWrapper.Success -> {
                        _navigationEvents.emit(MobileRegisterNavigation.GoToOnboarding)
                    }
                    is ResultWrapper.Error -> {
                        _passwordError.value = result.message
                    }
                }
            }
        }
    }
}
