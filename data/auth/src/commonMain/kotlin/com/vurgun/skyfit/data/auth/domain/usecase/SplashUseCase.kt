package com.vurgun.skyfit.data.auth.domain.usecase

import com.vurgun.skyfit.data.auth.domain.model.SplashResult
import com.vurgun.skyfit.data.auth.domain.repository.AppConfigRepository
import com.vurgun.skyfit.data.core.domain.manager.UserManager

class SplashUseCase(
    private val appConfigRepository: AppConfigRepository,
    private val userManager: UserManager,
) {
    suspend fun execute(): SplashResult = when {
        appConfigRepository.isAppInMaintenance() -> SplashResult.Maintenance
        userManager.getActiveUser(forceRefresh = true).isSuccess -> SplashResult.UserAvailable
        else -> SplashResult.UserNotFound
    }
}
