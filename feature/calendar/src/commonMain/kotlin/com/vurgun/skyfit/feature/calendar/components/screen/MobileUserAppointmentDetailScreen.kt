package com.vurgun.skyfit.feature.calendar.components.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.event.AppointmentCardViewData
import com.vurgun.skyfit.ui.core.components.special.SkyFitScaffold
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_check_circle
import skyfit.ui.core.generated.resources.ic_chevron_left
import skyfit.ui.core.generated.resources.ic_clock
import skyfit.ui.core.generated.resources.ic_location_pin
import skyfit.ui.core.generated.resources.ic_profile
import skyfit.ui.core.generated.resources.trainer_note_label

@Composable
fun MobileUserAppointmentDetailScreen(
    goToBack: () -> Unit
) {
    val viewModel: UserAppointmentDetailViewModel = koinInject()

    // Load appointment data once
    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val appointmentState by viewModel.appointment.collectAsState()

    appointmentState?.let { appointmentData ->
        SkyFitScaffold(
            topBar = {
                MobileUserAppointmentDetailScreenToolbarComponent(
                    title = appointmentData.title,
                    onClickBack = goToBack,
                    status = appointmentData.status
                )
            }
        ) {
            Column(Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                MobileUserAppointmentDetailScreenInformationComponent(appointmentData)
                MobileUserAppointmentDetailScreenNoteComponent(appointmentData.note)
                Spacer(Modifier.weight(1f))

                if (appointmentData.status == "Planlanan") {
                    MobileUserAppointmentDetailScreenCancelActionComponent(onClick = viewModel::cancelAppointment)
                }
            }
        }
    }
}

// 🚀 **Toolbar Component (Title + Status Chip)**
@Composable
fun MobileUserAppointmentDetailScreenToolbarComponent(title: String, onClickBack: () -> Unit, status: String?) {
    Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
        Icon(
            painter = painterResource(Res.drawable.ic_chevron_left),
            contentDescription = "Back",
            tint = SkyFitColor.text.default,
            modifier = Modifier.align(Alignment.CenterStart).size(16.dp).clickable(onClick = onClickBack)
        )

        Text(
            text = title,
            style = SkyFitTypography.bodyLargeSemibold,
            modifier = Modifier.align(Alignment.Center)
        )

        if (status != null && status != "Planlanan") {
            AppointmentStatusChip(status = status, modifier = Modifier.align(Alignment.CenterEnd))
        }
    }
}

// 📌 **Appointment Details Grid**
@Composable
fun MobileUserAppointmentDetailScreenInformationComponent(appointment: AppointmentCardViewData) {
    Column(Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
        AppointmentDetailRow(
            leftIconRes = Res.drawable.ic_clock,
            leftTitle = "Tarih - Saat",
            leftValue = "${appointment.date}\n${appointment.hours}",
            rightIconRes = Res.drawable.ic_profile,
            rightTitle = "Eğitmen",
            rightValue = appointment.trainer
        )

        Spacer(Modifier.height(16.dp))

        AppointmentDetailRow(
            leftIconRes = Res.drawable.ic_location_pin,
            leftTitle = "Studio",
            leftValue = appointment.location,
            rightIconRes = Res.drawable.ic_check_circle,
            rightTitle = "Toplam Katılımcı",
            rightValue = appointment.capacity ?: "-"
        )
    }
}

// 📌 **New Component to Ensure Equal Height in a Row**
@Composable
fun AppointmentDetailRow(
    leftIconRes: DrawableResource,
    leftTitle: String,
    leftValue: String,
    rightIconRes: DrawableResource,
    rightTitle: String,
    rightValue: String
) {
    BoxWithConstraints(Modifier.fillMaxWidth()) {
        val maxHeight = remember { mutableStateOf(0.dp) } // Store max height

        Row(Modifier.fillMaxWidth()) {
            AppointmentDetailItemCard(leftIconRes, leftTitle, leftValue, Modifier.weight(1f), maxHeight)
            Spacer(Modifier.width(16.dp))
            AppointmentDetailItemCard(rightIconRes, rightTitle, rightValue, Modifier.weight(1f), maxHeight)
        }
    }
}

// 📌 **Updated Detail Card with Adjustable Height**
@Composable
fun AppointmentDetailItemCard(
    iconRes: DrawableResource,
    title: String,
    value: String,
    modifier: Modifier,
    maxHeight: MutableState<Dp>
) {
    val density = LocalDensity.current // Get current density for conversion

    Box(
        modifier
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
            .heightIn(min = maxHeight.value) // Apply max height to sync cards
            .onGloballyPositioned { coordinates ->
                val newHeight = with(density) { coordinates.size.height.toDp() } // Convert px → dp
                if (newHeight > maxHeight.value) {
                    maxHeight.value = newHeight // Update max height
                }
            }
            .padding(12.dp)
    ) {
        Column {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = "Icon",
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(16.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = title,
                style = SkyFitTypography.bodyMediumSemibold,
                color = SkyFitColor.text.secondary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                style = SkyFitTypography.bodyLargeSemibold
            )
        }
    }
}


// 📝 **Trainer Note**
@Composable
fun MobileUserAppointmentDetailScreenNoteComponent(note: String?) {
    if (!note.isNullOrEmpty()) {
        Text(
            text = stringResource(Res.string.trainer_note_label),
            style = SkyFitTypography.bodyMediumSemibold,
            modifier = Modifier.padding(top = 16.dp, start = 8.dp)
        )
        Text(
            text = note,
            style = SkyFitTypography.bodyMediumRegular,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .background(SkyFitColor.background.fillTransparent, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        )
    }
}

// ❌ **Cancel Appointment Button**
@Composable
fun MobileUserAppointmentDetailScreenCancelActionComponent(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(SkyFitColor.specialty.secondaryButtonRest, CircleShape)
            .border(1.dp, SkyFitColor.border.critical, CircleShape)
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "İptal Et",
            color = SkyFitColor.text.criticalOnBgFill,
            style = SkyFitTypography.bodyMediumSemibold
        )
    }
}

// 🎯 **Status Chip (Tamamlandı, Eksik, İptal)**
@Composable
fun AppointmentStatusChip(status: String, modifier: Modifier = Modifier) {
    val chipColor = when (status) {
        "Tamamlandı" -> SkyFitColor.background.surfaceSuccessActive
        "Eksik" -> SkyFitColor.background.surfaceCriticalActive
        "İptal" -> SkyFitColor.background.surfaceSecondary
        else -> SkyFitColor.background.surface
    }

    val textColor = when (status) {
        "Tamamlandı" -> SkyFitColor.text.successOnBgFill
        "Eksik" -> SkyFitColor.text.criticalOnBgFill
        "İptal" -> SkyFitColor.text.inverseSecondary
        else -> SkyFitColor.text.default
    }

    Box(
        modifier = modifier
            .height(28.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(chipColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = status,
            color = textColor,
            style = SkyFitTypography.bodyMediumMedium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

// 📌 **Individual Appointment Detail Item**
@Composable
fun AppointmentDetailItemCard(title: String, value: String, modifier: Modifier) {
    Box(
        modifier
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column {
            Text(
                text = title,
                style = SkyFitTypography.bodyMediumSemibold,
                color = SkyFitColor.text.secondary
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = value,
                style = SkyFitTypography.bodyLargeSemibold
            )
        }
    }
}
