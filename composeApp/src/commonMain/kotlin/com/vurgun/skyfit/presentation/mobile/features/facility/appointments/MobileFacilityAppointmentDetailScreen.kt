package com.vurgun.skyfit.presentation.mobile.features.facility.appointments

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityAppointmentDetailScreen(navigator: Navigator) {

    val isCancelling: Boolean = true

    SkyFitScaffold {
        Box(Modifier.fillMaxSize()) {
            Column {
                MobileFacilityAppointmentDetailScreenToolbarComponent()
                MobileFacilityAppointmentDetailScreenInformationComponent()
                MobileFacilityAppointmentDetailScreenNoteComponent()
                Spacer(Modifier.weight(1f))
                MobileFacilityAppointmentDetailScreenCancelActionComponent()
            }

            if (isCancelling) {
                MobileFacilityAppointmentDetailScreenCancelDialogActionComponent()
            }
        }
    }
}

@Composable
private fun MobileFacilityAppointmentDetailScreenToolbarComponent() {
    TodoBox("MobileFacilityAppointmentDetailScreenToolbarComponent", Modifier.size(430.dp, 44.dp))
}

@Composable
private fun MobileFacilityAppointmentDetailScreenInformationComponent() {
    TodoBox("MobileFacilityAppointmentDetailScreenInformationComponent", Modifier.size(430.dp, 280.dp))
}

@Composable
private fun MobileFacilityAppointmentDetailScreenNoteComponent() {
    TodoBox("MobileFacilityAppointmentDetailScreenNoteComponent", Modifier.size(430.dp, 124.dp))
}

@Composable
private fun MobileFacilityAppointmentDetailScreenCancelActionComponent() {
    TodoBox("MobileFacilityAppointmentDetailScreenCancelActionComponent", Modifier.size(430.dp, 80.dp))
}

@Composable
private fun MobileFacilityAppointmentDetailScreenCancelDialogActionComponent() {
    TodoBox("MobileFacilityAppointmentDetailScreenCancelActionComponent", Modifier.size(390.dp, 240.dp))
}