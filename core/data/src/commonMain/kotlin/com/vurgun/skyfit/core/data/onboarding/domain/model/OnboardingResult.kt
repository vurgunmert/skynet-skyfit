package com.vurgun.skyfit.core.data.onboarding.domain.model

sealed class OnboardingResult {
    data object Success : OnboardingResult()
    data class Error(val message: String?) : OnboardingResult()
    data object Unauthorized : OnboardingResult()
}