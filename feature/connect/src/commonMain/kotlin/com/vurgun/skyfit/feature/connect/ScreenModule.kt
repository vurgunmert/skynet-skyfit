package com.vurgun.skyfit.feature.connect

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.messaging.communication.ConversationsScreen
import com.vurgun.skyfit.feature.notification.notification.UserNotificationsScreen

val connectScreenModule = screenModule {
    register<SharedScreen.Notifications> { UserNotificationsScreen() }
    register<SharedScreen.Conversations> { ConversationsScreen() }
}