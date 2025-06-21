package com.vurgun.skyfit.settings

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen

val screenSettingsModule = screenModule {
    register<SharedScreen.Settings> { SettingsScreen() }
}