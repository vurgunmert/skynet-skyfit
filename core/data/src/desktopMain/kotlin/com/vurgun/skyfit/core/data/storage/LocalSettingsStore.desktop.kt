package com.vurgun.skyfit.core.data.storage

import java.util.prefs.Preferences

actual fun provideLocalSettings(context: Any?): LocalSessionStorage {
    return DesktopLocalSessionStorage()
}

class DesktopLocalSessionStorage : LocalSessionStorage {
    private val prefs: Preferences = Preferences.userRoot().node(this::class.java.name)

    override fun savePhoneNumber(value: String) {
        prefs.put("phone_number", value)
    }

    override fun getPhoneNumber(): String? {
        return prefs.get("phone_number", null)
    }

    override fun saveToken(value: String) {
        prefs.put("token", value)
    }

    override fun getToken(): String? {
        return prefs.get("token", null)
    }

    override fun clearToken() {
        prefs.remove("token")
    }

    override fun clearPhoneNumber() {
        prefs.remove("phone_number")
    }

    override fun clearAll() {
        prefs.clear()
    }

    override fun isChatbotOnboardingCompleted(): Boolean {
        return prefs.getBoolean("chatbot_onboarding_completed", false)
    }

    override fun setChatbotOnboardingCompleted(value: Boolean) {
        prefs.putBoolean("chatbot_onboarding_completed", value)
    }
}
