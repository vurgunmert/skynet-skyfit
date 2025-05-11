package com.vurgun.skyfit.core.data.access.data.repository

import com.vurgun.skyfit.core.data.access.domain.repository.AppConfigRepository

class AppConfigRepositoryImpl : AppConfigRepository {

    override suspend fun isAppInMaintenance(): Boolean {
        return false
    }
}
