package com.vurgun.skyfit

import com.mmk.kmpnotifier.extensions.composeDesktopResourcesPath
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import java.io.File

object DesktopNotifier {

    fun initialize() {

        NotifierManager.initialize(
            NotificationPlatformConfiguration.Desktop(
                showPushNotification = true,
                notificationIconPath = composeDesktopResourcesPath() + File.separator + "icons/fiwe.png"
            )
        )
    }
}