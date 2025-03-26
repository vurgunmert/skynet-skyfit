package com.vurgun.skyfit.feature_onboarding.data.repository

import com.vurgun.skyfit.core.network.model.ApiResult
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import com.vurgun.skyfit.core.utils.DispatcherProvider
import com.vurgun.skyfit.feature_onboarding.data.OnboardingRequest
import com.vurgun.skyfit.feature_onboarding.data.service.OnboardingApiService
import com.vurgun.skyfit.feature_onboarding.domain.model.OnboardingResult
import com.vurgun.skyfit.feature_onboarding.domain.repository.OnboardingRepository
import kotlinx.coroutines.withContext

class OnboardingRepositoryImpl(
    private val settingsStore: LocalSettingsStore,
    private val apiService: OnboardingApiService,
    private val dispatchers: DispatcherProvider
) : OnboardingRepository {

    override suspend fun submitOnboarding(request: OnboardingRequest) = withContext(dispatchers.io) {
        val token = settingsStore.getToken() ?: return@withContext OnboardingResult.Unauthorized

        when (val response = apiService.onboardUser(request, token)) {
            is ApiResult.Error -> OnboardingResult.Error(response.message)
            is ApiResult.Exception -> OnboardingResult.Error(response.exception.message)
            is ApiResult.Success -> OnboardingResult.Success
        }
    }
}