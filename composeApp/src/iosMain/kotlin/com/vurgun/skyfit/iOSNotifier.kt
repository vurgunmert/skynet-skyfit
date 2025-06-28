package com.vurgun.skyfit

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration

fun initializeNotifier() {
    NotifierManager.initialize(NotificationPlatformConfiguration.Ios(
        showPushNotification = true,
        askNotificationPermissionOnStart = true,
        notificationSoundName = null,
    ))
}