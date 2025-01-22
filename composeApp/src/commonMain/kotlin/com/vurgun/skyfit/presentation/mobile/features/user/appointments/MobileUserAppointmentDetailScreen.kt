package com.vurgun.skyfit.presentation.mobile.features.user.appointments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserAppointmentDetailScreen(navigator: Navigator) {

    SkyFitScaffold {
        Column {
            MobileUserAppointmentDetailScreenToolbarComponent()
            MobileUserAppointmentDetailScreenInformationComponent()
            MobileUserAppointmentDetailScreenNoteComponent()
            Spacer(Modifier.weight(1f))
            MobileUserAppointmentDetailScreenCancelActionComponent()
        }
    }
}

@Composable
private fun MobileUserAppointmentDetailScreenToolbarComponent() {
    TodoBox("MobileUserAppointmentDetailScreenToolbarComponent", Modifier.size(430.dp, 44.dp))
}

@Composable
private fun MobileUserAppointmentDetailScreenInformationComponent() {
    TodoBox("MobileUserAppointmentDetailScreenInformationComponent", Modifier.size(430.dp, 280.dp))
}

@Composable
private fun MobileUserAppointmentDetailScreenNoteComponent() {
    TodoBox("MobileUserAppointmentDetailScreenNoteComponent", Modifier.size(430.dp, 124.dp))
}

@Composable
private fun MobileUserAppointmentDetailScreenCancelActionComponent() {
    TodoBox("MobileUserAppointmentDetailScreenCancelActionComponent", Modifier.size(430.dp, 80.dp))
}