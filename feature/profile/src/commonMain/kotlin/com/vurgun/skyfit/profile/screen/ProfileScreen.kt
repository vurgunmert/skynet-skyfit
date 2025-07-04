package com.vurgun.skyfit.profile.screen

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.CrossfadeTransition
import com.vurgun.skyfit.core.data.v1.domain.global.model.AccountRole
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen
import com.vurgun.skyfit.core.utils.rememberUserRole
import com.vurgun.skyfit.profile.facility.screen.FacilityProfileScreen
import com.vurgun.skyfit.profile.trainer.TrainerProfileScreen
import com.vurgun.skyfit.profile.user.screen.UserProfileScreen

class ProfileScreen : Screen {

    override val key: ScreenKey = SharedScreen.Profile.key

    @Composable
    override fun Content() {
        val userRole = rememberUserRole()

        val screen = when (userRole) {
            AccountRole.Facility -> FacilityProfileScreen()
            AccountRole.Trainer -> TrainerProfileScreen()
            AccountRole.User -> UserProfileScreen()
            AccountRole.Guest -> UnauthorizedAccessScreen()
        }

        Navigator(screen) { profileNavigation ->
            CrossfadeTransition(profileNavigation)
        }
    }
}