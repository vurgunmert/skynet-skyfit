package com.vurgun.skyfit.feature.schedule.screen.appointments

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonParticipant
import com.vurgun.skyfit.core.ui.components.button.PrimaryMicroButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryDestructiveMicroButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryMediumButton
import com.vurgun.skyfit.core.ui.components.chip.RectangleChip
import com.vurgun.skyfit.core.ui.components.image.CircleNetworkImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitCheckBox
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyLargeMediumText
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect

class TrainerAppointmentDetailScreen(private val lessonId: Int) : Screen {

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

                if (content.participants.isNotEmpty()) {
                    ParticipantList(
                        participants = content.participants,
                        onEvaluate = { p, e -> onAction(TrainerAppointmentDetailAction.EvaluateParticipant(p, e)) }
                    )
                }
            }
        }
    }

    @Composable
    private fun ParticipantList(
        participants: List<LessonParticipant>,
        onEvaluate: (LessonParticipant, String) -> Unit
    ) {
        Spacer(Modifier.height(16.dp))
        BodyMediumSemiboldText("Katılım")
        Spacer(Modifier.height(12.dp))
        LazyColumn {
            items(participants) { participant ->
                LessonParticipantListItem(participant, onEvaluate)
            }
        }
    }
}


@Composable
private fun LessonParticipantListItem(
    participant: LessonParticipant,
    onEvaluate: (LessonParticipant, String) -> Unit
) {

    var showDialog by remember { mutableStateOf(false) }
    var inputText by remember { mutableStateOf("") }

    var isEnabled by remember { mutableStateOf(false) }

    Row(
        Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SkyFitCheckBox(isEnabled, onCheckedChange = { isEnabled = !isEnabled })

        CircleNetworkImage(url = participant.profileImageUrl, size = 60.dp)

        Column(
            modifier = Modifier.weight(1f)
        ) {
            BodyMediumSemiboldText(participant.username)
            Spacer(Modifier.height(4.dp))
            BodyMediumRegularText(
                text = "${participant.firstName} ${participant.lastName}",
                color = SkyFitColor.text.secondary
            )
        }

        if (participant.evaluated) {
            RectangleChip(text = "Değerlendirildi")
        } else {
            SecondaryMediumButton(
                text = "Değerlendir",
                enabled = isEnabled,
                onClick = { showDialog = true }
            )
        }
    }

    if (showDialog) {
        EvaluationCustomDialog(
            value = inputText,
            onValueChange = { inputText = it },
            onDismiss = { showDialog = false },
            onSave = {
                onEvaluate(participant, inputText)
                showDialog = false
            }
        )
    }
}

@Composable
fun EvaluationCustomDialog(
    value: String,
    onValueChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = SkyFitColor.background.surfaceSecondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    Modifier.fillMaxWidth()
                        .background(SkyFitColor.background.surfaceTertiary)
                        .padding(16.dp)
                ) {
                    BodyLargeMediumText("Değerlendir")
                }

                Spacer(modifier = Modifier.height(12.dp))

                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(120.dp)
                        .background(SkyFitColor.background.surfaceTertiary, shape = RoundedCornerShape(8.dp))
                        .padding(12.dp),
                    singleLine = false,
                    maxLines = 5,
                    textStyle = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.default),
                    cursorBrush = SolidColor(SkyFitColor.text.default),
                    decorationBox = { innerTextField ->
                        if (value.isEmpty()) {
                            BodyMediumRegularText(
                                "Örn.: Daha fazla esneme çalışmalı…",
                                color = SkyFitColor.text.secondary
                            )
                        }
                        innerTextField()
                    }
                )

                Row(
                    modifier = Modifier.padding(16.dp).align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    SecondaryDestructiveMicroButton("İptal", onClick = onDismiss)
                    PrimaryMicroButton("Kaydet", onClick = onSave)
                }
            }
        }
    }
}


