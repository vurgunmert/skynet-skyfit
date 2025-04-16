package com.vurgun.skyfit.feature.courses.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.vurgun.skyfit.data.core.domain.model.CalendarRecurrence
import com.vurgun.skyfit.data.core.domain.model.CalendarRecurrenceType
import com.vurgun.skyfit.designsystem.components.text.MultiLineInputText
import com.vurgun.skyfit.designsystem.components.text.SingleLineInputText
import com.vurgun.skyfit.designsystem.components.text.TitledMediumRegularText
import com.vurgun.skyfit.feature.calendar.components.component.calendar.dialog.SingleDatePickerDialog
import com.vurgun.skyfit.feature.courses.component.LessonSelectCancelDurationPopupMenu
import com.vurgun.skyfit.feature.courses.component.LessonSelectCapacityPopupMenu
import com.vurgun.skyfit.feature.courses.component.LessonSelectRecurrenceTypePopupMenu
import com.vurgun.skyfit.feature.courses.component.LessonSelectTrainerPopupMenu
import com.vurgun.skyfit.feature.courses.component.SelectableTrainerMenuItemModel
import com.vurgun.skyfit.ui.core.components.button.PrimaryLargeButton
import com.vurgun.skyfit.ui.core.components.button.RadioButton
import com.vurgun.skyfit.ui.core.components.dialog.DestructiveDialog
import com.vurgun.skyfit.ui.core.components.icon.ActionIcon
import com.vurgun.skyfit.ui.core.components.image.CircleNetworkImage
import com.vurgun.skyfit.ui.core.components.special.SkyFitCheckBoxComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.ui.core.components.text.BodyMediumRegularText
import com.vurgun.skyfit.ui.core.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.ui.core.styling.LocalPadding
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.apply_action
import skyfit.ui.core.generated.resources.day_friday_label
import skyfit.ui.core.generated.resources.day_monday_label
import skyfit.ui.core.generated.resources.day_saturday_label
import skyfit.ui.core.generated.resources.day_sunday_label
import skyfit.ui.core.generated.resources.day_thursday_label
import skyfit.ui.core.generated.resources.day_tuesday_label
import skyfit.ui.core.generated.resources.day_wednesday_label
import skyfit.ui.core.generated.resources.ic_calendar_dots
import skyfit.ui.core.generated.resources.ic_check
import skyfit.ui.core.generated.resources.ic_chevron_down
import skyfit.ui.core.generated.resources.icon_label
import skyfit.ui.core.generated.resources.lesson_capacity_label
import skyfit.ui.core.generated.resources.lesson_cost_label
import skyfit.ui.core.generated.resources.lesson_create_private_action
import skyfit.ui.core.generated.resources.lesson_date_hint
import skyfit.ui.core.generated.resources.lesson_edit_action
import skyfit.ui.core.generated.resources.lesson_edit_cancel_message
import skyfit.ui.core.generated.resources.lesson_end_date_label
import skyfit.ui.core.generated.resources.lesson_end_hour_hint
import skyfit.ui.core.generated.resources.lesson_end_hour_label
import skyfit.ui.core.generated.resources.lesson_exercise_title
import skyfit.ui.core.generated.resources.lesson_exercise_title_hint
import skyfit.ui.core.generated.resources.lesson_mandatory_appointment_booking_label
import skyfit.ui.core.generated.resources.lesson_repeat_label
import skyfit.ui.core.generated.resources.lesson_start_date_label
import skyfit.ui.core.generated.resources.lesson_start_hour_hint
import skyfit.ui.core.generated.resources.lesson_start_hour_label
import skyfit.ui.core.generated.resources.lesson_trainer_label
import skyfit.ui.core.generated.resources.no_action
import skyfit.ui.core.generated.resources.open_action
import skyfit.ui.core.generated.resources.recurrence_daily_label
import skyfit.ui.core.generated.resources.recurrence_last_cancel_duration_label
import skyfit.ui.core.generated.resources.recurrence_none_label
import skyfit.ui.core.generated.resources.recurrence_weekly_label
import skyfit.ui.core.generated.resources.save_action
import skyfit.ui.core.generated.resources.trainer_note_hint_add
import skyfit.ui.core.generated.resources.trainer_note_label
import skyfit.ui.core.generated.resources.yes_action

