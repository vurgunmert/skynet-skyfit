package com.vurgun.skyfit.feature_auth.domain.model

sealed class AuthLoginResult {
    data object Success : AuthLoginResult()
    data object AwaitingOTPLogin : AuthLoginResult()
    data object AwaitingOTPRegister : AuthLoginResult()
    data class Error(val message: String?) : AuthLoginResult()
}
