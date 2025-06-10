package com.vurgun.skyfit.core.data.v1.data.config.repository

import com.vurgun.skyfit.core.data.v1.domain.config.AppConfigRepository

class AppConfigRepositoryImpl : AppConfigRepository {

    override suspend fun isAppInMaintenance(): Boolean {
        return false
    }
}