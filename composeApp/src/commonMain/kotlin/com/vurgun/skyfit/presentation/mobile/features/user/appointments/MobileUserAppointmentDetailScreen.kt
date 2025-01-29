package com.vurgun.skyfit.presentation.mobile.features.user.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.data.network.models.BookingStatus
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserAppointmentDetailScreen(navigator: Navigator) {

    SkyFitScaffold {
        Column {
            MobileUserAppointmentDetailScreenToolbarComponent(
                title = "Fitness",
                onClickBack = { navigator.popBackStack() },
                status = BookingStatus.ABSENT
            )
            MobileUserAppointmentDetailScreenInformationComponent()
            MobileUserAppointmentDetailScreenNoteComponent()
            Spacer(Modifier.weight(1f))
            MobileUserAppointmentDetailScreenCancelActionComponent()
        }
    }
}

@Composable
private fun MobileUserAppointmentDetailScreenToolbarComponent(title: String, onClickBack: () -> Unit, status: BookingStatus) {
    Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
        IconButton(
            onClick = onClickBack,
            modifier = Modifier
                .align(Alignment.CenterStart)
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Back",
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(16.dp)
            )
        }

        Text(
            text = title,
            style = SkyFitTypography.bodyLargeSemibold,
            modifier = Modifier.align(Alignment.Center)
        )

        AppointmentStatusChip(
            status = status,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
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

@Composable
fun AppointmentStatusChip(status: BookingStatus, modifier: Modifier = Modifier) {
    val label = when (status) {
        BookingStatus.PENDING -> "Belli Degil"
        BookingStatus.CONFIRMED -> "Gelecegim"
        BookingStatus.CANCELLED -> "İptal"
        BookingStatus.COMPLETED -> "Tamamlandı"
        BookingStatus.ABSENT -> "Eksik"
    }
    val chipColor = when (status) {
        BookingStatus.PENDING -> SkyFitColor.background.surface
        BookingStatus.CONFIRMED -> SkyFitColor.background.surface
        BookingStatus.CANCELLED -> SkyFitColor.background.surfaceSecondary
        BookingStatus.COMPLETED -> SkyFitColor.background.surfaceSuccessActive
        BookingStatus.ABSENT -> SkyFitColor.background.surfaceCriticalActive
    }
    val textColor = when (status) {
        BookingStatus.PENDING -> SkyFitColor.text.default
        BookingStatus.CONFIRMED -> SkyFitColor.text.default
        BookingStatus.CANCELLED -> SkyFitColor.text.inverseSecondary
        BookingStatus.COMPLETED -> SkyFitColor.text.successOnBgFill
        BookingStatus.ABSENT -> SkyFitColor.text.criticalOnBgFill
    }

    Box(
        modifier = modifier
            .height(28.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(chipColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            style = SkyFitTypography.bodyMediumMedium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}