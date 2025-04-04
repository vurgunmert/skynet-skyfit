package com.vurgun.skyfit.core.storage

import android.content.Context
import android.content.SharedPreferences

class LocalSettingsImpl(context: Context) : LocalSettingsStore {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("skyfit_prefs", Context.MODE_PRIVATE)

    override fun savePhoneNumber(value: String) {
        sharedPreferences.edit().putString("phone_number", value).apply()
    }

    override fun getPhoneNumber(): String? {
        return sharedPreferences.getString("phone_number", null)
    }

    override fun saveToken(value: String) {
        sharedPreferences.edit().putString("token", value).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    override fun clearToken() {
        sharedPreferences.edit().remove("token").apply()
    }

    override fun clearPhoneNumber() {
        sharedPreferences.edit().remove("phone_number").apply()
    }

    override fun clearAll() {
        clearPhoneNumber()
        clearToken()
    }

    override fun isChatbotOnboardingCompleted(): Boolean {
        return sharedPreferences.getBoolean("chatbot_onboarding_completed", false)
    }

    override fun setChatbotOnboardingCompleted(value: Boolean) {
        sharedPreferences.edit().putBoolean("chatbot_onboarding_completed", value).apply()
    }
}
