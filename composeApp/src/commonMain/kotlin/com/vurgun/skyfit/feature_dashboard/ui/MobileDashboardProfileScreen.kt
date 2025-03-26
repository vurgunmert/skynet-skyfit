package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.domain.models.UserType
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.loader.CircularLoader
import com.vurgun.skyfit.feature_profile.ui.facility.MobileFacilityProfileScreen
import com.vurgun.skyfit.feature_profile.ui.trainer.MobileTrainerProfileScreen
import com.vurgun.skyfit.feature_profile.ui.user.MobileUserProfileScreen
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun MobileDashboardProfileScreen(rootNavigator: Navigator) {
    val viewModel: DashboardHomeViewModel = koinInject()

    val user by viewModel.user.collectAsState()

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    SkyFitMobileScaffold {

        Box(Modifier.fillMaxSize()) {
            if (isLoading) {
                CircularLoader(Modifier.size(240.dp).align(Alignment.Center))
            } else {
                when (user?.userType) {
                    UserType.User -> MobileUserProfileScreen(rootNavigator)
                    UserType.Trainer -> MobileTrainerProfileScreen(rootNavigator)
                    UserType.Facility -> MobileFacilityProfileScreen(rootNavigator)
                    else -> Unit
                }
            }
        }
    }
}