package com.vurgun.skyfit

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration

object AndroidNotifier {

    fun initialize() {
        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = 1,
                showPushNotification = true,
            )
        )
    }
}