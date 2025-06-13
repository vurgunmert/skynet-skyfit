package com.vurgun.skyfit.feature.persona.settings.model

sealed interface SettingsNavigationRoute {
    data object Account: SettingsNavigationRoute
    data object PaymentHistory: SettingsNavigationRoute
    data object Notifications: SettingsNavigationRoute
    data object Support: SettingsNavigationRoute
    data object Packages: SettingsNavigationRoute
    data object Members: SettingsNavigationRoute
    data object Trainers: SettingsNavigationRoute
    data object Branches: SettingsNavigationRoute
}