package com.vurgun.skyfit.feature.dashboard

import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.screenModule
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.feature.dashboard.screen.DashboardMainScreen
import com.vurgun.skyfit.feature.explore.screen.ExploreScreen
import com.vurgun.skyfit.feature.home.screen.HomeScreen
import com.vurgun.skyfit.feature.profile.ProfileScreen
import com.vurgun.skyfit.feature.social.screen.SocialMediaScreen

val dashboardScreenModule = screenModule {
    register<SharedScreen.Dashboard> { DashboardMainScreen() }
    register<DashboardScreen.Home> { HomeScreen() }
    register<DashboardScreen.Explore> { ExploreScreen() }
    register<DashboardScreen.Social> { SocialMediaScreen() }
    register<DashboardScreen.Profile> { ProfileScreen() }
}

internal sealed class DashboardScreen : ScreenProvider {
    data object Home : DashboardScreen()
    data object Explore : DashboardScreen()
    data object Social : DashboardScreen()
    data object Profile : DashboardScreen()
}