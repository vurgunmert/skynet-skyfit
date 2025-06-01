package com.vurgun.skyfit.feature.dashboard.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.feature.dashboard.DashboardScreen
import com.vurgun.skyfit.feature.dashboard.component.BottomNavigationBar

internal object DashboardLayoutCompact {
    @Composable
    fun Screen() {
        val appNavigator = LocalNavigator.currentOrThrow
        val homeScreen = rememberScreen(DashboardScreen.Home)
        val exploreScreen = rememberScreen(DashboardScreen.Explore)
        val profileScreen = rememberScreen(DashboardScreen.Profile)
        val socialScreen = rememberScreen(DashboardScreen.Social)

        Navigator(homeScreen) { dashboardNavigator ->
            SkyFitScaffold(
                bottomBar = {
                    BottomNavigationBar(
                        modifier = Modifier.padding(bottom = 12.dp),
                        currentScreen = dashboardNavigator.lastItem,
                        onClickHome = { dashboardNavigator.replace(homeScreen) },
                        onClickExplore = { dashboardNavigator.replace(exploreScreen) },
                        onClickSocial = { dashboardNavigator.replace(socialScreen) },
                        onClickProfile = { dashboardNavigator.replace(profileScreen) },
                        onClickAIBot = { appNavigator.push(SharedScreen.ChatBot) }
                    )
                }
            ) {
                CurrentScreen()
            }
        }
    }
}
