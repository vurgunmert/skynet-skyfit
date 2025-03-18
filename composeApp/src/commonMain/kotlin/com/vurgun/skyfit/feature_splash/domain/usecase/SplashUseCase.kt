package com.vurgun.skyfit.feature_splash.domain.usecase

import com.vurgun.skyfit.core.domain.repository.AppConfigRepository
import com.vurgun.skyfit.core.domain.repository.UserRepository
import com.vurgun.skyfit.feature_splash.domain.model.SplashResult

class SplashUseCase(
    private val appConfigRepository: AppConfigRepository,
    private val userRepository: UserRepository
) {
    suspend fun execute(): SplashResult {
        if (appConfigRepository.isAppInMaintenance()) {
            return SplashResult.Maintenance
        }

        return userRepository.getUserDetails().fold(
            onSuccess = { SplashResult.UserAvailable },
            onFailure = { SplashResult.UserNotFound }
        )
    }
}
