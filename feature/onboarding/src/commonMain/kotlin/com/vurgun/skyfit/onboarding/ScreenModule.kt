package com.vurgun.skyfit.onboarding

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.onboarding.screen.OnboardingScreen

val screenOnboardingModule = screenModule {
    register<SharedScreen.Onboarding> { OnboardingScreen() }
}