@Composable
fun MobileFacilityEditLessonScreen(
    goToBack: () -> Unit,
    goToLessonCreated: () -> Unit,
    viewModel: FacilityEditLessonViewModel
) {
    val facilityClass = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadClass("facilityId", "null")
    }

    val scrollState = rememberScrollState()

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.lesson_edit_action), onClickBack = {
                if (facilityClass.isSaveButtonEnabled) {
                    viewModel.updateShowCancelDialog(true)
                } else {
                    goToBack()
                }
            })
        }
    ) {

        Column(
            modifier = Modifier
                .padding(LocalPadding.current.medium)
                .fillMaxSize()
                .imePadding()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // region: Head Info (Icon + Title)
            EditLessonSubjectItem(
                selectedIcon = facilityClass.iconId,
                title = facilityClass.title,
                onIconSelected = viewModel::updateIcon,
                onTitleChanged = viewModel::updateTitle
            )
            // endregion

            // region: Trainer Selection + Trainer Note
            EditLessonTrainerRow(
                trainers = facilityClass.trainers,
                onSelectionChanged = viewModel::updateSelectedTrainer
            )

            LessonEditNoteRow(
                note = facilityClass.trainerNote,
                onChanged = viewModel::updateTrainerNote
            )
            // endregion

            // region: Date & Time Selection
            EditLessonDatesRow(
                startDate = facilityClass.startDate,
                endDate = facilityClass.endDate,
                onStartDateSelected = viewModel::updateStartDate,
                onEndDateSelected = viewModel::updateEndDate
            )

            LessonEditHoursRow(
                selectedStartTime = facilityClass.startTime,
                selectedEndTime = facilityClass.endTime,
                onStartTimeSelected = viewModel::updateStartTime,
                onEndTimeSelected = viewModel::updateEndTime
            )
            // endregion

            // region: Recurrence
            LessonEditRecurrenceRow(
                selectedRecurrence = facilityClass.recurrence,
                onSelectionChanged = viewModel::updateRecurrence
            )
            // endregion

            // region: Capacity
            LessonEditCapacityRow(
                selectedCapacity = facilityClass.capacity,
                onSelectionChanged = viewModel::updateCapacity
            )
            // endregion

            // region: Cancel Duration
            LessonEditCancelDurationRow(
                selectedHour = facilityClass.cancelDurationHour,
                onSelectionChanged = viewModel::updateCancelDurationHour
            )
            // endregion

            // region: Obligation
            LessonEditAppointmentObligationRow(
                isMandatory = facilityClass.isAppointmentMandatory,
                onSelectionChanged = viewModel::updateAppointmentMandatory
            )
            // endregion

            // region: Cost
            LessonEditCostRow(
                cost = facilityClass.cost,
                onChanged = viewModel::updateCost
            )
            // endregion

            LessonEditActionRow(
                isNewLesson = true, //TODO: new-edit?
                isEnabled = facilityClass.isSaveButtonEnabled,
                isLoading = false, //TODO: loading
                onClick = goToLessonCreated
            )

            Spacer(Modifier.height(24.dp))
        }

        if (facilityClass.showCancelDialog) {
            DestructiveDialog(
                showDialog = facilityClass.showCancelDialog,
                message = stringResource(Res.string.lesson_edit_cancel_message),
                onClickConfirm = goToBack,
                onClickDismiss = { viewModel.updateShowCancelDialog(false) }
            )
        }
    }
}

