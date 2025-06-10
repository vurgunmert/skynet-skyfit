package com.vurgun.skyfit.core.data.v1.domain.account.model

sealed class AccountOnboardingResult {
    data object Success : AccountOnboardingResult()
    data class Error(val message: String?) : AccountOnboardingResult()
    data object Unauthorized : AccountOnboardingResult()
}