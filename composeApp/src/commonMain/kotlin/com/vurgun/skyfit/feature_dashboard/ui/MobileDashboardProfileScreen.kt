package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.domain.models.UserRole
import com.vurgun.skyfit.feature_profile.ui.facility.MobileFacilityProfileScreen
import com.vurgun.skyfit.feature_profile.ui.trainer.MobileTrainerProfileScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserProfileScreen
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileDashboardProfileScreen(rootNavigator: Navigator) {
    when (unmanaged_role) {
        UserRole.ADMIN -> Unit
        UserRole.GUEST -> Unit
        UserRole.USER -> MobileUserProfileScreen(rootNavigator)
        UserRole.TRAINER -> MobileTrainerProfileScreen(rootNavigator)
        UserRole.FACILITY_MANAGER -> MobileFacilityProfileScreen(rootNavigator)
    }
}