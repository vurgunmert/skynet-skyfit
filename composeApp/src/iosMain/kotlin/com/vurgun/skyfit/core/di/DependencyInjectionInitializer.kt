package com.vurgun.skyfit.core.di

import com.vurgun.skyfit.core.storage.provideLocalSettings
import org.koin.core.context.startKoin
import org.koin.dsl.module

private val storageModule = module { single { provideLocalSettings(get()) } }

fun initKoin() {
    startKoin {
        modules(storageModule + sharedModules)
    }
}

