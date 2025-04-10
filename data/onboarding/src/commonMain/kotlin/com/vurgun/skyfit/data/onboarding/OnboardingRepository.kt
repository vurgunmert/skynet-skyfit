package com.vurgun.skyfit.data.onboarding

interface OnboardingRepository {
    suspend fun submitOnboarding(request: OnboardingRequest): OnboardingResult
}