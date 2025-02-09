package com.vurgun.skyfit.presentation.mobile.features.user.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.presentation.shared.components.SkyFitBadgeTabBarComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitIcon
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.presentation.shared.viewmodel.UserAppointmentsViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserAppointmentsScreen(navigator: Navigator) {
    val viewModel: UserAppointmentsViewModel = koinInject()

    val appointments by viewModel.filteredAppointments.collectAsState()
    val activeTab by viewModel.activeTab.collectAsState()
    val tabTitles by viewModel.tabTitles.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }
    var appointmentToDelete by remember { mutableStateOf<AppointmentCardViewData?>(null) }
    var showDeleteAllDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                SkyFitScreenHeader("Randevularım", onClickBack = { navigator.popBackStack() })

                SkyFitBadgeTabBarComponent(
                    titles = tabTitles,
                    selectedTabIndex = activeTab,
                    onTabSelected = { index -> viewModel.updateActiveTab(index) },
                    deleteAllEnabled = appointments.isNotEmpty(),
                    onDeleteAll = { showDeleteAllDialog = true }
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(appointments) { appointment ->
                BookedAppointmentCardItemComponent(
                    item = appointment,
                    modifier = Modifier.fillMaxWidth(),
                    onDeleteClick = {
                        appointmentToDelete = appointment
                        showDeleteDialog = true
                    }
                )
            }
        }
    }

    // Confirm Delete Single Appointment
    AppointmentDeleteDialog(
        showDialog = showDeleteDialog,
        onDismiss = { showDeleteDialog = false },
        onConfirm = {
            appointmentToDelete?.let { viewModel.deleteAppointment(it) }
            showDeleteDialog = false
        }
    )

    // Confirm Delete All Appointments
    AppointmentDeleteDialog(
        showDialog = showDeleteAllDialog,
        onDismiss = { showDeleteAllDialog = false },
        onConfirm = {
            viewModel.deleteAllAppointments()
            showDeleteAllDialog = false
        },
        title = "Tüm Randevuları Sil",
        message = "Tüm randevuları silmek istediğinize emin misiniz?"
    )
}


@Composable
fun BookedAppointmentCardItemComponent(
    item: AppointmentCardViewData,
    modifier: Modifier = Modifier,
    onDeleteClick: (() -> Unit)? = null
) {
    Column(
        modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        // Title Row: Icon + Title + Date + Status/Delete
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Exercise Icon
            Icon(
                painter = painterResource(SkyFitIcon.getIconResource("barbell") ?: Res.drawable.logo_skyfit),
                contentDescription = item.title,
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(8.dp))

            // Title
            Text(
                modifier = Modifier.weight(1f),
                text = item.title,
                maxLines = 1,
                style = SkyFitTypography.bodyMediumSemibold
            )

            // Date
            Text(
                text = item.date,
                style = SkyFitTypography.bodyMediumSemibold,
                color = SkyFitColor.text.secondary
            )

            Spacer(Modifier.width(8.dp))
            // Status OR Delete Icon
            if (item.status == "Planlanan") {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onDeleteClick?.invoke() }
                )
            } else {
                Text(
                    text = item.status ?: "",
                    style = SkyFitTypography.bodyMediumSemibold,
                    color = when (item.status) {
                        "Tamamlandı" -> Color.Green
                        "Eksik" -> Color.Red
                        "İptal" -> Color.Gray
                        else -> SkyFitColor.text.secondary
                    }
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        // Time & Category
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AppointmentSingleDataBoxComponent(item.hours, modifier = Modifier.weight(1f))
            Spacer(Modifier.width(8.dp))
            AppointmentSingleDataBoxComponent(item.category, modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(8.dp))

        // Location & Trainer
        Row {
            AppointmentSingleDataBoxComponent(item.location, modifier = Modifier.weight(1f))
            Spacer(Modifier.width(8.dp))
            AppointmentSingleDataBoxComponent(item.trainer, modifier = Modifier.weight(1f))
        }

        // Capacity & Cost
        if (item.capacity != null && item.cost != null) {
            Spacer(Modifier.height(8.dp))
            Row {
                AppointmentSingleDataBoxComponent(item.capacity, modifier = Modifier.weight(1f))
                Spacer(Modifier.width(8.dp))
                AppointmentSingleDataBoxComponent(item.cost, modifier = Modifier.weight(1f))
            }
        }

        // Class Notes
        item.note?.let {
            Spacer(Modifier.height(8.dp))
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Note",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = it,
                    style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
                )
            }
        }
    }
}

@Composable
private fun AppointmentDeleteDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String = "Randevuyu İptal Et",
    message: String = "Randevunuzu iptal etmek istiyor musunuz?"
) {
    SkyFitDestructiveDialogComponent(
        showDialog = showDialog,
        title = title,
        message = message,
        onDismiss = onDismiss,
        onConfirm = onConfirm
    )
}

@Composable
private fun SkyFitDestructiveDialogComponent(
    showDialog: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmText: String = "Evet, İptal Et",
    dismissText: String = "Hayır, Geri Dön"
) {
    if (showDialog) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Close Button
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onDismiss() }
                        )
                    }

                    // Title
                    Text(
                        text = title,
                        style = SkyFitTypography.bodyLargeMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Message
                    Text(
                        text = message,
                        style = SkyFitTypography.bodyLargeMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Buttons Row (Single Line)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Confirm Button (Destructive)
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(50))
                                .border(1.dp, SkyFitColor.border.critical, RoundedCornerShape(50))
                                .clickable { onConfirm() }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = confirmText,
                                color = SkyFitColor.text.criticalOnBgFill,
                                style = SkyFitTypography.bodyMediumSemibold
                            )
                        }

                        // Dismiss Button
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(50))
                                .background(SkyFitColor.specialty.buttonBgRest)
                                .clickable { onDismiss() }
                                .padding(vertical = 12.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = dismissText,
                                color = SkyFitColor.text.inverse,
                                style = SkyFitTypography.bodyMediumSemibold
                            )
                        }
                    }
                }
            }
        }
    }
}
