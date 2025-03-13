package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.domain.model.UserType
import moe.tlaster.precompose.navigation.Navigator

//TODO: USER ROLE MANAGEMENT
var unmanaged_role: UserType = UserType.User

@Composable
fun MobileDashboardHomeScreen(rootNavigator: Navigator) {
    when (unmanaged_role) {
        is UserType.Guest -> Unit
        is UserType.User -> MobileUserHomeScreen(rootNavigator)
        is UserType.Trainer -> MobileTrainerHomeScreen(rootNavigator)
        is UserType.Facility -> MobileFacilityHomeScreen(rootNavigator)
    }
}