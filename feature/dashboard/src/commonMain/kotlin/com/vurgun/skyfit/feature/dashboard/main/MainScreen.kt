package com.vurgun.skyfit.feature.dashboard.main

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.vurgun.skyfit.feature.dashboard.screen.DashboardMainScreen

class MainScreen : Screen {

    @Composable
    override fun Content() {
        Navigator(DashboardMainScreen(), key = "main") { navigator ->
            CurrentScreen()
        }
    }
}