package com.vurgun.skyfit.feature.onboarding

import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.onboarding.screen.OnboardingScreen

val onboardingScreenModule = screenModule {
    register<SharedScreen.Onboarding> { OnboardingScreen() }
}