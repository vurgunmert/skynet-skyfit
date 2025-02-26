package com.vurgun.skyfit.core.storage

import android.content.Context
import android.content.SharedPreferences

class LocalSettingsImpl(context: Context) : LocalSettingsStore {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("skyfit_prefs", Context.MODE_PRIVATE)

    override fun saveToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString("token", null)
    }

    override fun clearToken() {
        sharedPreferences.edit().remove("token").apply()
    }
}
