package com.vurgun.skyfit.core.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.vurgun.skyfit.core.data.storage.createDataStore
import org.koin.dsl.module

internal actual val platformModule = module {
    single<DataStore<Preferences>> { createDataStore() }
}