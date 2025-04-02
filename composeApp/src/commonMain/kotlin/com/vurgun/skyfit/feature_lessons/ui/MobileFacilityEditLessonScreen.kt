package com.vurgun.skyfit.feature_lessons.ui

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.AsyncImage
import com.vurgun.skyfit.core.domain.models.CalendarRecurrence
import com.vurgun.skyfit.core.domain.models.CalendarRecurrenceType
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.DatePickerDialog
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitCheckBoxComponent
import com.vurgun.skyfit.core.ui.components.SkyFitDropdownComponent
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitRadioButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitIcon
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.designsystem.components.button.RadioButton
import com.vurgun.skyfit.designsystem.components.icon.ActionIcon
import com.vurgun.skyfit.designsystem.components.image.CircleNetworkImage
import com.vurgun.skyfit.designsystem.components.popup.LessonSelectCapacityPopupMenu
import com.vurgun.skyfit.designsystem.components.popup.LessonSelectRecurrenceTypePopupMenu
import com.vurgun.skyfit.designsystem.components.popup.LessonSelectTrainerPopupMenu
import com.vurgun.skyfit.designsystem.components.popup.SelectableTrainerMenuItemModel
import com.vurgun.skyfit.designsystem.components.text.MultiLineInputText
import com.vurgun.skyfit.designsystem.components.text.TitledMediumRegularText
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterInputComponent
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.day_friday_label
import skyfit.composeapp.generated.resources.day_monday_label
import skyfit.composeapp.generated.resources.day_saturday_label
import skyfit.composeapp.generated.resources.day_sunday_label
import skyfit.composeapp.generated.resources.day_thursday_label
import skyfit.composeapp.generated.resources.day_tuesday_label
import skyfit.composeapp.generated.resources.day_wednesday_label
import skyfit.composeapp.generated.resources.ic_calendar_dots
import skyfit.composeapp.generated.resources.ic_check
import skyfit.composeapp.generated.resources.ic_chevron_down
import skyfit.composeapp.generated.resources.ic_exercises
import skyfit.composeapp.generated.resources.icon_label
import skyfit.composeapp.generated.resources.lesson_capacity_label
import skyfit.composeapp.generated.resources.lesson_date_hint
import skyfit.composeapp.generated.resources.lesson_edit_action
import skyfit.composeapp.generated.resources.lesson_end_date_label
import skyfit.composeapp.generated.resources.lesson_end_hour_hint
import skyfit.composeapp.generated.resources.lesson_end_hour_label
import skyfit.composeapp.generated.resources.lesson_exercise_title
import skyfit.composeapp.generated.resources.lesson_exercise_title_hint
import skyfit.composeapp.generated.resources.lesson_repeat_label
import skyfit.composeapp.generated.resources.lesson_start_date_label
import skyfit.composeapp.generated.resources.lesson_start_hour_hint
import skyfit.composeapp.generated.resources.lesson_start_hour_label
import skyfit.composeapp.generated.resources.lesson_trainer_label
import skyfit.composeapp.generated.resources.logo_skyfit
import skyfit.composeapp.generated.resources.open_action
import skyfit.composeapp.generated.resources.recurrence_daily_label
import skyfit.composeapp.generated.resources.recurrence_none_label
import skyfit.composeapp.generated.resources.recurrence_weekly_label
import skyfit.composeapp.generated.resources.trainer_note_hint_add
import skyfit.composeapp.generated.resources.trainer_note_label

