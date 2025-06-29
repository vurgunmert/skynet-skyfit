package com.vurgun.skyfit

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object AndroidNotifier {

    fun initialize(context: android.content.Context) {

        val resId = context.resources.getIdentifier("ic_fiwe_logo", "drawable", context.packageName)

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = resId,
                showPushNotification = true,
            )
        )
    }
}