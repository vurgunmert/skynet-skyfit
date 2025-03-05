package com.vurgun.skyfit.core.di

import com.vurgun.skyfit.feature_appointments.di.appointmentModule
import com.vurgun.skyfit.feature_auth.di.authModule
import com.vurgun.skyfit.feature_chatbot.di.chatBotModule
import com.vurgun.skyfit.feature_messaging.di.messagingModule
import com.vurgun.skyfit.feature_notifications.di.notificationModule
import com.vurgun.skyfit.feature_profile.di.profileModule
import com.vurgun.skyfit.feature_settings.di.settingsModule
import org.koin.core.module.Module

val sharedModules: List<Module> = listOf(
    appModule,
    authModule,
    settingsModule,
    messagingModule,
    notificationModule,
    appointmentModule,
    chatBotModule,
    profileModule
)