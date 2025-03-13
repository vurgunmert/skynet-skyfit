package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingBirthYearSelection
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingBodyTypeSelection
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingCharacterSelection
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingCompleted
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingFacilityDetails
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingFacilityProfileTags
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingGenderSelection
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingHeightSelection
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingTrainerDetails
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingUserGoalSelection
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingUserTypeSelection
import com.vurgun.skyfit.feature_navigation.NavigationRoute.OnboardingWeightSelection
import com.vurgun.skyfit.feature_onboarding.ui.viewmodel.OnboardingViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MobileOnboardingNavGraph(rootNavigator: Navigator) {

    val onboardingViewModel: OnboardingViewModel = koinViewModel()
    val onboardingNavigator = rememberNavigator()

    val screenTransition = NavTransition(
        createTransition = fadeIn(),
        destroyTransition = fadeOut(),
        pauseTransition = fadeOut(),
        resumeTransition = fadeIn()
    )

    NavHost(
        navigator = onboardingNavigator,
        initialRoute = OnboardingUserTypeSelection.route
    ) {
        scene(OnboardingUserTypeSelection.route, navTransition = screenTransition) {
            OnboardingUserTypeSelectionScreen(onboardingViewModel, onboardingNavigator)
        }

        // User & Trainer Flow
        scene(OnboardingCharacterSelection.route, navTransition = screenTransition) {
            MobileOnboardingCharacterSelectionScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingBirthYearSelection.route, navTransition = screenTransition) {
            MobileOnboardingBirthdaySelectionScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingGenderSelection.route, navTransition = screenTransition) {
            MobileOnboardingGenderSelectionScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingWeightSelection.route, navTransition = screenTransition) {
            MobileOnboardingWeightSelectionScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingHeightSelection.route, navTransition = screenTransition) {
            MobileOnboardingHeightSelectionScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingBodyTypeSelection.route, navTransition = screenTransition) {
            MobileOnboardingBodyTypeSelectionScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingUserGoalSelection.route, navTransition = screenTransition) {
            MobileOnboardingGoalSelectionScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingTrainerDetails.route, navTransition = screenTransition) {
            MobileOnboardingTrainerDetailsScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingFacilityDetails.route, navTransition = screenTransition) {
            MobileOnboardingFacilityDetailsScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingFacilityProfileTags.route, navTransition = screenTransition) {
            MobileOnboardingFacilityProfileTagsScreen(onboardingViewModel, onboardingNavigator)
        }

        scene(OnboardingCompleted.route, navTransition = screenTransition) {
            MobileOnboardingCompletedScreen(onboardingViewModel, rootNavigator)
        }
    }
}
