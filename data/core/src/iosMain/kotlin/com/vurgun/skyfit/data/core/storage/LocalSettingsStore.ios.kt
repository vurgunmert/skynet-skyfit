package com.vurgun.skyfit.data.core.storage

actual fun provideLocalSettings(context: Any?): LocalSettingsStore {
    return LocalSettingsImpl()
}