//region Subject
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EditLessonSubjectItem(
    selectedIcon: Int?,
    title: String?,
    onIconSelected: (Int) -> Unit,
    onTitleChanged: (String) -> Unit
) {
    var isIconPickerOpen by remember { mutableStateOf(false) }
    val icons = SkyFitAsset.SkyFitIcon.intIdMap.keys.toList()

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column {
                Text(
                    text = stringResource(Res.string.icon_label),
                    style = SkyFitTypography.bodyMediumSemibold,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier = Modifier
                        .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(percent = 50))
                        .clickable { isIconPickerOpen = !isIconPickerOpen }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = SkyFitAsset.getPainter(selectedIcon),
                        contentDescription = "Icon",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(Res.drawable.ic_chevron_down),
                        contentDescription = stringResource(Res.string.open_action),
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            SingleLineInputText(
                title = stringResource(Res.string.lesson_exercise_title),
                hint = stringResource(Res.string.lesson_exercise_title_hint),
                value = title,
                onValueChange = onTitleChanged
            )
        }

        if (isIconPickerOpen) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                icons.forEach { iconId ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                            .then(
                                if (iconId == selectedIcon) {
                                    Modifier.border(
                                        width = 1.dp,
                                        color = SkyFitColor.border.secondaryButton,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                } else Modifier
                            )
                            .clickable {
                                onIconSelected(iconId)
                                isIconPickerOpen = false
                            }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = SkyFitAsset.getPainter(iconId),
                            contentDescription = "Icon",
                            tint = if (iconId == selectedIcon) Color.White else SkyFitColor.icon.default
                        )
                    }
                }
            }
        }
    }
}
//endregion Subject

//region Trainer
@Composable
private fun LessonEditNoteRow(
    note: String?,
    onChanged: (String) -> Unit
) {
    MultiLineInputText(
        title = stringResource(Res.string.trainer_note_label),
        hint = stringResource(Res.string.trainer_note_hint_add),
        value = note,
        onValueChange = onChanged
    )
}

@Composable
private fun EditLessonTrainerRow(
    trainers: List<SelectableTrainerMenuItemModel>,
    onSelectionChanged: (SelectableTrainerMenuItemModel) -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    val selectedTrainer = trainers.firstOrNull { it.selected }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Title
        Text(
            text = stringResource(Res.string.lesson_trainer_label),
            style = SkyFitTypography.bodyMediumSemibold,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        // Dropdown Selection Box
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(SkyFitColor.background.surfaceSecondary)
                .clickable { isDialogOpen = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CircleNetworkImage(selectedTrainer?.imageUrl)
                BodyMediumRegularText(selectedTrainer?.name.orEmpty(), modifier = Modifier.weight(1f))
                ActionIcon(res = Res.drawable.ic_chevron_down) { isDialogOpen = true }
            }
        }

        if (isDialogOpen) {
            Spacer(Modifier.height(6.dp))
            LessonSelectTrainerPopupMenu(
                modifier = Modifier.fillMaxWidth(),
                isOpen = isDialogOpen,
                onDismiss = { isDialogOpen = false },
                trainers = trainers,
                onSelectionChanged = onSelectionChanged
            )
        }
    }
}

//endregion Trainer

