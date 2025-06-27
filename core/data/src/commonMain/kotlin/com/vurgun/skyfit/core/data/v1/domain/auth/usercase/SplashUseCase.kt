package com.vurgun.skyfit.core.data.v1.domain.auth.usercase

import com.vurgun.skyfit.core.data.v1.domain.account.manager.ActiveAccountManager
import com.vurgun.skyfit.core.data.v1.domain.auth.model.SplashResult
import com.vurgun.skyfit.core.data.v1.domain.config.AppConfigRepository

class SplashUseCase(
    private val appConfigRepository: AppConfigRepository,
    private val userManager: ActiveAccountManager,
) {


    suspend fun execute(): SplashResult = when {
        appConfigRepository.isAppInMaintenance() -> SplashResult.Maintenance
        userManager.getActiveUser(forceRefresh = true).getOrNull() != null -> SplashResult.UserAvailable
        else -> SplashResult.UserNotFound
    }
}