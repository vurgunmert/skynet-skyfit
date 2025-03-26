package com.vurgun.skyfit.core.storage

import kotlinx.browser.localStorage

actual fun provideLocalSettings(context: Any?): LocalSettingsStore {
    return WebLocalSettingsStore()
}

class WebLocalSettingsStore : LocalSettingsStore {

    override fun savePhoneNumber(value: String) {
        localStorage.setItem("phone_number", value)
    }

    override fun getPhoneNumber(): String? {
        return localStorage.getItem("phone_number")
    }

    override fun saveToken(value: String) {
        localStorage.setItem("token", value)
    }

    override fun getToken(): String? {
        return localStorage.getItem("token")
    }

    override fun clearToken() {
        localStorage.removeItem("token")
    }

    override fun clearPhoneNumber() {
        localStorage.removeItem("phone_number")
    }

    override fun clearAll() {
        localStorage.clear()
    }
}
