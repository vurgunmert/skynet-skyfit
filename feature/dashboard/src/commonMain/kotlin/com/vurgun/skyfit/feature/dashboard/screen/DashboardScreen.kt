package com.vurgun.skyfit.feature.dashboard.screen

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
import com.vurgun.skyfit.feature.dashboard.DashboardScreen
import com.vurgun.skyfit.feature.dashboard.component.BottomNavigationBar

class DashboardMainScreen : Screen {
    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()

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
}