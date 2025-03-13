package com.vurgun.skyfit.core.storage

interface LocalSettingsStore {
    fun savePhoneNumber(value: String)
    fun getPhoneNumber(): String?
    fun saveToken(value: String)
    fun getToken(): String?
    fun clearToken()
    fun clearPhoneNumber()
    fun clearAll()
}

expect fun provideLocalSettings(context: Any?): LocalSettingsStore