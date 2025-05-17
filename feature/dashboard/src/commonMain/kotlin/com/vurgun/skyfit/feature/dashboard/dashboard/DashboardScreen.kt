package com.vurgun.skyfit.feature.dashboard.dashboard

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.dashboard.DashboardScreen
import com.vurgun.skyfit.feature.dashboard.component.BottomNavigationBar

class DashboardMainScreen : Screen {
    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current

        if (windowSize == WindowSize.EXPANDED) {
            Expanded()
        } else {
            Compact()
        }
    }

    @Composable
    private fun Compact() {
        val appNavigator = LocalNavigator.currentOrThrow
        val homeScreen = rememberScreen(DashboardScreen.Home)
        val profileScreen = rememberScreen(DashboardScreen.Profile)

        Navigator(homeScreen) { dashboardNavigator ->
            SkyFitScaffold(
                bottomBar = {
                    BottomNavigationBar(
                        modifier = Modifier.padding(bottom = 12.dp),
                        currentScreen = dashboardNavigator.lastItem,
                        onClickHome = { dashboardNavigator.replace(homeScreen) },
                        onClickProfile = { dashboardNavigator.replace(profileScreen) },
                        onClickAppAction = { appNavigator.push(SharedScreen.PostureAnalysis) }
                    )
                }
            ) {
                CurrentScreen()
            }
        }
    }

    @Composable
    private fun Expanded() {
        val appNavigator = LocalNavigator.currentOrThrow
        val homeScreen = rememberScreen(DashboardScreen.Home)
        val profileScreen = rememberScreen(DashboardScreen.Profile)

        Navigator(homeScreen) { dashboardNavigator ->
            CurrentScreen()
        }
    }
}