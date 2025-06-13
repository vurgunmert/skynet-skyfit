package com.vurgun.skyfit.feature.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import com.vurgun.skyfit.core.data.v1.domain.global.model.UserRole
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen
import com.vurgun.skyfit.core.utils.rememberUserRole
import com.vurgun.skyfit.feature.home.screen.facility.FacilityHomeScreen
import com.vurgun.skyfit.feature.home.screen.trainer.TrainerHomeScreen
import com.vurgun.skyfit.feature.home.screen.user.UserHomeScreen

class HomeScreen : Screen {

    override val key: ScreenKey
        get() = "home"

    @Composable
    override fun Content() {
        val userRole = rememberUserRole()
        val startScreen = remember(userRole) {
            when (userRole) {
                UserRole.Facility -> FacilityHomeScreen()
                UserRole.Trainer -> TrainerHomeScreen()
                UserRole.User -> UserHomeScreen()
                else -> UnauthorizedAccessScreen()
            }
        }
        Navigator(startScreen)
    }
}