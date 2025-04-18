package com.vurgun.skyfit.data.settings

import com.vurgun.skyfit.data.settings.repository.SettingsApiService
import com.vurgun.skyfit.data.settings.domain.repository.MemberRepository
import com.vurgun.skyfit.data.settings.domain.repository.TrainerRepository
import com.vurgun.skyfit.data.settings.repository.SettingsRepositoryImpl
import org.koin.dsl.module

val dataSettingsModule = module {

    single { SettingsApiService(get()) }
    single { SettingsRepositoryImpl(get(), get(), get()) }

    single<MemberRepository> { get<SettingsRepositoryImpl>() }
    single<TrainerRepository> { get<SettingsRepositoryImpl>() }

}