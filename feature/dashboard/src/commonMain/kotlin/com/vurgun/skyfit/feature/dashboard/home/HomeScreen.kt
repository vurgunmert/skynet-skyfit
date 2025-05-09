package com.vurgun.skyfit.feature.dashboard.home

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.data.domain.model.UserRole
import com.vurgun.skyfit.core.utils.rememberUserRole

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val userRole = rememberUserRole()

        when (userRole) {
            UserRole.Facility -> FacilityHomeScreen().Content()
            UserRole.Trainer -> TrainerHomeScreen().Content()
            UserRole.User -> UserHomeScreen().Content()
            UserRole.Guest -> Unit
        }
    }
}