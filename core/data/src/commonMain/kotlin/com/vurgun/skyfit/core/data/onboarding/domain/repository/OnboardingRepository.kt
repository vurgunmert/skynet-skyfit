package com.vurgun.skyfit.core.data.onboarding.domain.repository

import com.vurgun.skyfit.core.data.onboarding.data.model.OnboardingRequest
import com.vurgun.skyfit.core.data.onboarding.domain.model.OnboardingResult

interface OnboardingRepository {
    suspend fun submitOnboarding(request: OnboardingRequest, isAccountAddition: Boolean): OnboardingResult
}