package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.domain.model.UserRole
import com.vurgun.skyfit.presentation.mobile.features.facility.home.MobileFacilityHomeScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.home.MobileTrainerHomeScreen
import com.vurgun.skyfit.presentation.mobile.features.user.home.MobileUserHomeScreen
import moe.tlaster.precompose.navigation.Navigator

//TODO: USER ROLE MANAGEMENT
var unmanaged_role = UserRole.USER

@Composable
fun MobileDashboardHomeScreen(rootNavigator: Navigator) {
    when (unmanaged_role) {
        UserRole.VISITOR -> Unit
        UserRole.USER -> MobileUserHomeScreen(rootNavigator)
        UserRole.TRAINER -> MobileTrainerHomeScreen(rootNavigator)
        UserRole.FACILITY_MANAGER -> MobileFacilityHomeScreen(rootNavigator)
    }
}