package com.vurgun.skyfit.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vurgun.skyfit.feature.onboarding.screen.MobileOnboardingScreen
import kotlinx.serialization.Serializable

@Serializable
data object Onboarding : OnboardingRoute

internal sealed interface OnboardingRoute {

    @Serializable
    data object UserTypeSelection : OnboardingRoute

    @Serializable
    data object CharacterSelection : OnboardingRoute

    @Serializable
    data object BirthYearSelection : OnboardingRoute

    @Serializable
    data object GenderSelection : OnboardingRoute

    @Serializable
    data object WeightSelection : OnboardingRoute

    @Serializable
    data object HeightSelection : OnboardingRoute

    @Serializable
    data object UserGoalSelection : OnboardingRoute

    @Serializable
    data object BodyTypeSelection : OnboardingRoute

    @Serializable
    data object EnterProfile : OnboardingRoute

    @Serializable
    data object FacilityDetails : OnboardingRoute

    @Serializable
    data object FacilityProfileTags : OnboardingRoute

    @Serializable
    data object Completed : OnboardingRoute
}

fun NavGraphBuilder.onboardingRoutes(
    goToLogin: () -> Unit,
    goToDashboard: () -> Unit,
) {
    composable<Onboarding> {
        MobileOnboardingScreen(
            goToLogin = goToLogin,
            goToDashboard = goToDashboard
        )
    }
}
