package com.vurgun.skyfit.core.storage

actual fun provideLocalSettings(context: Any?): LocalSettingsStore {
    return LocalSettingsImpl()
}
