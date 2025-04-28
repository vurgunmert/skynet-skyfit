package com.vurgun.skyfit.feature.splash

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.splash.domain.SplashUseCase
import com.vurgun.skyfit.feature.splash.screenmodel.SplashViewModel
import org.koin.dsl.module

val featureSplashModule = module {
    includes(dataCoreModule)

    factory { SplashUseCase(get(), get()) }
    factory { SplashViewModel(get()) }
}