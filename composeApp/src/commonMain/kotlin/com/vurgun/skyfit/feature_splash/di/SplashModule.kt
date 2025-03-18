package com.vurgun.skyfit.feature_splash.di

import com.vurgun.skyfit.feature_splash.domain.usecase.SplashUseCase
import com.vurgun.skyfit.feature_splash.presentation.viewmodel.SplashViewModel
import org.koin.dsl.module

val splashModule = module {
    factory { SplashUseCase(get(), get()) }
    factory { SplashViewModel(get()) }
}