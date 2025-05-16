package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.schedule.screen.lessons.LessonEditHoursRow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.cancel_action
import skyfit.core.ui.generated.resources.continue_action
import skyfit.core.ui.generated.resources.ic_clock

class EditWorkoutTimeScreen(
    val name: String,
    val date: LocalDate,
    val workoutEventId: Int?,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<EditWorkoutTimeViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                EditWorkoutTimeEffect.NavigateToBack -> {
                    navigator.pop()
                }
                EditWorkoutTimeEffect.NavigateToEditWorkoutConfirm -> {
                    navigator.push(EditWorkoutConfirmScreen(viewModel))
                }
            }
        }

        LaunchedEffect(viewModel) {
            viewModel.loadData(name, date, workoutEventId)
        }

        when(uiState) {
            EditWorkoutTimeUiState.Loading -> FullScreenLoaderContent()
            is EditWorkoutTimeUiState.Error -> {
               val message = (uiState as EditWorkoutTimeUiState.Error).message
               ErrorScreen(message = message, onConfirm = { navigator.pop() })
            }
            is EditWorkoutTimeUiState.Content -> {
                val content = uiState as EditWorkoutTimeUiState.Content

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
    content: EditWorkoutTimeUiState.Content,
    onAction: (EditWorkoutTimeAction) -> Unit,
) {
    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = content.name,
                onClickBack = { onAction(EditWorkoutTimeAction.OnClickBack) })
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Spacer(Modifier.height(16.dp))
            LessonEditHoursRow(
                selectedStartTime = content.startTime,
                selectedEndTime = content.endTime,
                onStartTimeSelected = { onAction(EditWorkoutTimeAction.OnUpdateStartTime(it)) },
                onEndTimeSelected = { onAction(EditWorkoutTimeAction.OnUpdateEndTime(it)) }
            )

            WorkoutCardsList(
                cards = listOf(
                    Pair("00:10:00", "Yürüyüş"),
                    Pair("00:20:00", "Alt karin sporu"),
                    Pair("00:15:00", "Kosma"),
                ),
                onSelect = { onAction(EditWorkoutTimeAction.OnClickDuration(it)) }
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
            .clip(RoundedCornerShape(16.dp))
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
                    .clickable { onSelect(duration) }
            )
        }
    }
}

