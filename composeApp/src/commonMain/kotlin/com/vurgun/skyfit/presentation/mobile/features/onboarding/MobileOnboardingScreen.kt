package com.vurgun.skyfit.presentation.mobile.features.onboarding

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun MobileOnboardingScreen(rootNavigator: Navigator) {
    val onboardingNavigator = rememberNavigator()

    val completeOnboarding: () -> Unit = {
        rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
    }

    NavHost(
        navigator = onboardingNavigator,
        initialRoute = SkyFitNavigationRoute.OnboardingUserTypeSelection.route
    ) {
        scene(SkyFitNavigationRoute.OnboardingUserTypeSelection.route) { MobileOnboardingUserTypeSelectionScreen(onboardingNavigator) }
        scene(SkyFitNavigationRoute.OnboardingCharacterSelection.route) { MobileOnboardingCharacterSelectionScreen(onboardingNavigator) }
        scene(SkyFitNavigationRoute.OnboardingBirthYearSelection.route) { MobileOnboardingBirthYearSelectionScreen(onboardingNavigator) }
        scene(SkyFitNavigationRoute.OnboardingGenderSelection.route) { MobileOnboardingGenderSelectionScreen(onboardingNavigator) }
        scene(SkyFitNavigationRoute.OnboardingWeightSelection.route) { MobileOnboardingWeightSelectionScreen(onboardingNavigator) }
        scene(SkyFitNavigationRoute.OnboardingHeightSelection.route) { MobileOnboardingHeightSelectionScreen(onboardingNavigator) }
        scene(SkyFitNavigationRoute.OnboardingGoalSelection.route) { MobileOnboardingGoalSelectionScreen(onboardingNavigator) }
        scene(SkyFitNavigationRoute.OnboardingCompleted.route) { MobileOnboardingCompletedScreen(onboardingNavigator) }
    }
}
