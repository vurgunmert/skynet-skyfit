package com.vurgun.skyfit.data.onboarding

import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import kotlinx.coroutines.withContext

internal class OnboardingRepositoryImpl(
    private val apiService: OnboardingApiService,
    private val dispatchers: DispatcherProvider,
    private val storage: Storage
) : OnboardingRepository {

    override suspend fun submitOnboarding(request: OnboardingRequest, isAccountAddition: Boolean) = withContext(dispatchers.io) {
        val token = storage.get(UserRepository.UserAuthToken) ?: return@withContext OnboardingResult.Unauthorized

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