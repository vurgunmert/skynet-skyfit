package com.vurgun.skyfit.core.storage

import platform.Foundation.NSUserDefaults

class LocalSettingsImpl : LocalSettingsStore {

    private val userDefaults = NSUserDefaults.standardUserDefaults

    override fun saveToken(token: String) {
        userDefaults.setObject(token, forKey = "token")
    }

    override fun getToken(): String? {
        return userDefaults.stringForKey("token")
    }

    override fun clearToken() {
        userDefaults.removeObjectForKey("token")
    }
}