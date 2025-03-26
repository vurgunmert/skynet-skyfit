package com.vurgun.skyfit.feature_onboarding.domain.repository

import com.vurgun.skyfit.feature_onboarding.data.OnboardingRequest
import com.vurgun.skyfit.feature_onboarding.domain.model.OnboardingResult

interface OnboardingRepository {
    suspend fun submitOnboarding(request: OnboardingRequest): OnboardingResult
}