package com.vurgun.skyfit.domain.usecases.auth

sealed class AuthLoginResult {
    data object AwaitingOTPLogin : AuthLoginResult()
    data object AwaitingOTPRegister : AuthLoginResult()
    data class Error(val message: String) : AuthLoginResult()
}
