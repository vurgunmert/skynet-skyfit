package com.vurgun.skyfit

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData

fun setLogger() {
    NotifierManager.setLogger { message ->
        // Log the message
        println(message)
    }
}

fun addListener() {
    NotifierManager.addListener(object : NotifierManager.Listener {
        override fun onNotificationClicked(data: PayloadData) {
            super.onNotificationClicked(data)
            println("Notification clicked, Notification payloadData: $data")
        }
    })
}