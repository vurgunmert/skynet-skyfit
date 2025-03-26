package com.vurgun.skyfit.core.di

import com.vurgun.skyfit.feature_appointments.di.appointmentModule
import com.vurgun.skyfit.feature_auth.di.authModule
import com.vurgun.skyfit.feature_chatbot.di.chatBotModule
import com.vurgun.skyfit.feature_dashboard.di.dashboardModule
import com.vurgun.skyfit.feature_messaging.di.messagingModule
import com.vurgun.skyfit.feature_notifications.di.notificationModule
import com.vurgun.skyfit.feature_onboarding.di.onboardingModule
import com.vurgun.skyfit.feature_profile.di.profileModule
import com.vurgun.skyfit.feature_settings.di.settingsModule
import com.vurgun.skyfit.feature_splash.di.splashModule
import org.koin.core.module.Module

val sharedModules: List<Module> = listOf(
    networkModule,
    configModule,
    userModule,
    dashboardModule,
    splashModule,
    authModule,
    onboardingModule,
    settingsModule,
    messagingModule,
    notificationModule,
    appointmentModule,
    chatBotModule,
    profileModule
)