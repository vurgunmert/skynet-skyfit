package com.vurgun.skyfit.data.auth

import com.vurgun.skyfit.data.auth.repository.AuthRepositoryImpl
import com.vurgun.skyfit.data.auth.repository.config.AppConfigRepositoryImpl
import com.vurgun.skyfit.data.auth.service.AuthApiService
import com.vurgun.skyfit.data.auth.domain.usecase.SplashUseCase
import com.vurgun.skyfit.data.auth.domain.repository.AppConfigRepository
import com.vurgun.skyfit.data.auth.domain.repository.AuthRepository
import com.vurgun.skyfit.data.core.dataCoreModule
import org.koin.dsl.module

val dataAuthModule = module {
    includes(dataCoreModule)

    single { AuthApiService(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get(), get(), get()) }

    single<AppConfigRepository> { AppConfigRepositoryImpl() }
    factory { SplashUseCase(get(), get()) }
}