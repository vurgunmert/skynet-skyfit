package com.vurgun.skyfit.feature.connect

import com.vurgun.skyfit.core.data.dataCoreModule
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatWithBotViewModel
import com.vurgun.skyfit.feature.connect.chatbot.model.ChatbotViewModel
import com.vurgun.skyfit.feature.connect.conversation.ChatViewModel
import com.vurgun.skyfit.feature.connect.conversation.ConversationsViewModel
import com.vurgun.skyfit.feature.connect.notification.NotificationsViewModel
import com.vurgun.skyfit.feature.connect.social.UserSocialMediaFeedViewModel
import org.koin.dsl.module

val dataConnectModule = module {
    includes(dataCoreModule)

    factory { ChatViewModel() }
    factory { ChatbotViewModel(get()) }
    factory { ChatWithBotViewModel(get()) }

    factory { NotificationsViewModel() }
    factory { ConversationsViewModel() }

    factory { UserSocialMediaFeedViewModel(get(), get()) }
}