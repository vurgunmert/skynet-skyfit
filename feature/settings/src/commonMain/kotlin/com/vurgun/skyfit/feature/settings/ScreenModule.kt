package com.vurgun.skyfit.feature.settings

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen
import com.vurgun.skyfit.feature.settings.facility.FacilitySettingsMainScreen
import com.vurgun.skyfit.feature.settings.trainer.TrainerSettingsMainScreen
import com.vurgun.skyfit.feature.settings.user.UserSettingsMainScreen
import kotlinx.serialization.Serializable

val settingsScreenModule = screenModule {
    register<SharedScreen.Settings> { SettingsHostScreen() }
    register<SettingsHomeEntryPoint.Facility> { FacilitySettingsMainScreen() }
    register<SettingsHomeEntryPoint.Trainer> { TrainerSettingsMainScreen() }
    register<SettingsHomeEntryPoint.User> { UserSettingsMainScreen() }
    register<SettingsHomeEntryPoint.Unauthorized> { UnauthorizedAccessScreen() }
}

@Serializable
internal sealed class SettingsHomeEntryPoint : ScreenProvider {
    @Serializable
    data object Facility : SettingsHomeEntryPoint()

    @Serializable
    data object Trainer : SettingsHomeEntryPoint()

    @Serializable
    data object User : SettingsHomeEntryPoint()

    @Serializable
    data object Unauthorized : SettingsHomeEntryPoint()
}
