package com.vurgun.skyfit.core.navigation

import cafe.adriel.voyager.core.registry.ScreenProvider
import kotlinx.serialization.Serializable

@Serializable
sealed class SharedScreen : ScreenProvider {

    // Splash / Auth
    data object Splash : SharedScreen()
    data object Maintenance : SharedScreen()
    data object Login : SharedScreen()
    data object Register : SharedScreen()
    data object Onboarding : SharedScreen()

    // Main App
    data object Dashboard : SharedScreen()
    data object Home : SharedScreen()
    data object Explore : SharedScreen()
    data object Profile : SharedScreen()

    // Explore Sub Screens
    data object ExploreHome : SharedScreen()
    data object ExploreFacilities : SharedScreen()
    data object ExploreTrainers : SharedScreen()

    // Features
    data object Settings : SharedScreen()
    data object Courses : SharedScreen()
    data object Appointments : SharedScreen()

    // Social
    data object Conversations : SharedScreen()

    @Serializable
    data class UserChat(val participantId: Int) : SharedScreen()
}