//region Date Time Components
@Composable
private fun EditLessonDatesRow(
    modifier: Modifier = Modifier,
    startDate: LocalDate? = null,
    endDate: LocalDate? = null,
    onStartDateSelected: (LocalDate) -> Unit = {},
    onEndDateSelected: (LocalDate) -> Unit = {}
) {
    var isStartDatePickerOpen by remember { mutableStateOf(false) }
    var isEndDatePickerOpen by remember { mutableStateOf(false) }
    var mutableStartDate by remember { mutableStateOf(startDate) }
    var mutableEndDate by remember { mutableStateOf(endDate) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitledMediumRegularText(
            modifier = Modifier.weight(1f).clickable { isStartDatePickerOpen = true },
            title = stringResource(Res.string.lesson_start_date_label),
            hint = stringResource(Res.string.lesson_date_hint),
            value = mutableStartDate?.classDateFormatToDisplay(),
            rightIconRes = Res.drawable.ic_calendar_dots
        )

        TitledMediumRegularText(
            modifier = Modifier.weight(1f).clickable { isEndDatePickerOpen = true },
            title = stringResource(Res.string.lesson_end_date_label),
            hint = stringResource(Res.string.lesson_date_hint),
            value = mutableEndDate?.classDateFormatToDisplay(),
            rightIconRes = Res.drawable.ic_calendar_dots
        )
    }

    if (isStartDatePickerOpen) {
        SingleDatePickerDialog(
            isOpen = isStartDatePickerOpen,
            onConfirm = {
                mutableStartDate = it
                onStartDateSelected(it)
                isStartDatePickerOpen = false
            },
            onDismiss = { isStartDatePickerOpen = false }
        )
    }

    if (isEndDatePickerOpen) {
        SingleDatePickerDialog(
            isOpen = isEndDatePickerOpen,
            onConfirm = {
                mutableEndDate = it
                onEndDateSelected(it)
                isEndDatePickerOpen = false
            },
            onDismiss = { isEndDatePickerOpen = false }
        )
    }
}


