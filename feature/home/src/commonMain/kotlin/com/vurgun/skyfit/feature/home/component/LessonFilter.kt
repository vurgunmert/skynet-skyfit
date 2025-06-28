package com.vurgun.skyfit.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.button.PrimaryMediumButton
import com.vurgun.skyfit.core.ui.components.chip.SecondaryPillChip
import com.vurgun.skyfit.core.ui.components.icon.ActionIcon
import com.vurgun.skyfit.core.ui.components.icon.BackIcon
import com.vurgun.skyfit.core.ui.components.schedule.monthly.CalendarDateSelector
import com.vurgun.skyfit.core.ui.components.schedule.monthly.rememberEmptySelectCalendarSelectorController
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

data class LessonFilterData(
    val query: String? = null,
    val selectedTitles: Set<String> = emptySet(),
    val selectedHours: Set<String> = emptySet(),
    val selectedDates: Set<LocalDate> = emptySet(),
    val selectedStatuses: Set<String> = emptySet(),
    val selectedTrainers: Set<String> = emptySet(),
) {
    val hasAny = selectedTitles.isNotEmpty() || selectedHours.isNotEmpty() || selectedDates.isNotEmpty()
}

class LessonFilterScreen(
    private val lessons: List<LessonSessionItemViewData>,
    val onApply: (LessonFilterData) -> Unit = {},
) : Screen {

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow.parent ?: LocalNavigator.currentOrThrow

        val isTitleSectionExpanded = remember { mutableStateOf(true) }
        val isHourSectionExpanded = remember { mutableStateOf(true) }
        val isDateSectionExpanded = remember { mutableStateOf(false) }
        val isTrainerSectionExpanded = remember { mutableStateOf(false) }

        val availableTitles = remember(lessons) { lessons.map { it.title }.toSet() }
        val availableHours = remember(lessons) { lessons.mapNotNull { it.hours }.toSet() }
        val availableTrainers = remember(lessons) { lessons.mapNotNull { it.trainer }.toSet() }
        val availableStatuses = remember(lessons) { lessons.map { it.statusName }.toSet() }

        var appliedFilter by remember { mutableStateOf(LessonFilterData()) }

        val selectedTitleSet = remember(appliedFilter) {
            mutableStateOf(appliedFilter.selectedTitles)
        }
        val selectedHourSet = remember(appliedFilter) {
            mutableStateOf(appliedFilter.selectedHours)
        }
        val selectedDateSet = remember(appliedFilter) {
            mutableStateOf(appliedFilter.selectedDates)
        }
        val selectedTrainerSet = remember(appliedFilter) {
            mutableStateOf(appliedFilter.selectedTrainers)
        }
        val selectedStatusSet = remember(appliedFilter) {
            mutableStateOf(appliedFilter.selectedStatuses)
        }

        val dateController = rememberEmptySelectCalendarSelectorController(initialStartDate = selectedDateSet.value.firstOrNull())

        val isResetEnabled by remember {
            derivedStateOf {
                selectedTitleSet.value.isNotEmpty() ||
                        selectedHourSet.value.isNotEmpty() ||
                        selectedDateSet.value.isNotEmpty()||
                        selectedTrainerSet.value.isNotEmpty()
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
                        selectedTrainerSet.value = emptySet()
                        dateController.reset()
                        appliedFilter = LessonFilterData()
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
                //region Title Filter
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
                //endregion Title Filter

                Spacer(Modifier.height(24.dp))
                Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                Spacer(Modifier.height(24.dp))

                //region Hours Filter
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
                        availableHours.forEach { hour ->
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
                //endregion Hours Filter

                Spacer(Modifier.height(24.dp))
                Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                Spacer(Modifier.height(24.dp))

                //region Date Filter
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
                //endregion Date Filter

                Spacer(Modifier.height(24.dp))
                Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                Spacer(Modifier.height(24.dp))

                //region Trainer Filter
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isTrainerSectionExpanded.value = !isTrainerSectionExpanded.value },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyMediumSemiboldText(
                        text = stringResource(Res.string.event_name_label),
                        color = SkyFitColor.text.secondary,
                        modifier = Modifier.weight(1f)
                    )
                    val icon = if (isTrainerSectionExpanded.value) Res.drawable.ic_minus else Res.drawable.ic_plus
                    ActionIcon(
                        res = icon,
                        onClick = { isTrainerSectionExpanded.value = !isTrainerSectionExpanded.value }
                    )
                }

                if (isTrainerSectionExpanded.value) {
                    Spacer(Modifier.height(16.dp))
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        availableTrainers.forEach { trainer ->
                            val selected = selectedTrainerSet.value.contains(trainer)
                            SecondaryPillChip(
                                text = trainer,
                                selected = selected,
                                onClick = {
                                    selectedTrainerSet.value = if (selected) {
                                        selectedTrainerSet.value - trainer
                                    } else {
                                        selectedTrainerSet.value + trainer
                                    }
                                }
                            )
                        }
                    }
                }
                //endregion Trainer Filter

                Spacer(Modifier.height(24.dp))
                Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
                Spacer(Modifier.height(24.dp))

                //region Status Filter
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isTrainerSectionExpanded.value = !isTrainerSectionExpanded.value },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BodyMediumSemiboldText(
                        text = stringResource(Res.string.event_name_label),
                        color = SkyFitColor.text.secondary,
                        modifier = Modifier.weight(1f)
                    )
                    val icon = if (isTrainerSectionExpanded.value) Res.drawable.ic_minus else Res.drawable.ic_plus
                    ActionIcon(
                        res = icon,
                        onClick = { isTrainerSectionExpanded.value = !isTrainerSectionExpanded.value }
                    )
                }

                if (isTrainerSectionExpanded.value) {
                    Spacer(Modifier.height(16.dp))
                    FlowRow(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        availableStatuses.forEach { status ->
                            val selected = selectedStatusSet.value.contains(status)
                            SecondaryPillChip(
                                text = status,
                                selected = selected,
                                onClick = {
                                    selectedStatusSet.value = if (selected) {
                                        selectedStatusSet.value - status
                                    } else {
                                        selectedStatusSet.value + status
                                    }
                                }
                            )
                        }
                    }
                }
                //endregion Trainer Filter

                Spacer(Modifier.height(48.dp))

                PrimaryLargeButton(
                    text = stringResource(Res.string.filter_action),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        appliedFilter = LessonFilterData(
                            selectedTitles = selectedTitleSet.value,
                            selectedHours = selectedHourSet.value,
                            selectedDates = selectedDateSet.value
                        )
                        onApply(appliedFilter)
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
