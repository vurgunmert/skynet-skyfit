package com.vurgun.skyfit

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("InitializeNotifier")
fun initializeNotifier() {
    NotifierManager.initialize(NotificationPlatformConfiguration.Ios(
        showPushNotification = true,
        askNotificationPermissionOnStart = true,
        notificationSoundName = null,
    ))
}