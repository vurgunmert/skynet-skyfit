package com.vurgun.skyfit.feature.messaging

import com.vurgun.skyfit.data.messaging.dataMessagingModule
import com.vurgun.skyfit.feature.messaging.chatbot.ChatbotViewModel
import com.vurgun.skyfit.feature.messaging.screen.SkyFitConversationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureMessagingModule = module {
    includes(dataMessagingModule)

    viewModel { SkyFitConversationViewModel() }
    viewModel { ChatbotViewModel(get()) }
}