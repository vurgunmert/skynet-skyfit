package com.vurgun.skyfit.data.onboarding

import com.vurgun.skyfit.data.core.storage.LocalSettingsStore
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import kotlinx.coroutines.withContext

internal class OnboardingRepositoryImpl(
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