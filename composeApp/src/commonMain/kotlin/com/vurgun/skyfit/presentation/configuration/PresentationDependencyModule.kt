package com.vurgun.skyfit.presentation.configuration

import com.vurgun.skyfit.presentation.shared.features.auth.SplashViewModel
import com.vurgun.skyfit.presentation.shared.viewmodel.ChatbotViewModel
import com.vurgun.skyfit.presentation.shared.viewmodel.SkyFitConversationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationDependencyModule = module {

    //ViewModel
//    viewModel { SplashViewModel(get()) }
    viewModelOf(::SplashViewModel)
    viewModel { ChatbotViewModel(get()) }
    viewModel { SkyFitConversationViewModel() }
}