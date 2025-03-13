package com.vurgun.skyfit.feature_auth.domain.model

sealed class AuthOTPResult {
    data object LoginSuccess : AuthOTPResult()
    data object RegisterSuccess : AuthOTPResult()
    data class Error(val message: String?) : AuthOTPResult()
}
