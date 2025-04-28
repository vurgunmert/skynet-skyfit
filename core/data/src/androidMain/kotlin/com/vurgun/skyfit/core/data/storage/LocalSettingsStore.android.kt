package com.vurgun.skyfit.core.data.storage

import android.content.Context

actual fun provideLocalSettings(context: Any?): LocalSettingsStore {
    require(context is Context) { "Expected Context for Android" }
    return LocalSettingsImpl(context)
}