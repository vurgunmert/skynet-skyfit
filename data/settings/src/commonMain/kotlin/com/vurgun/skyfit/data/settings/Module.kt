package com.vurgun.skyfit.data.settings

import com.vurgun.skyfit.data.settings.repository.SettingsApiService
import com.vurgun.skyfit.data.settings.repository.SettingsRepository
import com.vurgun.skyfit.data.settings.repository.SettingsRepositoryImpl
import org.koin.dsl.module

val dataSettingsModule = module {

    single { SettingsApiService(get()) }
    single<SettingsRepository> { SettingsRepositoryImpl(get(), get(), get()) }
}