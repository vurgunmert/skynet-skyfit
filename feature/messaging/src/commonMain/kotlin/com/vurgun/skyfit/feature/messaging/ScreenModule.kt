package com.vurgun.skyfit.feature.messaging

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.messaging.screen.ConversationsScreen

val messagingScreenModule = screenModule {
    register<SharedScreen.Conversations> { ConversationsScreen() }
}