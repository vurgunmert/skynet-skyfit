package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.domain.models.UserType
import com.vurgun.skyfit.feature_profile.ui.facility.MobileFacilityProfileScreen
import com.vurgun.skyfit.feature_profile.ui.trainer.MobileTrainerProfileScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserProfileScreen
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileDashboardProfileScreen(rootNavigator: Navigator) {
    when (unmanaged_role) {
        UserType.User -> MobileUserProfileScreen(rootNavigator)
        UserType.Trainer -> MobileTrainerProfileScreen(rootNavigator)
        UserType.Facility -> MobileFacilityProfileScreen(rootNavigator)
        else -> Unit
    }
}