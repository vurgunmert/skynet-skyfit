package com.vurgun.skyfit.feature_auth.domain.model

sealed class SendOTPResult {
    data object Success : SendOTPResult()
    data class Error(val message: String?) : SendOTPResult()
}