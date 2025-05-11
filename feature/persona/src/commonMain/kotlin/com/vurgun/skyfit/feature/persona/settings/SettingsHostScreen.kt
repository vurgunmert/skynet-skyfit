package com.vurgun.skyfit.feature.persona.settings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.vurgun.skyfit.core.data.persona.domain.model.UserRole
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.ui.screen.LoadingScreen
import com.vurgun.skyfit.core.utils.rememberUserRole
import com.vurgun.skyfit.feature.persona.SettingsHomeEntryPoint

class SettingsHostScreen : Screen {
    @Composable
    override fun Content() {
        val userRole = rememberUserRole()

        Navigator(LoadingScreen) { localNavigator ->

            LaunchedEffect(userRole) {
                val targetScreen = when (userRole) {
                    UserRole.Facility -> SettingsHomeEntryPoint.Facility
                    UserRole.Trainer -> SettingsHomeEntryPoint.Trainer
                    UserRole.User -> SettingsHomeEntryPoint.User
                    UserRole.Guest -> SettingsHomeEntryPoint.Unauthorized
                }

                localNavigator.replace(targetScreen)
            }

            CurrentScreen()
        }
    }
}