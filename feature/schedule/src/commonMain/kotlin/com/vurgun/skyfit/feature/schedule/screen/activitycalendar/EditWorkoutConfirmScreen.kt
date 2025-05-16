package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.activate_action

class EditWorkoutConfirmScreen(
    val viewModel: EditWorkoutTimeViewModel
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val content = (viewModel.uiState.value as EditWorkoutTimeUiState.Content)

        val (hours, minutes) = remember(content.startTime, content.endTime) {
            calculateHourMinuteDiff(
                start = content.startTime,
                end = content.endTime
            )
        }

        SkyFitMobileScaffold(
            topBar = {
                SkyFitScreenHeader(title = content.name, onClickBack = { navigator.pop() })
            }
        ) {

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                MobileUserActivityCalendarAddActivityScreenTimeHolderComponent(hours, minutes)
                MobileUserActivityCalendarAddActivityScreenTextHolderComponent(content.name)
                MobileUserActivityCalendarHourlyComponent(
                    modifier = Modifier.weight(1f)
                )

                SkyButton(
                    label = stringResource(Res.string.activate_action),
                    size = SkyButtonSize.Large,
                    onClick = {
                        viewModel.submitWorkout()
                    },
                    modifier = Modifier.padding(16.dp).fillMaxWidth()
                )
            }
        }
    }

    fun calculateHourMinuteDiff(start: String, end: String): Pair<Int, Int> {
        val startParts = start.split(":").mapNotNull { it.toIntOrNull() }
        val endParts = end.split(":").mapNotNull { it.toIntOrNull() }

        if (startParts.size != 2 || endParts.size != 2) return 0 to 0

        val startTotalMinutes = startParts[0] * 60 + startParts[1]
        val endTotalMinutes = endParts[0] * 60 + endParts[1]

        val duration = endTotalMinutes - startTotalMinutes
        val hours = duration / 60
        val minutes = duration % 60

        return hours to minutes
    }

}