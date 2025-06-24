package com.vurgun.skyfit.core.data.storage

import android.content.Context

actual fun provideLocalSettings(context: Any?): LocalSessionStorage {
    require(context is Context) { "Expected Context for Android" }
    return LocalSettingsImpl(context)
}