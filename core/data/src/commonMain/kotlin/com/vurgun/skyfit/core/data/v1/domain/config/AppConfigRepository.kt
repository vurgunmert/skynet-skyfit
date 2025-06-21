package com.vurgun.skyfit.core.data.v1.domain.config

interface AppConfigRepository {
    suspend fun isAppInMaintenance(): Boolean
}