@Composable
fun MobileFacilityEditLessonScreen(navigator: Navigator) {

    val viewModel = remember { MobileFacilityClassEditScreenViewModel() }
    val facilityClass = viewModel.facilityClassState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadClass("facilityId", "null")
    }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.lesson_edit_action), onClickBack = {
                if (facilityClass.isSaveButtonEnabled) {
                    viewModel.updateShowCancelDialog(true)
                } else {
                    navigator.popBackStack()
                }
            })
        },
        bottomBar = {
            MobileFacilityClassEditScreenActionComponent(
                enabled = facilityClass.isSaveButtonEnabled,
                onClick = {
                    navigator.jumpAndTakeover(
                        NavigationRoute.FacilityLessons,
                        NavigationRoute.FacilityLessonCreated
                    )
                })
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(SkyFitStyleGuide.Padding.large)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // region: Head Info (Icon + Title)
            EditLessonSubjectItem(
                selectedIcon = facilityClass.icon,
                title = facilityClass.title,
                onIconSelected = viewModel::updateIcon,
                onTitleChanged = viewModel::updateTitle
            )
            // endregion

            // region: Trainer Selection + Trainer Note
            EditLessonTrainerRow(
                trainers = facilityClass.trainerItems,
                onSelected = viewModel::updateSelectedTrainer
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

            // region: Repeat Options
//            FacilityClassCalendarRepeaterInputGroup(
//                selectedOption = facilityClass.repeatOption,
//                onOptionSelected = viewModel::updateRepeatOption,
//                selectedDaysOfWeek = facilityClass.selectedDaysOfWeek,
//                onDaySelected = viewModel::toggleDaySelection,
//                selectedMonthlyOption = facilityClass.monthlyOption,
//                onMonthlyOptionSelected = viewModel::updateMonthlyOption
//            )
            LessonEditRecurrenceRow(
                selectedRecurrence = facilityClass.recurrence,
                onChanged = viewModel::updateRecurrence
            )
            // endregion

            // region: Capacity & User Group


            LessonEditCapacityRow(
                selectedCapacity = facilityClass.capacity,
                onCapacitySelected = viewModel::updateCapacity
            )

            MobileFacilityClassEditSelectUserGroupComponent(
                selectedUserGroup = facilityClass.userGroup,
                onUserGroupSelected = viewModel::updateUserGroup
            )
            // endregion

            // region: Appointment & Payment
            FacilityClassMandatoryAppointmentInputGroup(
                isMandatory = facilityClass.isAppointmentMandatory,
                onOptionSelected = viewModel::updateAppointmentMandatory
            )

            MobileFacilityClassEditPaymentInputGroup(
                isPaymentMandatory = facilityClass.isPaymentMandatory,
                onPaymentMandatoryChanged = viewModel::updatePaymentMandatory,
                price = facilityClass.price,
                onPriceChanged = viewModel::updatePrice
            )
            // endregion

            Spacer(Modifier.height(112.dp))
        }

        MobileFacilityClassEditScreenCancelDialog(
            showDialog = facilityClass.showCancelDialog,
            onClickDismiss = { viewModel.updateShowCancelDialog(false) },
            onClickExit = { navigator.popBackStack() }
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EditLessonSubjectItem(
    selectedIcon: String?,
    title: String?,
    onIconSelected: (String) -> Unit,
    onTitleChanged: (String) -> Unit
) {
    var isIconPickerOpen by remember { mutableStateOf(false) }
    val icons = SkyFitIcon.idResMap.keys.toList()

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
                        painter = SkyFitIcon.getIconResourcePainter(selectedIcon, defaultRes = Res.drawable.ic_exercises),
                        contentDescription = selectedIcon,
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

            SkyFitSelectToEnterInputComponent(
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
                icons.forEach { iconName ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                            .then(
                                if (iconName == selectedIcon) {
                                    Modifier.border(
                                        width = 1.dp,
                                        color = SkyFitColor.border.secondaryButton,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                } else Modifier
                            )
                            .clickable {
                                onIconSelected(iconName)
                                isIconPickerOpen = false
                            }
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SkyFitIcon.getIconResourcePainter(iconName)?.let {
                            Icon(
                                painter = it,
                                contentDescription = iconName.replace("_", " "),
                                tint = if (iconName == selectedIcon) Color.White else SkyFitColor.icon.default
                            )
                        }
                    }
                }
            }
        }
    }
}

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

@Composable //TODO: Remove legacy
fun FacilityClassSelectTrainerInputComponent(
    trainers: List<FacilityTrainerViewData>,
    initialTrainer: FacilityTrainerViewData?,
    onTrainerSelected: (FacilityTrainerViewData) -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }
    val selectedTrainer = initialTrainer ?: trainers.first()

    Column(modifier = Modifier.fillMaxWidth()) {
        // Title
        Text(
            text = "Eğitmen",
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = selectedTrainer.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(24.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = selectedTrainer.name,
                    style = SkyFitTypography.bodyMediumRegular,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_chevron_down),
                    contentDescription = "Dropdown Arrow",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        if (isDialogOpen) {
            TrainerPickerDialog(
                trainers = trainers,
                initialTrainer = selectedTrainer,
                onDismiss = { isDialogOpen = false },
                onTrainerSelected = { trainer ->
                    onTrainerSelected(trainer)
                }
            )
        }
    }
}

