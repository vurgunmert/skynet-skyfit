package com.vurgun.skyfit.core.data.onboarding

interface OnboardingRepository {
    suspend fun submitOnboarding(request: OnboardingRequest, isAccountAddition: Boolean): OnboardingResult
}