package com.vurgun.skyfit.data.onboarding

sealed class OnboardingResult {
    data object Success : OnboardingResult()
    data class Error(val message: String?) : OnboardingResult()
    data object Unauthorized : OnboardingResult()
}