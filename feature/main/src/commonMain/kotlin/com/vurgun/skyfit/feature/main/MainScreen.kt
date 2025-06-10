package com.vurgun.skyfit.feature.main

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.vurgun.skyfit.core.navigation.SharedScreen

class MainScreen : Screen {

    @Composable
    override fun Content() {
        val dashboardScreen = rememberScreen(SharedScreen.Dashboard)

        Navigator(dashboardScreen) { navigator ->
            CurrentScreen()
        }
    }
}