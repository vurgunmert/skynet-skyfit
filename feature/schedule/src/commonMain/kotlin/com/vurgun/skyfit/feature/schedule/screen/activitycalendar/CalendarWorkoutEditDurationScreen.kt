package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.picker.MVDurationWheelPicker
import com.vurgun.skyfit.core.ui.components.picker.rememberPickerState
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.cancel_action
import fiwe.core.ui.generated.resources.continue_action
import fiwe.core.ui.generated.resources.ic_clock

class CalendarWorkoutEditDurationScreen(
    val name: String,
    val startDateTime: LocalDateTime,
    val workoutEventId: Int?,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<CalendarWorkoutEditDurationViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                EditWorkoutTimeEffect.NavigateToBack -> {
                    navigator.pop()
                }

                is EditWorkoutTimeEffect.NavigateToEditWorkoutConfirm -> {
                    navigator.push(
                        CalendarWorkoutEditConfirmScreen(
                            startDateTime = effect.startDateTime,
                            endDateTime = effect.endDateTime,
                            workoutName = effect.workoutName,
                            workoutId = effect.workoutId
                        )
                    )
                }
            }
        }

        LaunchedEffect(viewModel) {
            viewModel.loadData(name, startDateTime, workoutEventId)
        }

        when (uiState) {
            EditWorkoutDurationUiState.Loading -> FullScreenLoaderContent()
            is EditWorkoutDurationUiState.Error -> {
                val message = (uiState as EditWorkoutDurationUiState.Error).message
                ErrorScreen(message = message, onConfirm = { navigator.pop() })
            }

            is EditWorkoutDurationUiState.Content -> {
                val content = uiState as EditWorkoutDurationUiState.Content

                WorkoutTimeContent(
                    content = content,
                    onAction = viewModel::onAction
                )
            }
        }
    }

}

@Composable
private fun WorkoutTimeContent(
    content: EditWorkoutDurationUiState.Content,
    onAction: (EditWorkoutTimeAction) -> Unit,
) {
    val hourState = rememberPickerState()
    val minuteState = rememberPickerState()

    LaunchedEffect(content.durationHours, content.durationMinutes) {
        hourState.selectedItem = content.durationHours.toString().padStart(2, '0')
        minuteState.selectedItem = content.durationMinutes.toString().padStart(2, '0')
    }

    LaunchedEffect(hourState, minuteState) {
        snapshotFlow { hourState.selectedItem to minuteState.selectedItem }
            .distinctUntilChanged()
            .collect { (hour, minute) ->
                onAction(EditWorkoutTimeAction.OnUpdateDuration(hour, minute))
            }
    }

    SkyFitMobileScaffold(
        topBar = {
            CompactTopBar(
                title = content.name,
                onClickBack = { onAction(EditWorkoutTimeAction.OnClickBack) })
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                Modifier.fillMaxWidth().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                MVDurationWheelPicker(
                    hourState = hourState,
                    minuteState = minuteState,
                    visibleItemCount = 5,
                    itemHeight = 40.dp
                )
            }

            Spacer(Modifier.height(16.dp))

            WorkoutCardsList(
                cards = listOf(
                    Pair("01:30:00", "Yürüyüş"),
                    Pair("00:20:00", "Alt karin sporu"),
                    Pair("00:15:00", "Kosma"),
                ),
                onSelect = { onAction(EditWorkoutTimeAction.OnClickStoredDuration(it)) }
            )

            Spacer(Modifier.weight(1f))

            SkyButton(
                label = stringResource(Res.string.continue_action),
                variant = SkyButtonVariant.Primary,
                size = SkyButtonSize.Large,
                onClick = { onAction(EditWorkoutTimeAction.OnClickContinue) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            SkyButton(
                label = stringResource(Res.string.cancel_action),
                variant = SkyButtonVariant.Secondary,
                size = SkyButtonSize.Large,
                onClick = { onAction(EditWorkoutTimeAction.OnClickBack) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}


@Composable
fun TimeDurationWorkoutCard(
    duration: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(SkyFitColor.background.surfaceSecondary)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_clock),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = SkyFitColor.icon.default
            )
            Text(
                text = duration,
                style = SkyFitTypography.bodyXSmall
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = label,
            style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkoutCardsList(
    cards: List<Pair<String, String>>,
    onSelect: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        cards.forEach { (duration, label) ->
            TimeDurationWorkoutCard(
                duration = duration,
                label = label,
                modifier = Modifier
                    .weight(1f, fill = true)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onSelect(duration) }
            )
        }
    }
}

