package com.vurgun.skyfit.feature.access.domain

import com.vurgun.skyfit.core.data.domain.repository.AppConfigRepository
import com.vurgun.skyfit.core.data.domain.repository.UserManager

class SplashUseCase(
    private val appConfigRepository: AppConfigRepository,
    private val userManager: UserManager,
) {
    suspend fun execute(): SplashResult = when {
        appConfigRepository.isAppInMaintenance() -> SplashResult.Maintenance
        userManager.getActiveUser(forceRefresh = true).getOrNull() != null -> SplashResult.UserAvailable
        else -> SplashResult.UserNotFound
    }
}
