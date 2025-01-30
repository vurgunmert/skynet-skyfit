package com.vurgun.skyfit.presentation.mobile.features.user.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.UserAppointmentDetailViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserAppointmentDetailScreen(navigator: Navigator) {

    val showCancel = true
    val viewModel: UserAppointmentDetailViewModel = koinInject()

    SkyFitScaffold(
        topBar = {
            MobileUserAppointmentDetailScreenToolbarComponent(
                title = "Fitness",
                onClickBack = { navigator.popBackStack() },
                status = BookingStatus.PENDING
            )
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            MobileUserAppointmentDetailScreenInformationComponent()
            MobileUserAppointmentDetailScreenNoteComponent(viewModel.trainerNote)
            Spacer(Modifier.weight(1f))
            if (showCancel) {
                MobileUserAppointmentDetailScreenCancelActionComponent(onClick = viewModel::cancelAppointment)
            }
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
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Row {
            AppointmentDetailItemCard(Modifier.weight(1f))
            Spacer(Modifier.width(16.dp))
            AppointmentDetailItemCard(Modifier.weight(1f))
        }
        Spacer(Modifier.height(16.dp))
        Row {
            AppointmentDetailItemCard(Modifier.weight(1f))
            Spacer(Modifier.width(16.dp))
            AppointmentDetailItemCard(Modifier.weight(1f))
        }
    }
}

@Composable
private fun MobileUserAppointmentDetailScreenNoteComponent(note: String) {
    Text(
        text = "Eğitmenin Notu",
        style = SkyFitTypography.bodyMediumSemibold,
        modifier = Modifier.padding(top = 16.dp)
    )
    Text(
        text = note,
        style = SkyFitTypography.bodyMediumRegular,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(
                SkyFitColor.background.fillTransparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    )
}

@Composable
private fun MobileUserAppointmentDetailScreenCancelActionComponent(onClick: () -> Unit) {
    SkyFitButtonComponent(
        text = "İptal Et",
        onClick = onClick,
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Large,
        initialState = ButtonState.Rest
    )
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

@Composable
private fun AppointmentDetailItemCard(modifier: Modifier) {
    Box(
        modifier
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
    ) {

        Column(Modifier.padding(12.dp)) {
            Icon(
                painter = painterResource(Res.drawable.logo_skyfit),
                contentDescription = "Chip",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Tarih - Saat",
                style = SkyFitTypography.bodyMediumRegular
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "30/11/2024",
                style = SkyFitTypography.heading5
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "07:00-08:00",
                style = SkyFitTypography.heading5
            )
        }
    }
}
