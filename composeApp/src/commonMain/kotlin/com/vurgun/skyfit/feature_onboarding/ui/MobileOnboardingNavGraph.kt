package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.vurgun.skyfit.core.domain.models.UserType
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.BaseOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.FacilityOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.TrainerOnboardingViewModel
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.UserOnboardingViewModel
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import com.vurgun.skyfit.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator

@Composable
fun MobileOnboardingNavGraph(rootNavigator: Navigator) {

    val onboardingNavigator = rememberNavigator()

    var selectedUserType by remember { mutableStateOf<UserType?>(null) }

    val viewModel: BaseOnboardingViewModel? = when (selectedUserType) {
        UserType.USER -> remember { UserOnboardingViewModel() }
        UserType.TRAINER -> remember { TrainerOnboardingViewModel() }
        UserType.FACILITY_MANAGER -> remember { FacilityOnboardingViewModel() }
        else -> null
    }

    NavHost(
        navigator = onboardingNavigator,
        initialRoute = NavigationRoute.OnboardingUserTypeSelection.route
    ) {
        scene(NavigationRoute.OnboardingUserTypeSelection.route) {
            MobileOnboardingUserTypeSelectionScreen { userType ->
                selectedUserType = userType // Store the selected role
                when (userType) {
                    UserType.USER, UserType.TRAINER -> onboardingNavigator.jumpAndStay(NavigationRoute.OnboardingCharacterSelection)
                    UserType.FACILITY_MANAGER -> onboardingNavigator.jumpAndTakeover(
                        NavigationRoute.OnboardingUserTypeSelection,
                        NavigationRoute.OnboardingFacilityDetails
                    )

                    else -> Unit
                }
            }
        }

        // User & Trainer Flow
        scene(NavigationRoute.OnboardingCharacterSelection.route) {
            if (viewModel is UserOnboardingViewModel || viewModel is TrainerOnboardingViewModel) {
                MobileOnboardingCharacterSelectionScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }

        scene(NavigationRoute.OnboardingBirthYearSelection.route) {
            if (viewModel is UserOnboardingViewModel || viewModel is TrainerOnboardingViewModel) {
                MobileOnboardingBirthYearSelectionScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }
        scene(NavigationRoute.OnboardingGenderSelection.route) {
            if (viewModel is UserOnboardingViewModel || viewModel is TrainerOnboardingViewModel) {
                MobileOnboardingGenderSelectionScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }

        scene(NavigationRoute.OnboardingWeightSelection.route) {
            if (viewModel is UserOnboardingViewModel || viewModel is TrainerOnboardingViewModel) {
                MobileOnboardingWeightSelectionScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }

        scene(NavigationRoute.OnboardingHeightSelection.route) {
            if (viewModel is UserOnboardingViewModel || viewModel is TrainerOnboardingViewModel) {
                MobileOnboardingHeightSelectionScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }

        scene(NavigationRoute.OnboardingBodyTypeSelection.route) {
            if (viewModel is UserOnboardingViewModel || viewModel is TrainerOnboardingViewModel) {
                MobileOnboardingBodyTypeSelectionScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }

        scene(NavigationRoute.OnboardingUserGoalSelection.route) {
            if (viewModel is UserOnboardingViewModel) {
                MobileOnboardingGoalSelectionScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }

        scene(NavigationRoute.OnboardingTrainerDetails.route) {
            if (viewModel is TrainerOnboardingViewModel) {
                MobileOnboardingTrainerDetailsScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }

        scene(NavigationRoute.OnboardingFacilityDetails.route) {
            if (viewModel is FacilityOnboardingViewModel) {
                MobileOnboardingFacilityDetailsScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }
        scene(NavigationRoute.OnboardingFacilityProfileTags.route) {
            if (viewModel is FacilityOnboardingViewModel) {
                MobileOnboardingFacilityProfileTagsScreen(
                    viewModel = viewModel,
                    navigator = onboardingNavigator
                )
            }
        }

        scene(NavigationRoute.OnboardingCompleted.route) {
            if (viewModel is BaseOnboardingViewModel) {
                MobileOnboardingCompletedScreen(
                    viewModel = viewModel,
                    onClickContinue = {
                        rootNavigator.jumpAndTakeover(NavigationRoute.Onboarding, NavigationRoute.Dashboard)
                    }
                )
            }
        }
    }
}
