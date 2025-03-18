package com.vurgun.skyfit.core.di

import android.app.Application
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import com.vurgun.skyfit.core.storage.provideLocalSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

object DependencyInjectionInitializer {

    fun initKoin(application: Application) {
        startKoin {
            androidContext(application)
            modules(localStorageModule + sharedModules)
        }
    }

    private val localStorageModule = module {
        single<LocalSettingsStore> { provideLocalSettings(androidContext()) }
    }
}
