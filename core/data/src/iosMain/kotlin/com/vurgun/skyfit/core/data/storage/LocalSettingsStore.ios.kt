package com.vurgun.skyfit.core.data.storage

actual fun provideLocalSettings(context: Any?): LocalSessionStorage {
    return LocalSettingsImpl()
}
