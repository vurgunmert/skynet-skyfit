package com.vurgun.skyfit.settings

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen
import com.vurgun.skyfit.core.utils.rememberUserRole
import com.vurgun.skyfit.feature.persona.settings.trainer.TrainerSettingsScreen
import com.vurgun.skyfit.settings.facility.FacilitySettingsScreen
import com.vurgun.skyfit.settings.user.UserSettingsScreen

class SettingsScreen : Screen {

    override val key: ScreenKey get() = SharedScreen.Settings.key

    @Composable
    override fun Content() {
        val userRole = rememberUserRole()

        val screen = when (userRole) {
            AccountRole.Facility -> FacilitySettingsScreen()
            AccountRole.Trainer -> TrainerSettingsScreen()
            AccountRole.User -> UserSettingsScreen()
            AccountRole.Guest -> UnauthorizedAccessScreen()
        }

        Navigator(screen) {
            CrossfadeTransition(it)
        }
    }
}