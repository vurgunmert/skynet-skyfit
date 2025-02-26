package com.vurgun.skyfit.core.storage

interface LocalSettingsStore {
    fun saveToken(token: String)
    fun getToken(): String?
    fun clearToken()
}

expect fun provideLocalSettings(context: Any?): LocalSettingsStore