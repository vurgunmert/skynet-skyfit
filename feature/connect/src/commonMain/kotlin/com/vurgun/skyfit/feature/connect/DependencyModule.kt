package com.vurgun.skyfit.feature.connect

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.connect.chatbot.ChatbotViewModel
import com.vurgun.skyfit.feature.messaging.communication.ChatViewModel
import com.vurgun.skyfit.feature.notification.notification.UserNotificationsViewModel
import org.koin.dsl.module

val featureConnectModule = module {
    includes(dataCoreModule)

    factory { ChatViewModel() }
    factory { ChatbotViewModel(get()) }

    factory { UserNotificationsViewModel() }
}