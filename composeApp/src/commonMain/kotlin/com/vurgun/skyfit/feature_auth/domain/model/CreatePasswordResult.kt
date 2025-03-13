package com.vurgun.skyfit.feature_auth.domain.model

sealed class CreatePasswordResult {
    data object Success : CreatePasswordResult()
    data class Error(val message: String?) : CreatePasswordResult()
}