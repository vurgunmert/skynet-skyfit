package com.vurgun.skyfit

import com.mmk.kmpnotifier.notification.NotificationImage
import com.mmk.kmpnotifier.notification.Notifier
import com.mmk.kmpnotifier.notification.NotifierManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

object CoreNotifier {
    val permissionUtil by lazy { NotifierManager.getPermissionUtil() }
    val localNotifier by lazy { NotifierManager.getLocalNotifier() }
    val pushNotifier by lazy { NotifierManager.getPushNotifier() }

    fun subscribeTopic() {
        GlobalScope.launch {
            val pushToken = pushNotifier.getToken()
            println("[CoreNotifier] push token ->>${pushToken}")

            pushNotifier.subscribeToTopic("all_users")
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun sendGreetingsNotification() {
        localNotifier.notify {
            id = Random.nextInt(0, Int.MAX_VALUE)
            title = "D: Güzel Bir Gün Başlasın!"
            body = "FIWE ile bugünün de dolu dolu geçsin. Hazırsan başlayalım! 🚀"
            payloadData = mapOf(
                Notifier.KEY_URL to "https://fiwe.io/welcome",
                "deeplink" to "fiwe::profile",
                "type" to "greeting"
            )
            image = NotificationImage.Url("https://ik.imagekit.io/skynet2skyfit/Logo/AppIcon~ios-marketing.png?updatedAt=1751197502196")
        }

        GlobalScope.launch {
            val pushToken = pushNotifier.getToken()
            println("[CoreNotifier] push token ->>${pushToken}")

            pushNotifier.subscribeToTopic("all_users")
        }
    }
}