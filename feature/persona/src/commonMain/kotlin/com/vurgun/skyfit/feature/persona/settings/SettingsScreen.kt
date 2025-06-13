package com.vurgun.skyfit.feature.persona.settings

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.core.data.v1.domain.global.model.UserRole
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen
import com.vurgun.skyfit.core.utils.rememberUserRole
import com.vurgun.skyfit.feature.persona.settings.facility.FacilitySettingsScreen
import com.vurgun.skyfit.feature.persona.settings.trainer.TrainerSettingsScreen
import com.vurgun.skyfit.feature.persona.settings.user.UserSettingsScreen

class SettingsScreen : Screen {
    @Composable
    override fun Content() {
        val userRole = rememberUserRole()

        val screen = when (userRole) {
            UserRole.Facility -> FacilitySettingsScreen()
            UserRole.Trainer -> TrainerSettingsScreen()
            UserRole.User -> UserSettingsScreen()
            UserRole.Guest -> UnauthorizedAccessScreen()
        }

        Navigator(screen) {
            CrossfadeTransition(it)
        }
    }
}

