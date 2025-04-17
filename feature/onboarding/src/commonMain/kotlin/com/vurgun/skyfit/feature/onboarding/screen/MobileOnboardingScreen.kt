package com.vurgun.skyfit.feature.onboarding.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vurgun.skyfit.feature.onboarding.navigation.OnboardingRoute
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MobileOnboardingScreen(
    isAddition: Boolean = false,
    goToLogin: () -> Unit,
    goToDashboard: () -> Unit,
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val onboardingNavigator = rememberNavController()

    LaunchedEffect(Unit) {
        viewModel.updateIsAccountAddition(isAddition)
    }

    NavHost(
        onboardingNavigator,
        startDestination = OnboardingRoute.UserTypeSelection
    ) {
        composable<OnboardingRoute.UserTypeSelection> {
            OnboardingUserTypeSelectionScreen(
                viewModel = viewModel,
                goToCharacterSelection = { onboardingNavigator.navigate(OnboardingRoute.CharacterSelection) },
                goToFacilityDetails = { onboardingNavigator.navigate(OnboardingRoute.FacilityDetails) }
            )
        }

        composable<OnboardingRoute.CharacterSelection> {
            MobileOnboardingCharacterSelectionScreen(
                viewModel = viewModel,
                goToBirthdaySelection = { onboardingNavigator.navigate(OnboardingRoute.BirthYearSelection) }
            )
        }

        composable<OnboardingRoute.BirthYearSelection> {
            MobileOnboardingBirthdaySelectionScreen(
                viewModel = viewModel,
                goToGenderSelection = { onboardingNavigator.navigate(OnboardingRoute.GenderSelection) }
            )
        }

        composable<OnboardingRoute.GenderSelection> {
            MobileOnboardingGenderSelectionScreen(
                viewModel = viewModel,
                goToWeightSelection = { onboardingNavigator.navigate(OnboardingRoute.WeightSelection) }
            )
        }

        composable<OnboardingRoute.WeightSelection> {
            MobileOnboardingWeightSelectionScreen(
                viewModel = viewModel,
                goToHeightSelection = { onboardingNavigator.navigate(OnboardingRoute.HeightSelection) }
            )
        }

        composable<OnboardingRoute.HeightSelection> {
            MobileOnboardingHeightSelectionScreen(
                viewModel = viewModel,
                goToBodyTypeSelection = { onboardingNavigator.navigate(OnboardingRoute.BodyTypeSelection) }
            )
        }

        composable<OnboardingRoute.BodyTypeSelection> {
            MobileOnboardingBodyTypeSelectionScreen(
                viewModel = viewModel,
                goToGoalSelection = { onboardingNavigator.navigate(OnboardingRoute.UserGoalSelection) },
                goToEnterTrainerProfile = { onboardingNavigator.navigate(OnboardingRoute.EnterProfile) },
                goToFacilityDetail = { onboardingNavigator.navigate(OnboardingRoute.FacilityDetails) }
            )
        }

        composable<OnboardingRoute.UserGoalSelection> {
            MobileOnboardingGoalSelectionScreen(
                viewModel = viewModel,
                goToEnterProfile = { onboardingNavigator.navigate(OnboardingRoute.EnterProfile) }
            )
        }

        composable<OnboardingRoute.EnterProfile> {
            MobileOnboardingEnterProfileScreen(
                viewModel = viewModel,
                goToCompleted = {
                    onboardingNavigator.navigate(OnboardingRoute.Completed) {
                        popUpTo(OnboardingRoute.UserTypeSelection) { inclusive = true }
                    }
                },
                goToLogin = goToLogin
            )
        }

        composable<OnboardingRoute.FacilityDetails> {
            MobileOnboardingFacilityDetailsScreen(
                viewModel = viewModel,
                goToProfileTags = { onboardingNavigator.navigate(OnboardingRoute.FacilityProfileTags) },
            )
        }

        composable<OnboardingRoute.FacilityProfileTags> {
            MobileOnboardingFacilityProfileTagsScreen(
                viewModel = viewModel,
                goToCompleted = {
                    onboardingNavigator.navigate(OnboardingRoute.Completed) {
                        popUpTo(OnboardingRoute.UserTypeSelection) { inclusive = true }
                    }
                },
                goToLogin = goToLogin
            )
        }

        composable<OnboardingRoute.Completed> {
            MobileOnboardingCompletedScreen(
                viewModel = viewModel,
                goToDashboard = goToDashboard
            )
        }
    }
}