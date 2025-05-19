package com.vurgun.skyfit.feature.persona.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.persona.domain.model.UserRole
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.utils.rememberUserRole
import com.vurgun.skyfit.feature.persona.SettingsHomeEntryPoint

class SettingsHostScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val userRole = rememberUserRole()

        val screenToShow = {
            when (userRole) {
                UserRole.Facility -> SettingsHomeEntryPoint.Facility
                UserRole.Trainer -> SettingsHomeEntryPoint.Trainer
                UserRole.User -> SettingsHomeEntryPoint.User
                UserRole.Guest -> SettingsHomeEntryPoint.Unauthorized
            }
        }

        val screen = rememberScreen(screenToShow.invoke())

        Navigator(screen, onBackPressed = { navigator.pop() }) {
            CurrentScreen()
        }
    }
}

