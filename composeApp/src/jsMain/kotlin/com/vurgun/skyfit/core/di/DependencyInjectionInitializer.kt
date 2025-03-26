package com.vurgun.skyfit.core.di

import com.vurgun.skyfit.core.storage.provideLocalSettings
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val localStorageModule = module { single { provideLocalSettings(null) } }

fun initKoin() {
    startKoin {
        modules(localStorageModule + sharedModules)
    }
}

