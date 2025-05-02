package com.vurgun.skyfit.feature.notification

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.notification.screen.UserNotificationsScreen

val notificationsScreenModule = screenModule {
    register<SharedScreen.Notifications> { UserNotificationsScreen() }
}