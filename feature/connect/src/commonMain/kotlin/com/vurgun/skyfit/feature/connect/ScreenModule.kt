package com.vurgun.skyfit.feature.connect

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.connect.chatbot.ChatBotScreen
import com.vurgun.skyfit.feature.connect.communication.ConversationsScreen
import com.vurgun.skyfit.feature.connect.communication.ExpandedConversationsScreen
import com.vurgun.skyfit.feature.connect.communication.UserToUserChatScreen
import com.vurgun.skyfit.feature.connect.notification.CompactNotificationsScreen
import com.vurgun.skyfit.feature.connect.notification.ExpandedNotificationsScreen

val connectScreenModule = screenModule {
    register<SharedScreen.NotificationsCompact> { CompactNotificationsScreen() }
    register<SharedScreen.NotificationsExpanded> { ExpandedNotificationsScreen(it.onDismiss) }
    register<SharedScreen.Conversations> { ConversationsScreen() }
    register<SharedScreen.ConversationsExpanded> { ExpandedConversationsScreen(it.onDismiss) }
    register<SharedScreen.UserChat> { UserToUserChatScreen() }
    register<SharedScreen.ChatBot> { ChatBotScreen() }
}