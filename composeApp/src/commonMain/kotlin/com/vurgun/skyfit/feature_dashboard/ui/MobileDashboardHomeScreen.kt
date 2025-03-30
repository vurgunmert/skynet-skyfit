package com.vurgun.skyfit.feature_dashboard.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.domain.models.UserType
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.error.ErrorDialog
import com.vurgun.skyfit.designsystem.components.loader.CircularLoader
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun MobileDashboardHomeScreen(rootNavigator: Navigator) {
    val viewModel: DashboardHomeViewModel = koinInject()

    val user by viewModel.user.collectAsState()

    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    SkyFitMobileScaffold {

        Box(Modifier.fillMaxSize()) {

            if (isLoading) {
                CircularLoader(Modifier.align(Alignment.Center))
            } else {
                when (user?.userType) {
                    is UserType.User -> MobileUserHomeScreen(user!!, rootNavigator)
                    is UserType.Trainer -> MobileTrainerHomeScreen(user!!, rootNavigator)
                    is UserType.Facility -> MobileFacilityHomeScreen(rootNavigator)
                    else -> ErrorDialog("No user", { rootNavigator.popBackStack() })
                }
            }
        }
    }

}