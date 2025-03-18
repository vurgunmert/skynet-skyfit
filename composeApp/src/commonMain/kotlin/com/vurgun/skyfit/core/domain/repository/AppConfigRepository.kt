package com.vurgun.skyfit.core.domain.repository

interface AppConfigRepository {
    suspend fun isAppInMaintenance(): Boolean
}