@Composable
private fun LessonEditHoursRow(
    selectedStartTime: String,
    selectedEndTime: String,
    onStartTimeSelected: (String) -> Unit,
    onEndTimeSelected: (String) -> Unit
) {
    var isStartDialogOpen by remember { mutableStateOf(false) }
    var isEndDialogOpen by remember { mutableStateOf(false) }
    var startTimeOptions by remember { mutableStateOf(generateHourSlots(8, 22, 30)) }
    var endTimeOptions by remember { mutableStateOf(generateHourSlots(8, 22, 30)) }

    // Update end time options when start time changes
    LaunchedEffect(selectedStartTime) {
        val startIndex = generateHourSlots(8, 22, 30).indexOf(selectedStartTime)
        if (startIndex != -1) {
            endTimeOptions = generateHourSlots(8, 22, 30).drop(startIndex + 1) // Ensure no earlier time is selectable
        }
        if (!endTimeOptions.contains(selectedEndTime)) {
            onEndTimeSelected(endTimeOptions.first()) // Adjust end time if no longer valid
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TitledMediumRegularText(
            modifier = Modifier.weight(1f).clickable { isStartDialogOpen = true },
            title = stringResource(Res.string.lesson_start_hour_label),
            hint = stringResource(Res.string.lesson_start_hour_hint),
            value = selectedStartTime,
            rightIconRes = Res.drawable.ic_chevron_down
        )
        TitledMediumRegularText(
            modifier = Modifier.weight(1f).clickable { isEndDialogOpen = true },
            title = stringResource(Res.string.lesson_end_hour_label),
            hint = stringResource(Res.string.lesson_end_hour_hint),
            value = selectedEndTime,
            rightIconRes = Res.drawable.ic_chevron_down
        )
    }

    if (isStartDialogOpen) {
        TimePickerDialog(
            isOpen = isStartDialogOpen,
            initialTime = selectedStartTime,
            startTime = null,
            onDismiss = { isStartDialogOpen = false },
            onTimeSelected = onStartTimeSelected
        )
    }

    if (isEndDialogOpen) {
        TimePickerDialog(
            isOpen = isEndDialogOpen,
            initialTime = selectedEndTime,
            startTime = selectedStartTime,
            onDismiss = { isEndDialogOpen = false },
            onTimeSelected = onEndTimeSelected
        )
    }
}

/**
 * Generates a list of time slots with the given interval (e.g., 08:00, 08:30, 09:00...).
 */
private fun generateHourSlots(startHour: Int, endHour: Int, intervalMinutes: Int): List<String> {
    val times = mutableListOf<String>()
    for (hour in startHour until endHour) {
        times.add("${hour.toString().padStart(2, '0')}:00")
        if (intervalMinutes < 60) {
            times.add("${hour.toString().padStart(2, '0')}:${intervalMinutes.toString().padStart(2, '0')}")
        }
    }
    return times
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LessonEditRecurrenceRow(
    selectedRecurrence: CalendarRecurrence,
    onSelectionChanged: (CalendarRecurrence) -> Unit
) {
    val dayOfWeekLabels = mapOf(
        DayOfWeek.MONDAY to stringResource(Res.string.day_monday_label),
        DayOfWeek.TUESDAY to stringResource(Res.string.day_tuesday_label),
        DayOfWeek.WEDNESDAY to stringResource(Res.string.day_wednesday_label),
        DayOfWeek.THURSDAY to stringResource(Res.string.day_thursday_label),
        DayOfWeek.FRIDAY to stringResource(Res.string.day_friday_label),
        DayOfWeek.SATURDAY to stringResource(Res.string.day_saturday_label),
        DayOfWeek.SUNDAY to stringResource(Res.string.day_sunday_label)
    )

    var selectedDays by remember {
        mutableStateOf(
            if (selectedRecurrence is CalendarRecurrence.SomeDays) selectedRecurrence.days
            else listOf()
        )
    }

    var isRecurrencePopupOpened by remember { mutableStateOf(false) }
    var isWeekDaysOpened by remember {
        mutableStateOf(selectedRecurrence is CalendarRecurrence.SomeDays)
    }

    val recurrenceLabel = when (selectedRecurrence.type) {
        CalendarRecurrenceType.NEVER -> stringResource(Res.string.recurrence_none_label)
        CalendarRecurrenceType.DAILY -> stringResource(Res.string.recurrence_daily_label)
        CalendarRecurrenceType.SOMEDAYS -> stringResource(Res.string.recurrence_weekly_label)
    }

    fun onDaySelected(day: DayOfWeek) {
        val newList = selectedDays.toMutableList()
        if (newList.contains(day)) {
            newList.remove(day)
        } else {
            newList.add(day)
        }

        // Rule: Must have at least one selected
        if (newList.isNotEmpty()) {
            selectedDays = newList
            onSelectionChanged(CalendarRecurrence.SomeDays(newList))
        }
    }

    Column {
        TitledMediumRegularText(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isRecurrencePopupOpened = true },
            title = stringResource(Res.string.lesson_repeat_label),
            hint = stringResource(Res.string.recurrence_none_label),
            value = recurrenceLabel,
            rightIconRes = Res.drawable.ic_chevron_down
        )

        if (isWeekDaysOpened) {
            Spacer(Modifier.height(16.dp))
            FlowRow(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                DayOfWeek.entries.forEach { day ->
                    val isChecked = selectedDays.contains(day)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onDaySelected(day) }
                    ) {
                        RadioButton(
                            text = dayOfWeekLabels[day] ?: day.name,
                            selected = isChecked,
                            onClick = { onDaySelected(day) }
                        )
                    }
                }
            }
        }

        if (isRecurrencePopupOpened) {
            Spacer(Modifier.height(6.dp))
            LessonSelectRecurrenceTypePopupMenu(
                modifier = Modifier.fillMaxWidth(),
                isOpen = isRecurrencePopupOpened,
                onDismiss = { isRecurrencePopupOpened = false },
                selectedRecurrenceType = selectedRecurrence.type,
                onSelectionChanged = { newType ->
                    isRecurrencePopupOpened = false
                    isWeekDaysOpened = newType == CalendarRecurrenceType.SOMEDAYS

                    when (newType) {
                        CalendarRecurrenceType.NEVER -> onSelectionChanged(CalendarRecurrence.Never)
                        CalendarRecurrenceType.DAILY -> onSelectionChanged(CalendarRecurrence.Daily)
                        CalendarRecurrenceType.SOMEDAYS -> {
                            if (selectedDays.isEmpty()) {
                                // Start with default day to avoid empty invalid state
                                selectedDays = mutableListOf(DayOfWeek.MONDAY)
                            }
                            onSelectionChanged(CalendarRecurrence.SomeDays(selectedDays))
                        }
                    }
                }
            )
        }
    }
}
//endregion Date Time Components

