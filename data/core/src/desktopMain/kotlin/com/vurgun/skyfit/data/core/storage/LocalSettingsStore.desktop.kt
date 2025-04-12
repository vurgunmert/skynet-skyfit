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
        return "eyJpdiI6IjVLOHNQemppZi9GcjdjTmYxdnB2d0E9PSIsImRhdGEiOiIyL24rRVBsZHlCVXNvRkh4R2c4OVZNOGtKaHdRazRVREdKUncxMVhrTDRxbzdxTjBMRXpEdEEvME13VitNaVpkV0JRYURRbmFJQmc4VU5JVU8yYnRFaW1sYmJ1aGpjUXo5ckFDVmR6NVg0eVBPellsSjN5QUkzMmtPdnR2MTJPcWU2dHF5SXlHS3I3TjV5d3FPMExOWnN5OFlnb0FSeWV1TzZQdzhlUnVoN1FNMzIzZkJQYjkxK3F6dTlISDR2d01KSHo3RVQwM0E4cTJSbG9xeWM2UjdBaGdSNlMwdXhVTFVXRGpXVGpZR0ZVb1BsWlIzV3lGa1FIayttdUZoYXRUYmhsSEgxNnBlUnFYZzlGWkJIQjBhY01MOEdaVGFEdWZxSFdscExVdE9qUDh6SmdMQlNMUWFrTW5PWGdnditUVm94djlZZEtTUjUyL01XUEJMbjg4UW94T1c5STJUWnFiVi93M2trYkxtc009In0="
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
