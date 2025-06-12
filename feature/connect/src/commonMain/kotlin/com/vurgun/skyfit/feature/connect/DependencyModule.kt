package com.vurgun.skyfit.feature.connect

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatWithBotViewModel
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatbotViewModel
import com.vurgun.skyfit.feature.connect.conversation.ChatViewModel
import com.vurgun.skyfit.feature.connect.notification.UserNotificationsViewModel
import org.koin.dsl.module

val featureConnectModule = module {
    includes(dataCoreModule)

    factory { ChatViewModel() }
    factory { ChatbotViewModel(get()) }
    factory { ChatWithBotViewModel(get()) }

    factory { UserNotificationsViewModel() }
}