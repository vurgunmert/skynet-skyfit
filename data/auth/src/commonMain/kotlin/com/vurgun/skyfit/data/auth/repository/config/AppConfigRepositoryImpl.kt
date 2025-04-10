package com.vurgun.skyfit.data.auth.repository.config

import com.vurgun.skyfit.data.auth.domain.repository.AppConfigRepository

class AppConfigRepositoryImpl : AppConfigRepository {

    override suspend fun isAppInMaintenance(): Boolean {
        return false
    }
}
