package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.utility.toTurkishLongDate
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.activate_action
import skyfit.core.ui.generated.resources.activity_added_label
import skyfit.core.ui.generated.resources.add_selected_activity_label
import skyfit.core.ui.generated.resources.ic_check
import skyfit.core.ui.generated.resources.ic_chevron_down

class CalendarWorkoutEditConfirmScreen(
    private val startDateTime: LocalDateTime,
    private val endDateTime: LocalDateTime,
    private val workoutName: String,
    private val workoutId: Int? = null,
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<CalendarWorkoutEditConfirmViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                CalendarWorkoutEditConfirmEffect.NavigateToBack -> {
                    navigator.pop()
                }
                CalendarWorkoutEditConfirmEffect.NavigateToConfirmed -> {
                    navigator.push(CalendarWorkoutEditConfirmedScreen(
                        workoutName = workoutName,
                        startDateTime = startDateTime,
                        endDateTime = endDateTime
                    ))
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData(startDateTime, endDateTime, workoutName, workoutId)
        }

        when (uiState) {
            CalendarWorkoutEditConfirmUiState.Loading -> FullScreenLoaderContent()

            is CalendarWorkoutEditConfirmUiState.Error -> {
                val message = (uiState as CalendarWorkoutEditConfirmUiState.Error).message
                ErrorScreen(
                    message = message,
                    onConfirm = { navigator.pop() }
                )
            }

            is CalendarWorkoutEditConfirmUiState.Content -> {
                val content = uiState as CalendarWorkoutEditConfirmUiState.Content
                CalendarWorkoutEditConfirmContent(content, viewModel::onAction)
            }
        }

    }

    @Composable
    private fun CalendarWorkoutEditConfirmContent(
        content: CalendarWorkoutEditConfirmUiState.Content,
        onAction: (CalendarWorkoutEditConfirmAction) -> Unit,
    ) {
        SkyFitMobileScaffold(
            topBar = {
                SkyFitScreenHeader(
                    title = content.workoutName,
                    onClickBack = { onAction(CalendarWorkoutEditConfirmAction.OnClickBack) }
                )
            }
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                MobileUserActivityCalendarAddActivityScreenTimeHolderComponent(
                    hour = content.durationHour,
                    minute = content.durationMinute
                )

                CalendarWorkoutEditConfirmTimeGroup(
                    content.startDateTime.date.toTurkishLongDate(),
                    content.startDateTime.time.toString()
                )

                MobileUserActivityCalendarHourlyComponent(
                    name = content.workoutName,
                    startTime = content.startDateTime.time.toString(),
                    modifier = Modifier.weight(1f)
                )

                SkyButton(
                    label = stringResource(Res.string.add_selected_activity_label),
                    size = SkyButtonSize.Large,
                    leftIcon = painterResource(Res.drawable.ic_check),
                    onClick = { onAction(CalendarWorkoutEditConfirmAction.OnClickAdd) },
                    modifier = Modifier.padding(16.dp).fillMaxWidth()
                )
            }
        }
    }
}


@Composable
private fun CalendarWorkoutEditConfirmTimeGroup(
    date: String,
    hour: String
) {

    Row(modifier = Modifier.fillMaxWidth()) {

        SkyText(
            text = date,
            styleType = TextStyleType.BodyMediumSemibold
        )

        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkyText(
                text = hour,
                styleType = TextStyleType.BodySmallSemibold
            )
        }
    }
}