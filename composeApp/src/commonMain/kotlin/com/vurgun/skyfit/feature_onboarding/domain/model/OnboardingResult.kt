package com.vurgun.skyfit.feature_onboarding.domain.model

sealed class OnboardingResult {
    data object Success : OnboardingResult()
    data class Error(val message: String?) : OnboardingResult()
}