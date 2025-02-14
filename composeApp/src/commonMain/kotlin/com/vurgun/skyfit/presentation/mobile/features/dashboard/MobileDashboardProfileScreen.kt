package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.profile.MobileTrainerProfileScreen
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfileScreen
import com.vurgun.skyfit.presentation.shared.navigation.Role
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileDashboardProfileScreen(rootNavigator: Navigator) {
    when (unmanaged_role) {
        Role.VISITOR -> Unit
        Role.USER -> MobileUserProfileScreen(rootNavigator)
        Role.TRAINER -> MobileTrainerProfileScreen(rootNavigator)
        Role.FACILITY_MANAGER -> MobileFacilityProfileScreen(rootNavigator)
    }
}