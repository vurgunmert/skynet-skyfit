package com.vurgun.skyfit.presentation.mobile.features.onboarding

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.mobile.features.dashboard.unmanaged_role
import com.vurgun.skyfit.presentation.shared.navigation.Role
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun MobileOnboardingScreen(rootNavigator: Navigator) {
    val onboardingNavigator = rememberNavigator()

    val completeOnboarding: () -> Unit = {
        rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
    }

    NavHost(
        navigator = onboardingNavigator,
        initialRoute = NavigationRoute.OnboardingUserTypeSelection.route
    ) {
        scene(NavigationRoute.OnboardingUserTypeSelection.route) {
            MobileOnboardingUserTypeSelectionScreen(
                onClickUser = {
                    unmanaged_role = Role.USER
                    onboardingNavigator.jumpAndStay(NavigationRoute.OnboardingCharacterSelection)
                },
                onClickTrainer = {
                    unmanaged_role = Role.TRAINER
                    rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                },
                onClickFacility = {
                    unmanaged_role = Role.FACILITY_MANAGER
                    rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                }
            )
        }
        scene(NavigationRoute.OnboardingCharacterSelection.route) {
            MobileOnboardingCharacterSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(NavigationRoute.OnboardingBirthYearSelection)
                }
            )
        }
        scene(NavigationRoute.OnboardingBirthYearSelection.route) {
            MobileOnboardingBirthYearSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(NavigationRoute.OnboardingGenderSelection)
                }
            )
        }
        scene(NavigationRoute.OnboardingGenderSelection.route) {
            MobileOnboardingGenderSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(NavigationRoute.OnboardingWeightSelection)
                }
            )
        }
        scene(NavigationRoute.OnboardingWeightSelection.route) {
            MobileOnboardingWeightSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(NavigationRoute.OnboardingHeightSelection)
                }
            )
        }
        scene(NavigationRoute.OnboardingHeightSelection.route) {
            MobileOnboardingHeightSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(NavigationRoute.OnboardingGoalSelection)
                }
            )
        }
        scene(NavigationRoute.OnboardingGoalSelection.route) {
            MobileOnboardingGoalSelectionScreen(
                onSkip = {
                    rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                },
                onNext = {
                    onboardingNavigator.jumpAndStay(NavigationRoute.OnboardingCompleted)
                }
            )
        }
        scene(NavigationRoute.OnboardingCompleted.route) {
            MobileOnboardingCompletedScreen(
                onClickContinue = {
                    rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                }
            )
        }
    }
}
