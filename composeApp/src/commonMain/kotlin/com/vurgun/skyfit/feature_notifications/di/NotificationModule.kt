package com.vurgun.skyfit.feature_notifications.di

import com.vurgun.skyfit.feature_notifications.ui.UserNotificationsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val notificationModule = module {
    viewModel { UserNotificationsViewModel() }
}