package com.vurgun.skyfit.feature.social.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vurgun.skyfit.feature.social.screen.MobileSocialMediaScreen
import kotlinx.serialization.Serializable

sealed interface SocialMediaRoute {

    @Serializable
    data object Main
}

fun NavGraphBuilder.socialMediaRoutes() {

    composable<SocialMediaRoute.Main> {
        MobileSocialMediaScreen(
            goToCreatePost = {  }
        )
    }
}