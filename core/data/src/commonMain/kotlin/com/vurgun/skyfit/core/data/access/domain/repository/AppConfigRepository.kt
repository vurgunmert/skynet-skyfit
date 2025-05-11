package com.vurgun.skyfit.core.data.access.domain.repository

interface AppConfigRepository {
    suspend fun isAppInMaintenance(): Boolean
}