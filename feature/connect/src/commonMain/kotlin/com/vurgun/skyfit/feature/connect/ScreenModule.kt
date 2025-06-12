package com.vurgun.skyfit.feature.connect

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.connect.chatbot.screen.ChatBotScreen
import com.vurgun.skyfit.feature.connect.conversation.ConversationsScreen
import com.vurgun.skyfit.feature.connect.conversation.UserToUserChatScreen
import com.vurgun.skyfit.feature.connect.notification.CompactNotificationsScreen
import com.vurgun.skyfit.feature.connect.notification.ExpandedNotificationsScreen

val connectScreenModule = screenModule {
    register<SharedScreen.Notifications> { CompactNotificationsScreen() }
    register<SharedScreen.NotificationsExpanded> { ExpandedNotificationsScreen(it.onDismiss) }
    register<SharedScreen.Conversations> { ConversationsScreen() }
    register<SharedScreen.UserChat> { UserToUserChatScreen() }
    register<SharedScreen.ChatBot> { ChatBotScreen() }
}