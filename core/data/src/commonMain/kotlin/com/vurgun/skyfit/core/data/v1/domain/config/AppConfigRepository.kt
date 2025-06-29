package com.vurgun.skyfit.core.data.v1.domain.config

@Deprecated("RemoteConfig")
interface AppConfigRepository {
    suspend fun isAppInMaintenance(): Boolean
}