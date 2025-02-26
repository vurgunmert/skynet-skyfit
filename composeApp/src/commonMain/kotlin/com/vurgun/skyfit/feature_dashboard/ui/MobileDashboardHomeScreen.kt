package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.domain.models.UserRole
import moe.tlaster.precompose.navigation.Navigator

//TODO: USER ROLE MANAGEMENT
var unmanaged_role = UserRole.USER

@Composable
fun MobileDashboardHomeScreen(rootNavigator: Navigator) {
    when (unmanaged_role) {
        UserRole.GUEST -> Unit
        UserRole.ADMIN -> Unit
        UserRole.USER -> MobileUserHomeScreen(rootNavigator)
        UserRole.TRAINER -> MobileTrainerHomeScreen(rootNavigator)
        UserRole.FACILITY_MANAGER -> MobileFacilityHomeScreen(rootNavigator)
    }
}