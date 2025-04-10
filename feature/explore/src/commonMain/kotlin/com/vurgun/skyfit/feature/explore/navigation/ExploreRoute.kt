package com.vurgun.skyfit.feature.explore.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vurgun.skyfit.feature.explore.screen.MobileExploreScreen
import kotlinx.serialization.Serializable

sealed interface ExploreRoute {

    @Serializable
    data object Main
}

fun NavGraphBuilder.exploreRoutes() {
    
    composable<ExploreRoute.Main> {
        MobileExploreScreen(
            goToExercise = { },
            goToVisitTrainer = { },
            goToVisitFacility = { },
            goToExploreCommunities = { },
            goToExploreChallenges = { }
        )
    }
}