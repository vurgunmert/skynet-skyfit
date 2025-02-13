package com.vurgun.skyfit.presentation.mobile.features.onboarding

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
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
        initialRoute = SkyFitNavigationRoute.OnboardingCharacterSelection.route
    ) {
        scene(SkyFitNavigationRoute.OnboardingUserTypeSelection.route) {
            MobileOnboardingUserTypeSelectionScreen(
                onClickUser = {
                    onboardingNavigator.jumpAndStay(SkyFitNavigationRoute.OnboardingCharacterSelection)
                },
                onClickTrainer = {
                    rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
                },
                onClickFacility = {
                    rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
                }
            )
        }
        scene(SkyFitNavigationRoute.OnboardingCharacterSelection.route) {
            MobileOnboardingCharacterSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(SkyFitNavigationRoute.OnboardingBirthYearSelection)
                }
            )
        }
        scene(SkyFitNavigationRoute.OnboardingBirthYearSelection.route) {
            MobileOnboardingBirthYearSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(SkyFitNavigationRoute.OnboardingGenderSelection)
                }
            )
        }
        scene(SkyFitNavigationRoute.OnboardingGenderSelection.route) {
            MobileOnboardingGenderSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(SkyFitNavigationRoute.OnboardingWeightSelection)
                }
            )
        }
        scene(SkyFitNavigationRoute.OnboardingWeightSelection.route) {
            MobileOnboardingWeightSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(SkyFitNavigationRoute.OnboardingHeightSelection)
                }
            )
        }
        scene(SkyFitNavigationRoute.OnboardingHeightSelection.route) {
            MobileOnboardingHeightSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(SkyFitNavigationRoute.OnboardingGoalSelection)
                }
            )
        }
        scene(SkyFitNavigationRoute.OnboardingGoalSelection.route) {
            MobileOnboardingGoalSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(SkyFitNavigationRoute.OnboardingCompleted)
                }
            )
        }
        scene(SkyFitNavigationRoute.OnboardingCompleted.route) {
            MobileOnboardingCompletedScreen(
                onClickContinue = {
                    rootNavigator.jumpAndTakeover(SkyFitNavigationRoute.Onboarding, SkyFitNavigationRoute.Dashboard)
                }
            )
        }
    }
}
