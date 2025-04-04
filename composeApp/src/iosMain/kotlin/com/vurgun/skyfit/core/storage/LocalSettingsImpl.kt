package com.vurgun.skyfit.core.storage

import platform.Foundation.NSUserDefaults

class LocalSettingsImpl : LocalSettingsStore {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun savePhoneNumber(value: String) {
        userDefaults.setObject(value, forKey = "phone_number")
    }

    override fun getPhoneNumber(): String? {
        return userDefaults.stringForKey("phone_number")
    }

    override fun saveToken(value: String) {
        userDefaults.setObject(value, forKey = "token")
    }

    override fun getToken(): String? {
        return userDefaults.stringForKey("token")
    }

    override fun clearToken() {
        userDefaults.removeObjectForKey("token")
    }

    override fun clearPhoneNumber() {
        userDefaults.removeObjectForKey("phone_number")
    }

    override fun clearAll() {
        clearPhoneNumber()
        clearToken()
    }

    override fun isChatbotOnboardingCompleted(): Boolean {
        return userDefaults.boolForKey("chatbot_onboarding_completed")
    }

    override fun setChatbotOnboardingCompleted(value: Boolean) {
        userDefaults.setObject(value, forKey = "chatbot_onboarding_completed")
    }
}