package com.vurgun.skyfit.feature.calendar.components.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.special.SkyFitScaffold
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography

@Composable
fun MobileTrainerAppointmentDetailScreen(goToBack: () -> Unit) {
    val viewModel = remember { TrainerAppointmentDetailViewModel() }

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    val appointmentState by viewModel.appointment.collectAsState()
    val participants by viewModel.participants.collectAsState()

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
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                MobileUserAppointmentDetailScreenInformationComponent(appointmentData)
                MobileUserAppointmentDetailScreenNoteComponent(appointmentData.note)

                // **Participants List (Trainer Can Mark Attendance)**
                TrainerAppointmentParticipantsComponent(
                    participants,
                    viewModel::toggleAttendance,
                    viewModel::saveAttendance
                )
            }
        }
    }
}

// **Participant List & Attendance**
@Composable
private fun TrainerAppointmentParticipantsComponent(
    participants: List<ParticipantViewData>,
    onToggleAttendance: (String) -> Unit,
    saveAttendance: () -> Unit
) {
    var isSaveEnabled by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth().padding(top = 16.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Katılımcı Listesi", style = SkyFitTypography.bodyMediumSemibold)

            Text(
                text = if (isSaveEnabled) "Kaydet" else "Düzenle",
                color = SkyFitColor.text.linkInverse,
                style = SkyFitTypography.bodyMediumRegular,
                modifier = Modifier.clickable {
                    if (isSaveEnabled) {
                        saveAttendance()
                        isSaveEnabled = false // Reset after saving
                    }
                }
            )
        }
        Spacer(Modifier.height(4.dp))

        LazyColumn {
            items(participants) { participant ->
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = participant.name,
                            style = SkyFitTypography.bodyMediumRegular,
                            modifier = Modifier.weight(1f)
                        )
                        Checkbox(
                            checked = participant.isPresent,
                            onCheckedChange = {
                                onToggleAttendance(participant.id)
                                isSaveEnabled = true // Enable save when attendance changes
                            },
                            colors = CheckboxDefaults.colors(
                                checkmarkColor = SkyFitColor.icon.active,
                                uncheckedColor = SkyFitColor.icon.default
                            )
                        )
                    }
                    Divider(color = SkyFitColor.border.default, thickness = 1.dp)
                }
            }
        }
    }
}

