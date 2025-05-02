package com.vurgun.skyfit.feature.notification

import com.vurgun.skyfit.feature.notification.screen.UserNotificationsViewModel
import org.koin.dsl.module

val featureNotificationModule = module {
    factory { UserNotificationsViewModel() }
}