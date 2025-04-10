package com.vurgun.skyfit.feature.notification

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featureNotificationModule = module {
    viewModel { UserNotificationsViewModel() }
}