@Composable
private fun EditLessonTrainerRow(
    trainers: List<SelectableTrainerMenuItemModel>,
    onSelected: (SelectableTrainerMenuItemModel) -> Unit
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
                onSelectionChanged = onSelected
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
        DatePickerDialog(
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
        DatePickerDialog(
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

@Composable //TODO: REMOVE
fun FacilityClassCalendarRepeaterInputGroup(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    selectedDaysOfWeek: List<String>,
    onDaySelected: (String) -> Unit,
    selectedMonthlyOption: String,
    onMonthlyOptionSelected: (String) -> Unit
) {
    Column {
        SkyFitDropdownComponent(
            title = "Ders Tekrarı",
            options = classRepeatPeriodOptions,
            selectedOption = selectedOption,
            onOptionSelected = { onOptionSelected(it) }
        )

        Spacer(Modifier.height(8.dp))

        when (selectedOption) {
            "Haftada belirli günler" -> WeeklySelectionGroup(
                selectedDays = selectedDaysOfWeek,
                onDaySelected = onDaySelected
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LessonEditRecurrenceRow(
    selectedRecurrence: CalendarRecurrence,
    onChanged: (CalendarRecurrence) -> Unit
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
            onChanged(CalendarRecurrence.SomeDays(newList))
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
                        CalendarRecurrenceType.NEVER -> onChanged(CalendarRecurrence.Never)
                        CalendarRecurrenceType.DAILY -> onChanged(CalendarRecurrence.Daily)
                        CalendarRecurrenceType.SOMEDAYS -> {
                            if (selectedDays.isEmpty()) {
                                // Start with default day to avoid empty invalid state
                                selectedDays = mutableListOf(DayOfWeek.MONDAY)
                            }
                            onChanged(CalendarRecurrence.SomeDays(selectedDays))
                        }
                    }
                }
            )
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WeeklySelectionGroup(
    selectedDays: List<String>,
    onDaySelected: (String) -> Unit
) {

    Column {
        Text("Gün Seçimi", style = SkyFitTypography.bodyMediumSemibold)
        Spacer(Modifier.height(8.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            classRepeatDaysOfWeek.forEach { day ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onDaySelected(day) }
                ) {
                    SkyFitCheckBoxComponent(
                        label = day,
                        checked = selectedDays.contains(day),
                        onCheckedChange = { onDaySelected(day) }
                    )
                }
            }
        }
    }
}

@Composable //TODO: REMOVE
private fun MonthlySelectionGroup(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        Text("Format Seçimi", style = SkyFitTypography.bodyMediumSemibold)
        Text("Lütfen dersin her ay tekrarını hangi formatta yapmak istediğinizi seçin.", color = SkyFitColor.text.secondary)
        Spacer(Modifier.height(8.dp))

//        classRepeatMonthlyOptions.forEach { option ->
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.clickable { onOptionSelected(option) }
//            ) {
//                SkyFitRadioButtonComponent(
//                    text = option,
//                    selected = selectedOption == option,
//                    onOptionSelected = { onOptionSelected(option) }
//                )
//            }
//        }
    }
}

//endregion Date Time Components

@Composable
private fun LessonEditCapacityRow(
    selectedCapacity: Int,
    onCapacitySelected: (Int) -> Unit
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
                onSelectionChanged = onCapacitySelected
            )
        }
    }

//    SkyFitDropdownComponent(
//        title = "Kontenjan",
//        options = (1..50).map { it.toString() }, // Generates "1" to "50"
//        selectedOption = selectedCapacity,
//        onOptionSelected = onCapacitySelected
//    )
}


@Composable
private fun FacilityClassMandatoryAppointmentInputGroup(
    isMandatory: Boolean = false,
    onOptionSelected: (Boolean) -> Unit = {}
) {
    Column {
        Text(
            "Zorunlu Randevu Alimi",
            style = SkyFitTypography.bodyMediumSemibold,
            modifier = Modifier.padding(start = 8.dp)
        )

        Row {
            SkyFitRadioButtonComponent(
                text = "Evet",
                selected = isMandatory,
                onOptionSelected = { onOptionSelected(true) }
            )
            SkyFitRadioButtonComponent(
                text = "Hayır",
                selected = !isMandatory,
                onOptionSelected = { onOptionSelected(false) }
            )
        }
    }
}

