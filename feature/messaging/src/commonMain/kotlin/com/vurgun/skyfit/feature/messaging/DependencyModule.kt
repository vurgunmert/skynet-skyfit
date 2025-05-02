package com.vurgun.skyfit.feature.messaging

import com.vurgun.skyfit.data.messaging.dataMessagingModule
import com.vurgun.skyfit.feature.messaging.chatbot.ChatbotViewModel
import com.vurgun.skyfit.feature.messaging.screen.ChatViewModel
import org.koin.dsl.module

val featureMessagingModule = module {
    includes(dataMessagingModule)

    factory { ChatViewModel() }
    factory { ChatbotViewModel(get()) }
}