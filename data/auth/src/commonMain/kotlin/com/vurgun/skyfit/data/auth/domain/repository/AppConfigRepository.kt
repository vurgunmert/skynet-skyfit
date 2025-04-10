package com.vurgun.skyfit.data.auth.domain.repository

interface AppConfigRepository {
    suspend fun isAppInMaintenance(): Boolean
}