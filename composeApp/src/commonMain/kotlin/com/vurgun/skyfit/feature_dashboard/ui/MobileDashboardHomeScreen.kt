package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.core.domain.models.UserDetail
import com.vurgun.skyfit.core.domain.models.UserType
import moe.tlaster.precompose.navigation.Navigator

//TODO: USER ROLE MANAGEMENT
var unmanaged_role: UserType = UserType.User
var unmanagedUser: UserDetail? = null

@Composable
fun MobileDashboardHomeScreen(rootNavigator: Navigator) {
    when (unmanagedUser?.userType) {
        is UserType.User -> MobileUserHomeScreen(rootNavigator)
        is UserType.Trainer -> MobileTrainerHomeScreen(rootNavigator)
        is UserType.Facility -> MobileFacilityHomeScreen(rootNavigator)
        else -> Unit
    }
}