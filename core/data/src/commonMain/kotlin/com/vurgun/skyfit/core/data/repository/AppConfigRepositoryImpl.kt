package com.vurgun.skyfit.core.data.repository

import com.vurgun.skyfit.core.data.domain.repository.AppConfigRepository

class AppConfigRepositoryImpl : AppConfigRepository {

    override suspend fun isAppInMaintenance(): Boolean {
        return false
    }
}
