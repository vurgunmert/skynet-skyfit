package com.vurgun.skyfit.settings

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.settings.facility.trainer.FacilityTrainerSettingsScreen

val screenSettingsModule = screenModule {
    register<SharedScreen.Settings> { SettingsScreen() }
    register<SharedScreen.FacilityTrainerSettings> { FacilityTrainerSettingsScreen() }
}