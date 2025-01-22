package com.vurgun.skyfit.presentation.configuration

import com.vurgun.skyfit.presentation.shared.features.auth.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationDependencyModule = module {

    //ViewModel
//    viewModel { SplashViewModel(get()) }
    viewModelOf(::SplashViewModel)
}