//region Capacity
@Composable
private fun LessonEditCapacityRow(
    selectedCapacity: Int,
    onSelectionChanged: (Int) -> Unit
) {
    var isCapacityPopupOpened by remember { mutableStateOf(false) }

    Column {
        TitledMediumRegularText(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isCapacityPopupOpened = true },
            title = stringResource(Res.string.lesson_capacity_label),
            value = selectedCapacity.toString(),
            rightIconRes = Res.drawable.ic_chevron_down
        )

        if (isCapacityPopupOpened) {
            LessonSelectCapacityPopupMenu(
                modifier = Modifier.fillMaxWidth(),
                isOpen = isCapacityPopupOpened,
                onDismiss = { isCapacityPopupOpened = false },
                selectedCapacity = selectedCapacity,
                onSelectionChanged = onSelectionChanged
            )
        }
    }
}
//endregion Capacity

//region Cancel Duration
@Composable
private fun LessonEditCancelDurationRow(
    selectedHour: Int,
    onSelectionChanged: (Int) -> Unit
) {
    var isHourPopupOpened by remember { mutableStateOf(false) }

    Column {
        TitledMediumRegularText(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isHourPopupOpened = true },
            title = stringResource(Res.string.recurrence_last_cancel_duration_label),
            value = "$selectedHour saat kala",
            rightIconRes = Res.drawable.ic_chevron_down
        )

        if (isHourPopupOpened) {
            LessonSelectCancelDurationPopupMenu(
                modifier = Modifier.fillMaxWidth(),
                isOpen = isHourPopupOpened,
                onDismiss = { isHourPopupOpened = false },
                selectedDuration = selectedHour,
                onSelectionChanged = onSelectionChanged
            )
        }
    }
}
//endregion Cancel Duration

//region Appointment Obligation
@Composable
private fun LessonEditAppointmentObligationRow(
    isMandatory: Boolean = false,
    onSelectionChanged: (Boolean) -> Unit = {}
) {
    Column {
        BodyMediumSemiboldText(
            text = stringResource(Res.string.lesson_mandatory_appointment_booking_label),
            modifier = Modifier.padding(start = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            RadioButton(
                text = stringResource(Res.string.yes_action),
                selected = isMandatory,
                onClick = { onSelectionChanged(true) })

            RadioButton(
                text = stringResource(Res.string.no_action),
                selected = !isMandatory,
                onClick = { onSelectionChanged(false) })
        }
    }
}
//endregion Appointment Obligation

//region Cost
@Composable
private fun LessonEditCostRow(
    cost: Int?,
    onChanged: (Int) -> Unit
) {
    var isPaymentMandatory by remember { mutableStateOf((cost ?: 0) > 0) }
    var inputValue by remember { mutableStateOf(cost?.toString() ?: "") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
    ) {
        Text(
            text = stringResource(Res.string.lesson_cost_label),
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkyFitCheckBoxComponent(
                label = stringResource(Res.string.apply_action),
                checked = isPaymentMandatory,
                onCheckedChange = {
                    isPaymentMandatory = it
                    if (!it) inputValue = ""
                }
            )

            BasicTextField(
                value = inputValue,
                onValueChange = { newValue ->
                    val cleaned = newValue.replace(',', '.').filter { it.isDigit() || it == '.' }
                    inputValue = cleaned
                    cleaned.toIntOrNull()?.let { onChanged(it) }
                },
                enabled = isPaymentMandatory,
                textStyle = SkyFitTypography.bodyMediumRegular.copy(
                    color = if (isPaymentMandatory) SkyFitColor.text.default else SkyFitColor.text.secondary,
                    textAlign = TextAlign.Left
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                cursorBrush = SolidColor(SkyFitColor.icon.default),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (inputValue.isEmpty()) {
                            Text(
                                "0.00",
                                color = SkyFitColor.text.secondary,
                                style = SkyFitTypography.bodyMediumRegular
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            innerTextField()
                            Text("₺", color = SkyFitColor.text.secondary)
                        }
                    }
                }
            )
        }
    }
}
//endregion Cost

//region Actions
@Composable
private fun LessonEditActionRow(
    isNewLesson: Boolean,
    isEnabled: Boolean,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    if (isNewLesson) {
        PrimaryLargeButton(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            text = stringResource(Res.string.lesson_create_private_action),
            onClick = { if (isEnabled) onClick.invoke() },
            isLoading = isLoading,
            isEnabled = isEnabled && !isLoading
        )
    } else {
        PrimaryLargeButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.save_action),
            onClick = { if (isEnabled) onClick.invoke() },
            isLoading = isLoading,
            isEnabled = isEnabled && !isLoading
        )
    }
}
//endregion Actions

