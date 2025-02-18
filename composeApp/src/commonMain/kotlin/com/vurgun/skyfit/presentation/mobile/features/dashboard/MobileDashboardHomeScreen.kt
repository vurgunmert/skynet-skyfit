package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.mobile.features.facility.home.MobileFacilityHomeScreen
import com.vurgun.skyfit.presentation.mobile.features.trainer.home.MobileTrainerHomeScreen
import com.vurgun.skyfit.presentation.mobile.features.user.home.MobileUserHomeScreen
import com.vurgun.skyfit.presentation.shared.navigation.Role
import moe.tlaster.precompose.navigation.Navigator

//TODO: USER ROLE MANAGEMENT
var unmanaged_role = Role.FACILITY_MANAGER

@Composable
fun MobileDashboardHomeScreen(rootNavigator: Navigator) {
    when(unmanaged_role){
        Role.VISITOR -> Unit
        Role.USER -> MobileUserHomeScreen(rootNavigator)
        Role.TRAINER -> MobileTrainerHomeScreen(rootNavigator)
        Role.FACILITY_MANAGER -> MobileFacilityHomeScreen(rootNavigator)
    }
}