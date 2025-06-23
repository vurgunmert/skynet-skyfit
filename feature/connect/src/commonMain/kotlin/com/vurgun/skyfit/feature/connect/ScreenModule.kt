package com.vurgun.skyfit.feature.connect

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.connect.chatbot.screen.ChatBotScreen
import com.vurgun.skyfit.feature.connect.conversation.ConversationsScreen
import com.vurgun.skyfit.feature.connect.conversation.ChatScreen
import com.vurgun.skyfit.feature.connect.notification.CompactNotificationsScreen
import com.vurgun.skyfit.feature.connect.notification.ExpandedNotificationsScreen
import com.vurgun.skyfit.feature.connect.social.NewPostScreen
import com.vurgun.skyfit.feature.connect.social.SocialMediaScreen

val screenConnectModule = screenModule {
    register<SharedScreen.Notifications> { CompactNotificationsScreen() }
    register<SharedScreen.NotificationsExpanded> { ExpandedNotificationsScreen(it.onDismiss) }
    register<SharedScreen.Conversations> { ConversationsScreen() }
    register<SharedScreen.UserChat> { ChatScreen() }
    register<SharedScreen.ChatBot> { ChatBotScreen() }

    register<SharedScreen.Social> { SocialMediaScreen() }
    register<SharedScreen.NewPost> { NewPostScreen() }
}