@Composable
private fun TimePickerDialog(
    isOpen: Boolean,
    initialTime: String,
    startTime: String?,
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
) {
    var selectedTime by remember { mutableStateOf(initialTime) }
    var selectedInterval by remember { mutableStateOf(30) }  // ✅ Default interval: 30 minutes
    var filteredTimeOptions by remember { mutableStateOf(emptyList<String>()) }

    // Ensure valid interval filtering
    LaunchedEffect(selectedInterval, startTime) {
        val startHour = startTime?.split(":")?.get(0)?.toIntOrNull() ?: 8
        val startMinute = startTime?.split(":")?.get(1)?.toIntOrNull() ?: 0

        // ✅ Generate time slots **AFTER** the selected start time
        val allSlots = generateHourSlots(startHour, 22, selectedInterval)
            .filter { it > startTime.orEmpty() } // ✅ Filter out times before the start time

        filteredTimeOptions = allSlots

        // Select the closest valid time
        if (!filteredTimeOptions.contains(selectedTime)) {
            selectedTime = filteredTimeOptions.firstOrNull() ?: initialTime
        }
    }

    if (isOpen) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Saat Seçin",
                        style = SkyFitTypography.bodyMediumRegular
                    )
                    Spacer(Modifier.height(12.dp))

                    // Interval Selection
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        listOf(15, 30, 60).forEach { interval ->
                            Text(
                                text = "$interval dk",
                                style = if (selectedInterval == interval)
                                    SkyFitTypography.bodyMediumSemibold.copy(color = SkyFitColor.specialty.buttonBgRest)
                                else SkyFitTypography.bodyMediumRegular,
                                modifier = Modifier
                                    .clickable {
                                        selectedInterval = interval
                                    }
                                    .padding(8.dp)
                            )
                        }
                    }

                    Spacer(Modifier.height(12.dp))

                    // Time Selection List
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().heightIn(max = 300.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredTimeOptions) { time ->
                            val isSelected = selectedTime == time
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedTime = time }
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = time,
                                    style = SkyFitTypography.bodyMediumSemibold
                                )
                                if (isSelected) {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_check),
                                        contentDescription = "Selected",
                                        tint = SkyFitColor.icon.success
                                    )
                                }
                            }
                        }
                    }

                    Spacer(Modifier.height(24.dp))

                    // Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text("İptal", style = SkyFitTypography.bodyMediumSemibold)
                        }
                        Spacer(Modifier.width(16.dp))
                        Button(
                            onClick = {
                                onTimeSelected(selectedTime)
                                onDismiss()
                            },
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = SkyFitColor.specialty.buttonBgRest
                            )
                        ) {
                            Text(
                                text = "Onayla",
                                style = SkyFitTypography.bodyMediumSemibold,
                                color = SkyFitColor.text.inverse
                            )
                        }
                    }
                }
            }
        }
    }
}


private fun LocalDate.classDateFormatToDisplay(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0') // Ensures 01, 02, ...
    val month = this.monthNumber.toString().padStart(2, '0') // Ensures 01, 02, ...
    val year = this.year.toString()
    return "$day / $month / $year"
}