package com.vurgun.skyfit.feature.persona.profile

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.data.persona.domain.model.UserRole
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen
import com.vurgun.skyfit.core.utils.rememberUserRole
import com.vurgun.skyfit.feature.persona.profile.facility.owner.FacilityProfileOwnerScreen
import com.vurgun.skyfit.feature.persona.profile.trainer.owner.TrainerProfileOwnerScreen
import com.vurgun.skyfit.feature.persona.profile.user.owner.UserProfileOwnerScreen

class ProfileScreen : Screen {

    @Composable
    override fun Content() {
        val userRole = rememberUserRole()

        when (userRole) {
            UserRole.Facility -> FacilityProfileOwnerScreen().Content()
            UserRole.Trainer -> TrainerProfileOwnerScreen().Content()
            UserRole.User -> UserProfileOwnerScreen().Content()
            UserRole.Guest -> UnauthorizedAccessScreen().Content()
        }
    }
}