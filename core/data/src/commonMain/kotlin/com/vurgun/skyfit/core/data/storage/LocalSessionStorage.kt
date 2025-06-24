package com.vurgun.skyfit.core.data.storage

interface LocalSessionStorage {
    fun savePhoneNumber(value: String)
    fun getPhoneNumber(): String?
    fun saveToken(value: String)
    fun getToken(): String?
    fun clearToken()
    fun clearPhoneNumber()
    fun clearAll()
    fun isChatbotOnboardingCompleted(): Boolean
    fun setChatbotOnboardingCompleted(value: Boolean)
}

expect fun provideLocalSettings(context: Any?): LocalSessionStorage