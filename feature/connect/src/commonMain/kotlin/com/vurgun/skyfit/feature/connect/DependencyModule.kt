package com.vurgun.skyfit.feature.connect

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.connect.chatbot.ChatbotViewModel
import com.vurgun.skyfit.feature.connect.communication.ChatViewModel
import com.vurgun.skyfit.feature.connect.notification.UserNotificationsViewModel
import org.koin.dsl.module

val featureConnectModule = module {
    includes(dataCoreModule)

    factory { ChatViewModel() }
    factory { ChatbotViewModel(get(), get()) }

    factory { UserNotificationsViewModel() }
}