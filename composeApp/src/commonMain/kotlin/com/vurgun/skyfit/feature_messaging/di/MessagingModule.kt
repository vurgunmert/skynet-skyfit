package com.vurgun.skyfit.feature_messaging.di

import com.vurgun.skyfit.feature_messaging.ui.SkyFitConversationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val messagingModule = module {
    viewModel { SkyFitConversationViewModel() }
}