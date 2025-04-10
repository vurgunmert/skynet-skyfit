package com.vurgun.skyfit.data.core.storage

import java.util.prefs.Preferences

actual fun provideLocalSettings(context: Any?): LocalSettingsStore {
    return DesktopLocalSettingsStore()
}

class DesktopLocalSettingsStore : LocalSettingsStore {
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

    override fun getToken(): String? { //TODO: REMOVE DEBUG
        return "eyJpdiI6IjBaSmpMWDB3Z0RIN2dJM09IbGoyNnc9PSIsImRhdGEiOiJnSHlVN3crVjhRNlV6UThmODROWjhXbWFkS1J5RmFCdFBEOFJIeHpUOUlSeXExZ2JPN1B3K21SM0I0QW9FOUdqSUNza1AyNmhqZi9LWUtaajJkT0xvQWwvMUdkSHYzSHlaTk1PZ2w5LzA5WEs5K24za3BXZEFpQTM4cnh1RGVYdVg2eEFObUZacGl5cGNMdWp4ZW4yVUl6U2l1cFczTlVCOXpDT29vbWNWa0JHcTJzMFcyWG95eFBrdnZVRC8xNCtIQ3AwOWVidGJrOGtnUHVZNFZaaTVtbloyNzZuYjZoUTFWbFF1MGVvRkpQM3FiSGlsZk5jWCtGNHo1blpSRjlpZzNmQ1hOMXBlbXU4RUlLWHRvcUxaVG9hZSttRGsrVzZ2SkNmTEpadFBHUT0ifQ=="
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
