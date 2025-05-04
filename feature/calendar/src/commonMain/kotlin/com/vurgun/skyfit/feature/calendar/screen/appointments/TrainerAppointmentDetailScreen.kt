package com.vurgun.skyfit.feature.calendar.screen.appointments

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect

class TrainerAppointmentDetailScreen(val lessonId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<TrainerAppointmentDetailViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                TrainerAppointmentDetailEffect.NavigateToBack -> {
                    navigator.pop()
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadAppointment(lessonId)
        }

        MobileTrainerAppointmentDetailScreen(viewModel = viewModel)
    }
}

@Composable
private fun MobileTrainerAppointmentDetailScreen(
    viewModel: TrainerAppointmentDetailViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        TrainerAppointmentDetailUiState.Loading -> FullScreenLoaderContent()
        is TrainerAppointmentDetailUiState.Error -> {
            val message = (uiState as TrainerAppointmentDetailUiState.Error).message
            ErrorScreen(
                message = message,
                onConfirm = { viewModel.onAction(TrainerAppointmentDetailAction.NavigateToBack) })
        }

        is TrainerAppointmentDetailUiState.Content -> {
            val content = (uiState as TrainerAppointmentDetailUiState.Content)
            MobileTrainerAppointmentDetailComponent.Content(content, viewModel::onAction)
        }
    }
}

private object MobileTrainerAppointmentDetailComponent {

    @Composable
    fun Content(
        content: TrainerAppointmentDetailUiState.Content,
        onAction: (TrainerAppointmentDetailAction) -> Unit
    ) {
        SkyFitMobileScaffold(
            topBar = {
                MobileAppointmentDetailComponent.MobileUserAppointmentDetail_TopBar(
                    title = content.lesson.title,
                    onClickBack = { onAction(TrainerAppointmentDetailAction.NavigateToBack) },
                    status = content.lesson.status,
                    statusName = content.lesson.statusName
                )
            }
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                MobileAppointmentDetailComponent.MobileUserAppointmentDetail_InfoGrid(
                    startDate = content.lesson.startDate.toString(),
                    startTime = content.lesson.startTime.toString(),
                    endTime = content.lesson.endTime.toString(),
                    trainerFullName = content.lesson.trainerFullName,
                    facilityName = content.lesson.facilityName,
                    participantCount = content.lesson.participantCount
                )
                MobileAppointmentDetailComponent.MobileUserAppointmentDetail_TrainerNote(content.lesson.trainerNote)
            }
        }
    }

    @Composable
    private fun ParticipantList(
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
}


