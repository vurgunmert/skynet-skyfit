package com.vurgun.skyfit.presentation.mobile.features.dashboard

import androidx.compose.runtime.Composable
import com.vurgun.skyfit.presentation.mobile.features.user.profile.MobileUserProfileScreen
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileDashboardProfileScreen(rootNavigator: Navigator) {
    MobileUserProfileScreen(rootNavigator)
}