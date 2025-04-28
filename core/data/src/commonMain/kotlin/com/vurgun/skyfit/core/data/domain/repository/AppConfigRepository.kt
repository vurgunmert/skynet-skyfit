package com.vurgun.skyfit.core.data.domain.repository

interface AppConfigRepository {
    suspend fun isAppInMaintenance(): Boolean
}