package com.vurgun.skyfit.data.onboarding

import com.vurgun.skyfit.data.core.storage.TokenManager
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import com.vurgun.skyfit.data.network.utils.ioResult
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

    suspend fun submitOnboarding2(request: OnboardingRequest, isAccountAddition: Boolean) = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        when {
            isAccountAddition -> apiService.onboardingAdditionalAccount(request, token)
            else -> apiService.onboardNewAccount(request, token)
        }
    }
}