package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingBirthYearSelection
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingBodyTypeSelection
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingCharacterSelection
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingCompleted
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingFacilityDetails
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingFacilityProfileTags
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingGenderSelection
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingHeightSelection
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingEnterProfile
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingUserGoalSelection
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingUserTypeSelection
import com.vurgun.skyfit.feature_navigation.MobileNavRoute.OnboardingWeightSelection
import com.vurgun.skyfit.feature_onboarding.domain.viewmodel.OnboardingViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.koinInject

@Composable
fun MobileOnboardingFlowNavGraph(
    rootNavigator: Navigator,
    viewModel: OnboardingViewModel = koinInject()
) {
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
            OnboardingUserTypeSelectionScreen(viewModel, onboardingNavigator)
        }

        scene(OnboardingCharacterSelection.route, navTransition = screenTransition) {
            MobileOnboardingCharacterSelectionScreen(viewModel, onboardingNavigator)
        }

        scene(OnboardingBirthYearSelection.route, navTransition = screenTransition) {
            MobileOnboardingBirthdaySelectionScreen(viewModel, onboardingNavigator)
        }

        scene(OnboardingGenderSelection.route, navTransition = screenTransition) {
            MobileOnboardingGenderSelectionScreen(viewModel, onboardingNavigator)
        }

        scene(OnboardingWeightSelection.route, navTransition = screenTransition) {
            MobileOnboardingWeightSelectionScreen(viewModel, onboardingNavigator)
        }

        scene(OnboardingHeightSelection.route, navTransition = screenTransition) {
            MobileOnboardingHeightSelectionScreen(viewModel, onboardingNavigator)
        }

        scene(OnboardingBodyTypeSelection.route, navTransition = screenTransition) {
            MobileOnboardingBodyTypeSelectionScreen(viewModel, onboardingNavigator)
        }

        scene(OnboardingUserGoalSelection.route, navTransition = screenTransition) {
            MobileOnboardingGoalSelectionScreen(viewModel, onboardingNavigator)
        }

        scene(OnboardingEnterProfile.route, navTransition = screenTransition) {
            MobileOnboardingEnterProfileScreen(viewModel, rootNavigator, onboardingNavigator)
        }

        scene(OnboardingFacilityDetails.route, navTransition = screenTransition) {
            MobileOnboardingFacilityDetailsScreen(viewModel, onboardingNavigator)
        }

        scene(OnboardingFacilityProfileTags.route, navTransition = screenTransition) {
            MobileOnboardingFacilityProfileTagsScreen(viewModel, rootNavigator, onboardingNavigator)
        }

        scene(OnboardingCompleted.route, navTransition = screenTransition) {
            MobileOnboardingCompletedScreen(viewModel, rootNavigator)
        }
    }
}