@Composable
private fun MobileFacilityClassEditPaymentInputGroup(
    isPaymentMandatory: Boolean,
    onPaymentMandatoryChanged: (Boolean) -> Unit,
    price: String,
    onPriceChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(start = 8.dp)
    ) {
        Text(
            text = "Ücret",
            style = SkyFitTypography.bodyMediumSemibold
        )
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Checkbox to enable/disable payment input
            SkyFitCheckBoxComponent(
                label = "Uygula",
                checked = isPaymentMandatory,
                onCheckedChange = { onPaymentMandatoryChanged(it) }
            )

            // Price input field
            BasicTextField(
                value = price,
                onValueChange = { onPriceChanged(it) },
                enabled = isPaymentMandatory,
                textStyle = SkyFitTypography.bodyMediumRegular
                    .copy(color = if (isPaymentMandatory) SkyFitColor.text.default else SkyFitColor.text.secondary),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Done),
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                decorationBox = { innerTextField ->
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        innerTextField()
                        Text("₺", color = SkyFitColor.text.secondary)
                    }
                }
            )
        }
    }
}

@Composable
private fun MobileFacilityClassEditSelectUserGroupComponent(
    selectedUserGroup: String,
    onUserGroupSelected: (String) -> Unit
) {
    val userGroups = listOf("Herkes", "Üyeler", "Takipçiler")

    Column {
        Text(
            "Kimler Katılabilir?",
            style = SkyFitTypography.bodyMediumSemibold,
            modifier = Modifier.padding(start = 8.dp)
        )

        Row {
            userGroups.forEach { group ->
                Row(
                    modifier = Modifier
                        .clickable { onUserGroupSelected(group) }
                        .padding(end = 16.dp), // Spacing between options
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SkyFitRadioButtonComponent(
                        text = group,
                        selected = selectedUserGroup == group,
                        onOptionSelected = { onUserGroupSelected(group) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MobileFacilityClassEditScreenActionComponent(enabled: Boolean, onClick: () -> Unit) {
    Box(Modifier.padding(32.dp).background(SkyFitColor.background.default)) {
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(),
            text = "Özel Ders Oluştur",
            onClick = onClick,
            variant = if (enabled) ButtonVariant.Primary else ButtonVariant.Secondary,
            size = ButtonSize.Large,
            state = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.ic_check),
            isEnabled = enabled
        )
    }
}

@Composable
private fun MobileFacilityClassEditScreenCancelDialog(
    showDialog: Boolean,
    onClickExit: () -> Unit,
    onClickDismiss: () -> Unit
) {
    if (showDialog) {
        Dialog(onDismissRequest = onClickDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(SkyFitColor.background.surfaceSecondary)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Close Icon
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(bottom = 8.dp),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.logo_skyfit),
                            contentDescription = "Close",
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier.clickable(onClick = onClickDismiss)
                        )
                    }

                    // Alert Message
                    Text(
                        text = "Geri dönerseniz değişikler kaydedilmeyecek",
                        style = SkyFitTypography.bodyLargeMedium,
                        textAlign = TextAlign.Center,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        SkyFitButtonComponent(
                            text = "Tamam",
                            modifier = Modifier.wrapContentWidth(),
                            onClick = onClickExit,
                            variant = ButtonVariant.Secondary,
                            size = ButtonSize.Medium,
                        )

                        SkyFitButtonComponent(
                            text = "Hayır, şimdi değil",
                            modifier = Modifier.weight(1f),
                            onClick = onClickDismiss,
                            variant = ButtonVariant.Primary,
                            size = ButtonSize.Medium,
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TrainerPickerDialog(
    trainers: List<FacilityTrainerViewData>,
    initialTrainer: FacilityTrainerViewData? = null,
    onDismiss: () -> Unit,
    onTrainerSelected: (FacilityTrainerViewData) -> Unit
) {
    var selectedTrainer by remember { mutableStateOf(initialTrainer) }

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
                    text = "Eğitmen Seçin",
                    style = SkyFitTypography.bodyMediumRegular
                )
                Spacer(Modifier.height(12.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    trainers.forEach { trainer ->
                        val isSelected = selectedTrainer == trainer
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedTrainer = trainer }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AsyncImage(
                                    model = trainer.imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                )
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    text = trainer.name,
                                    style = SkyFitTypography.bodyMediumSemibold
                                )
                            }

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
                            selectedTrainer?.let { onTrainerSelected(it) }
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


@Composable
fun TimePickerDialog(
    isOpen: Boolean,
    initialTime: String,
    startTime: String?,  // ✅ Pass the selected start time
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