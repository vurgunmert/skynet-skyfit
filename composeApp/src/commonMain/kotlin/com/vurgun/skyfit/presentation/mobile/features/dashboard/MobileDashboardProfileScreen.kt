package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.domain.model.UserRole
import com.vurgun.skyfit.presentation.mobile.features.facility.profile.MobileFacilityProfileScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.profile.MobileTrainerProfileScreen
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfileScreen
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileDashboardProfileScreen(rootNavigator: Navigator) {
    when (unmanaged_role) {
        UserRole.VISITOR -> Unit
        UserRole.USER -> MobileUserProfileScreen(rootNavigator)
        UserRole.TRAINER -> MobileTrainerProfileScreen(rootNavigator)
        UserRole.FACILITY_MANAGER -> MobileFacilityProfileScreen(rootNavigator)
    }
}