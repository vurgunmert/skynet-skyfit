package com.vurgun.skyfit.feature_onboarding.domain.repository

import com.vurgun.skyfit.feature_onboarding.data.OnboardingRequest
import com.vurgun.skyfit.feature_onboarding.domain.model.OnboardingResult

interface OnboardingRepository {
    suspend fun completeOnboarding(request: OnboardingRequest): OnboardingResult
}