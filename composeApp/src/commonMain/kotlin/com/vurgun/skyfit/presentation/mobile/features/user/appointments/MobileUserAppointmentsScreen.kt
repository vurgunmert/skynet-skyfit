package com.vurgun.skyfit.presentation.mobile.features.user.appointments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitBadgeTabBarComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.viewmodel.UserAppointmentsViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.koin.compose.koinInject

@Composable
fun MobileUserAppointmentsScreen(navigator: Navigator) {
    val viewModel: UserAppointmentsViewModel = koinInject()

    val appointments by viewModel.appointments.collectAsState()
    var activeTab by remember { mutableStateOf(0) }
    val tabTitles by viewModel.tabTitles.collectAsState()

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                SkyFitScreenHeader("Randevularim", onBackClick = { navigator.popBackStack() })

                SkyFitBadgeTabBarComponent(
                    titles = tabTitles,
                    selectedTabIndex = activeTab,
                    onTabSelected = { index -> activeTab = index },
                    deleteAllEnabled = appointments.size + appointments.size > 0,
                    onDeleteAll = {  }
                )
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserAppointmentsComponent()
            MobileUserAppointmentsScreenCancelComponent()
        }
    }
}

@Composable
private fun MobileUserAppointmentsComponent() {
    TodoBox("MobileUserAppointmentsComponent", Modifier.size(430.dp, 488.dp))
}

@Composable
private fun MobileUserAppointmentsScreenCancelComponent() {
    TodoBox("MobileUserAppointmentsScreenCancelComponent", Modifier.size(382.dp, 172.dp))
}