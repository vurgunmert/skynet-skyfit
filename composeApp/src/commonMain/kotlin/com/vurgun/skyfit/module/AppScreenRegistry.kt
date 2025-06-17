package com.vurgun.skyfit.module

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.ScreenRegistry
import com.vurgun.explore.screenExploreModule
import com.vurgun.main.screenMainModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.screen.UnderDevelopmentScreen
import com.vurgun.skyfit.feature.access.screenAuthModule
import com.vurgun.skyfit.feature.connect.screenConnectModule
import com.vurgun.skyfit.feature.home.screenHomeModule
import com.vurgun.skyfit.settings.screenSettingsModule
import com.vurgun.skyfit.feature.schedule.screenScheduleModule
import com.vurgun.skyfit.feature.wellbeign.screenWellbeingModule
import com.vurgun.skyfit.onboarding.screenOnboardingModule
import com.vurgun.skyfit.profile.screenProfileModule

@Composable
internal fun AppScreenRegistry() {
    ScreenRegistry {
        register<SharedScreen.UnderDevelopment> { UnderDevelopmentScreen() }
        screenAuthModule()
        screenOnboardingModule()
        screenMainModule()
        screenHomeModule()
        screenProfileModule()
        screenExploreModule()
        screenSettingsModule()
        screenScheduleModule()
        screenWellbeingModule()
        screenConnectModule()
    }
}