package com.vurgun.skyfit.presentation.mobile.features.user.appointments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserAppointmentsScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                SkyFitScreenHeader("Randevularim", onBackClick = { navigator.popBackStack() })
                MobileUserAppointmentsScreenTabsComponent()
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
private fun MobileUserAppointmentsScreenTabsComponent() {
    TodoBox("MobileUserAppointmentsScreenTabsComponent", Modifier.size(430.dp, 52.dp))
}

@Composable
private fun MobileUserAppointmentsComponent() {
    TodoBox("MobileUserAppointmentsComponent", Modifier.size(430.dp, 488.dp))
}

@Composable
private fun MobileUserAppointmentsScreenCancelComponent() {
    TodoBox("MobileUserAppointmentsScreenCancelComponent", Modifier.size(382.dp, 172.dp))
}