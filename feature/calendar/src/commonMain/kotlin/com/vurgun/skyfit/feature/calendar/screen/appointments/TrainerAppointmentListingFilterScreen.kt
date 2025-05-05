package com.vurgun.skyfit.feature.calendar.screen.appointments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.button.PrimaryMediumButton
import com.vurgun.skyfit.core.ui.components.chip.SecondaryPillChip
import com.vurgun.skyfit.core.ui.components.icon.ActionIcon
import com.vurgun.skyfit.core.ui.components.icon.BackIcon
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.feature.calendar.component.monthly.CalendarDateSelector
import com.vurgun.skyfit.feature.calendar.component.monthly.rememberEmptySelectCalendarSelectorController
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.date_label
import skyfit.core.ui.generated.resources.event_name_label
import skyfit.core.ui.generated.resources.filter_action
import skyfit.core.ui.generated.resources.ic_minus
import skyfit.core.ui.generated.resources.ic_plus
import skyfit.core.ui.generated.resources.reset_action
import skyfit.core.ui.generated.resources.settings_account_label
import skyfit.core.ui.generated.resources.time_label

class TrainerAppointmentListingFilterScreen(
    private val viewModel: TrainerAppointmentListingViewModel
) : Screen {

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val state by viewModel.uiState.collectAsState()
        val allLessons = (state as? TrainerAppointmentListingUiState.Content)?.lessons.orEmpty()

        val isTitleSectionExpanded = remember { mutableStateOf(true) }
        val isHourSectionExpanded = remember { mutableStateOf(true) }
        val isDateSectionExpanded = remember { mutableStateOf(false) }

        val availableTitles = remember(allLessons) { allLessons.map { it.title }.toSet() }
        val availableHours = remember(allLessons) { allLessons.map { it.startTime }.toSet() }

        val appliedFilter = (state as? TrainerAppointmentListingUiState.Content)?.currentFilter

        val selectedTitleSet = remember(appliedFilter) {
            mutableStateOf(appliedFilter?.selectedTitles ?: emptySet())
        }
        val selectedHourSet = remember(appliedFilter) {
            mutableStateOf(appliedFilter?.selectedHours ?: emptySet())
        }
        val selectedDateSet = remember(appliedFilter) {
            mutableStateOf(appliedFilter?.selectedDates ?: emptySet())
        }

        val dateController = rememberEmptySelectCalendarSelectorController(initialStartDate = selectedDateSet.value.firstOrNull())

        val isResetEnabled by remember {
            derivedStateOf {
                selectedTitleSet.value.isNotEmpty() ||
                        selectedHourSet.value.isNotEmpty() ||
                        selectedDateSet.value.isNotEmpty()
            }
        }

        SkyFitMobileScaffold(
            topBar = {
                TrailerAppointmentListingFilterHeader(
                    onClickBack = { navigator.pop() },
                    resetEnabled = isResetEnabled,
                    onClickReset = {
                        selectedTitleSet.value = emptySet()
                        selectedHourSet.value = emptySet()
                        selectedDateSet.value = emptySet()
                        dateController.reset()
                        viewModel.resetFilter()
                        navigator.pop()
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                // Title Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isTitleSectionExpanded.value = !isTitleSectionExpanded.value },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyMediumSemiboldText(
                        text = stringResource(Res.string.event_name_label),
                        color = SkyFitColor.text.secondary,
                        modifier = Modifier.weight(1f)
                    )
                    val icon = if (isTitleSectionExpanded.value) Res.drawable.ic_minus else Res.drawable.ic_plus
                    ActionIcon(
                        res = icon,
                        onClick = { isTitleSectionExpanded.value = !isTitleSectionExpanded.value }
                    )
                }

                if (isTitleSectionExpanded.value) {
                    Spacer(Modifier.height(16.dp))
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        availableTitles.forEach { title ->
                            val selected = selectedTitleSet.value.contains(title)
                            SecondaryPillChip(
                                text = title,
                                selected = selected,
                                onClick = {
                                    selectedTitleSet.value = if (selected) {
                                        selectedTitleSet.value - title
                                    } else {
                                        selectedTitleSet.value + title
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
                Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                Spacer(Modifier.height(24.dp))

                // Hour Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isHourSectionExpanded.value = !isHourSectionExpanded.value },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyMediumSemiboldText(
                        text = stringResource(Res.string.time_label),
                        color = SkyFitColor.text.secondary,
                        modifier = Modifier.weight(1f)
                    )
                    val icon = if (isHourSectionExpanded.value) Res.drawable.ic_minus else Res.drawable.ic_plus
                    ActionIcon(
                        res = icon,
                        onClick = { isHourSectionExpanded.value = !isHourSectionExpanded.value }
                    )
                }

                if (isHourSectionExpanded.value) {
                    Spacer(Modifier.height(16.dp))
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        availableHours.sorted().forEach { hour ->
                            val selected = selectedHourSet.value.contains(hour)
                            SecondaryPillChip(
                                text = hour.toString(),
                                selected = selected,
                                onClick = {
                                    selectedHourSet.value = if (selected) {
                                        selectedHourSet.value - hour
                                    } else {
                                        selectedHourSet.value + hour
                                    }
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))
                Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                Spacer(Modifier.height(24.dp))

                // Date Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isDateSectionExpanded.value = !isDateSectionExpanded.value },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyMediumSemiboldText(
                        text = stringResource(Res.string.date_label),
                        color = SkyFitColor.text.secondary,
                        modifier = Modifier.weight(1f)
                    )
                    val icon = if (isDateSectionExpanded.value) Res.drawable.ic_minus else Res.drawable.ic_plus
                    ActionIcon(
                        res = icon,
                        onClick = { isDateSectionExpanded.value = !isDateSectionExpanded.value }
                    )
                }

                if (isDateSectionExpanded.value) {
                    Spacer(Modifier.height(16.dp))
                    CalendarDateSelector(dateController) { selectedDate, _ ->
                        if (selectedDate == null) {
                            selectedDateSet.value = emptySet()
                        } else {
                            selectedDateSet.value = setOf(selectedDate)
                        }
                    }
                }

                Spacer(Modifier.height(48.dp))

                PrimaryLargeButton(
                    text = stringResource(Res.string.filter_action),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        val selectedFilter = TrainerAppointmentFilter(
                            selectedTitles = selectedTitleSet.value,
                            selectedHours = selectedHourSet.value,
                            selectedDates = selectedDateSet.value
                        )
                        viewModel.applyFilter(selectedFilter)
                        navigator.pop()
                    }
                )
            }
        }
    }

}

@Composable
private fun TrailerAppointmentListingFilterHeader(
    resetEnabled: Boolean,
    onClickBack: () -> Unit,
    onClickReset: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 8.dp)
    ) {
        BackIcon(
            onClick = onClickBack,
            modifier = Modifier.align(Alignment.CenterStart)
        )

        Text(
            text = stringResource(Res.string.settings_account_label),
            style = SkyFitTypography.bodyLargeSemibold,
            modifier = Modifier.align(Alignment.Center)
        )

        PrimaryMediumButton(
            text = stringResource(Res.string.reset_action),
            modifier = Modifier.align(Alignment.CenterEnd),
            enabled = resetEnabled,
            onClick = onClickReset
        )
    }
}
