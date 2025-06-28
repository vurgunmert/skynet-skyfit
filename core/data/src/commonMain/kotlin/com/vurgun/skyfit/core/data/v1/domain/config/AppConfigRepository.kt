package com.vurgun.skyfit.core.data.v1.domain.config

@Deprecated("RemoteCOnfig")
interface AppConfigRepository {
    suspend fun isAppInMaintenance(): Boolean
}