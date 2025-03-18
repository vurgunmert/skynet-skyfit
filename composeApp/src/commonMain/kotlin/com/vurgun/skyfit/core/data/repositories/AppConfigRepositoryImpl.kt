package com.vurgun.skyfit.core.data.repositories

import com.vurgun.skyfit.core.domain.repository.AppConfigRepository

class AppConfigRepositoryImpl : AppConfigRepository {

    override suspend fun isAppInMaintenance(): Boolean {
        return false
    }
}
