package com.vurgun.skyfit.data.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.vurgun.skyfit.data.core.storage.createDataStore
import org.koin.dsl.module

internal actual val platformModule = module {
    single<DataStore<Preferences>> { createDataStore() }
}