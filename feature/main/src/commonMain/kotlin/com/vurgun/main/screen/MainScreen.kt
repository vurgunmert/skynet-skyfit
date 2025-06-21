package com.vurgun.main.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator

class MainScreen : Screen {

    @Composable
    override fun Content() {
        Navigator(DashboardScreen(), key = "main") { navigator ->
            CurrentScreen()
        }
    }
}