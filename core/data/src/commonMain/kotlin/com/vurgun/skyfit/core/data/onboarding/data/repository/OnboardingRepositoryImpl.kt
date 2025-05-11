package com.vurgun.skyfit.core.data.onboarding.data.repository

import com.vurgun.skyfit.core.data.onboarding.domain.repository.OnboardingRepository
import com.vurgun.skyfit.core.data.onboarding.data.model.OnboardingRequest
import com.vurgun.skyfit.core.data.onboarding.domain.model.OnboardingResult
import com.vurgun.skyfit.core.data.onboarding.data.service.OnboardingApiService
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.network.DispatcherProvider
import kotlinx.coroutines.withContext

internal class OnboardingRepositoryImpl(
    private val apiService: OnboardingApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : OnboardingRepository {

    override suspend fun submitOnboarding(request: OnboardingRequest, isAccountAddition: Boolean) = withContext(dispatchers.io) {
        val token = tokenManager.getTokenOrNull() ?: return@withContext OnboardingResult.Unauthorized

        val response = when {
            isAccountAddition -> apiService.onboardingAdditionalAccount(request, token)
            else -> apiService.onboardNewAccount(request, token)
        }
        when (response) {
            is ApiResult.Error -> OnboardingResult.Error(response.message)
            is ApiResult.Exception -> OnboardingResult.Error(response.exception.message)
            is ApiResult.Success -> OnboardingResult.Success
        }
    }
}