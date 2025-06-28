package com.vurgun.skyfit.feature.schedule.screen.lessons

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.CalendarRecurrence
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.CalendarRecurrenceType
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.Lesson
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCategory
import com.vurgun.skyfit.core.ui.components.button.*
import com.vurgun.skyfit.core.ui.components.dialog.BasicDialog
import com.vurgun.skyfit.core.ui.components.dialog.DestructiveDialog
import com.vurgun.skyfit.core.ui.components.dialog.ErrorDialog
import com.vurgun.skyfit.core.ui.components.dialog.rememberBasicDialogState
import com.vurgun.skyfit.core.ui.components.icon.ActionIcon
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.image.SkyImageSize
import com.vurgun.skyfit.core.ui.components.picker.SelectStartEndDateRow
import com.vurgun.skyfit.core.ui.components.schedule.*
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.components.text.*
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

class FacilityLessonEditScreen(private val lesson: Lesson? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityLessonEditViewModel>()
        var showNoTrainerError by remember { mutableStateOf(false) }
        val basicDialogState = rememberBasicDialogState()

        CollectEffect(viewModel.effect) {
            when (val effect = it) {
                FacilityLessonEditEffect.NavigateToBack -> {
                    navigator.pop()
                }

                FacilityLessonEditEffect.ShowNoTrainerError -> {
                    showNoTrainerError = true
                }

                is FacilityLessonEditEffect.NavigateToCreateComplete -> {
                    navigator.replace(FacilityLessonCreatedScreen(isUpdate = false, effect.lesson))
                }

                is FacilityLessonEditEffect.NavigateToUpdateComplete -> {
                    navigator.replace(FacilityLessonCreatedScreen(isUpdate = true, effect.lesson))
                }

                is FacilityLessonEditEffect.ShowNoCategoryError -> {
                    basicDialogState.show(
                        title = "Ders Kategorileri",
                        message = "Ders ekleyebilmek icin ders kategorisi tanimlamalisiniz.",
                        confirmText = "Ekle",
                        dismissText = "Iptal",
                        onConfirm = {
                            navigator.push(FacilityLessonCategoryListingScreen(effect.facilityId))
                        }
                    )
                }

                is FacilityLessonEditEffect.NavigateToCategoryListing -> {
                    navigator.push(FacilityLessonCategoryListingScreen(effect.facilityId))
                }

                is FacilityLessonEditEffect.ShowError -> {
                    basicDialogState.show(
                        title = "Ders Olusturulamadi",
                        message = effect.message.toString(),
                        onConfirm = { }
                    )
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData(lesson)
        }

        MobileFacilityEditLessonScreen(
            viewModel = viewModel
        )

        if (showNoTrainerError) {
            ErrorDialog(
                message = "Ders eklemek için bir eğitmen bulunamadı. Lütfen önce bir eğitmen ekleyin.",
                onDismiss = { navigator.pop() }
            )
        }

        BasicDialog(basicDialogState)
    }

}

@Composable
private fun MobileFacilityEditLessonScreen(
    viewModel: FacilityLessonEditViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    val scrollState = rememberScrollState()

    SkyFitMobileScaffold(
        topBar = {

            when (LocalWindowSize.current) {
                WindowSize.COMPACT, WindowSize.MEDIUM -> {
                    CompactTopBar(stringResource(Res.string.lesson_edit_action), onClickBack = {
                        if (uiState.isReadyToSave) {
                            viewModel.updateShowCancelDialog(true)
                        } else {
                            viewModel.onAction(FacilityLessonEditAction.NavigateToBack)
                        }
                    })
                }

                WindowSize.EXPANDED -> {
                    ExpandedTopBar(stringResource(Res.string.lesson_edit_action), onClickBack = {
                        if (uiState.isReadyToSave) {
                            viewModel.updateShowCancelDialog(true)
                        } else {
                            viewModel.onAction(FacilityLessonEditAction.NavigateToBack)
                        }
                    })
                }
            }
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
                selectedIcon = uiState.iconId,
                title = uiState.title,
                onIconSelected = viewModel::updateIcon,
                onTitleChanged = viewModel::updateTitle,
                isEditing = uiState.isEditing
            )
            // endregion

            // region: Trainer Selection + Trainer Note
            EditLessonTrainerRow(
                trainers = uiState.trainers,
                selectedTrainer = uiState.trainer,
                onSelectionChanged = viewModel::updateSelectedTrainer
            )

            LessonEditNoteRow(
                note = uiState.trainerNote,
                onChanged = viewModel::updateTrainerNote
            )
            // endregion

            // region: Date & Time Selection
            SelectStartEndDateRow(
                startDate = uiState.startDate,
                endDate = uiState.endDate,
                onStartDateSelected = viewModel::updateStartDate,
                onEndDateSelected = viewModel::updateEndDate
            )

            LessonEditHoursRow(
                selectedStartTime = uiState.startTime,
                selectedEndTime = uiState.endTime,
                onStartTimeSelected = viewModel::updateStartTime,
                onEndTimeSelected = viewModel::updateEndTime
            )
            // endregion

            // region: Recurrence
            LessonEditRecurrenceRow(
                selectedRecurrence = uiState.recurrence,
                onSelectionChanged = viewModel::updateRecurrence,
                isEditing = uiState.isEditing
            )
            // endregion

            // region: Category
            LessonSelectCategoryRow(
                allCategories = uiState.categories,
                selectedCategories = uiState.selectedCategories,
                onSelectionChanged = viewModel::updateSelectedCategories,
                onAddNew = { viewModel.onAction(FacilityLessonEditAction.AddNewCategory) },
            )
            // endregion

            // region: Capacity
            LessonEditCapacityRow(
                selectedCapacity = uiState.capacity,
                onSelectionChanged = viewModel::updateCapacity,
                participants = uiState.participantMembers,
                onParticipantsChanged = viewModel::updateParticipants,
                isEditing = uiState.isEditing
            )
            // endregion

            // region: Cancel Duration
            LessonEditCancelDurationRow(
                selectedHour = uiState.cancelDurationHour,
                onSelectionChanged = viewModel::updateCancelDurationHour
            )
            // endregion

            // region: Obligation
            LessonEditAppointmentObligationRow(
                isMandatory = uiState.isAppointmentMandatory,
                onSelectionChanged = viewModel::updateAppointmentMandatory
            )
            // endregion

            // region: Cost
            EditCostRow(
                cost = uiState.cost,
                onChanged = viewModel::updateCost
            )
            // endregion

            LessonEditActionRow(
                isNewLesson = !uiState.isEditing,
                isEnabled = uiState.isReadyToSave,
                isLoading = false, //TODO: loading
                onClick = { viewModel.onAction(FacilityLessonEditAction.Save) }
            )

            Spacer(Modifier.height(24.dp))
        }

        if (uiState.showCancelDialog) {
            DestructiveDialog(
                showDialog = uiState.showCancelDialog,
                message = stringResource(Res.string.lesson_edit_cancel_message),
                onClickConfirm = { viewModel.onAction(FacilityLessonEditAction.NavigateToBack) },
                onClickDismiss = { viewModel.updateShowCancelDialog(false) }
            )
        }
    }
}

//region Subject
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EditLessonSubjectItem(
    isEditing: Boolean,
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
                onValueChange = onTitleChanged,
                enabled = !isEditing
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
    selectedTrainer: SelectableTrainerMenuItemModel?,
    onSelectionChanged: (SelectableTrainerMenuItemModel) -> Unit
) {
    var isDialogOpen by remember { mutableStateOf(false) }

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
                SkyImage(
                    url = selectedTrainer?.imageUrl,
                    size = SkyImageSize.Size32,
                    shape = SkyImageShape.Circle,
                    error = Res.drawable.ic_high_intensity_training
                )
                SkyText(
                    text = selectedTrainer?.name.toString(),
                    styleType = TextStyleType.BodyMediumRegular,
                    modifier = Modifier.weight(1f)
                )
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
fun LessonEditHoursRow(
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
    onSelectionChanged: (CalendarRecurrence) -> Unit,
    isEditing: Boolean = false
) {
    if (isEditing) return

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


//region Lesson Category
@Composable
private fun LessonSelectCategoryRow(
    allCategories: List<LessonCategory>,
    selectedCategories: List<LessonCategory>,
    onSelectionChanged: (List<LessonCategory>) -> Unit,
    onAddNew: () -> Unit
) {
    var isCategoryPopupOpened by remember { mutableStateOf(false) }

    Column {
        TitledMediumRegularText(
            modifier = Modifier.fillMaxWidth()
                .clickable { isCategoryPopupOpened = true },
            title = stringResource(Res.string.lesson_categories_label),
            value = selectedCategories.joinToString(", ") { it.name },
            rightIconRes = Res.drawable.ic_chevron_down
        )

        if (isCategoryPopupOpened) {
            LessonSelectCategoryPopupMenu(
                isOpen = isCategoryPopupOpened,
                onDismiss = { isCategoryPopupOpened = false },
                allCategories = allCategories.toSet(),
                selectedCategories = selectedCategories.toSet(),
                onSelectionChanged = { onSelectionChanged(it.toList()) },
                onAddNew = onAddNew
            )
        }
    }
}
//endregion Lesson Category

//region Capacity
@Composable
private fun LessonEditCapacityRow(
    selectedCapacity: Int,
    onSelectionChanged: (Int) -> Unit,
    participants: List<ParticipatedMember>,
    onParticipantsChanged: (List<ParticipatedMember>) -> Unit,
    isEditing: Boolean
) {
    var isCapacityPopupOpened by remember { mutableStateOf(false) }
    var isEditMembersDialogOpened by remember { mutableStateOf(false) }

    Column {
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
            TitledMediumRegularText(
                modifier = Modifier
                    .weight(1f)
                    .clickable { isCapacityPopupOpened = true },
                title = stringResource(Res.string.lesson_capacity_label),
                value = selectedCapacity.toString(),
                rightIconRes = Res.drawable.ic_chevron_down
            )

            if (isEditing) {
                Spacer(Modifier.width(16.dp))
                PrimaryLargeButton("Üye Ekle/Sil", onClick = { isEditMembersDialogOpened = true })
            }
        }

        if (isCapacityPopupOpened) {
            LessonSelectCapacityPopupMenu(
                modifier = Modifier.fillMaxWidth(),
                isOpen = isCapacityPopupOpened,
                onDismiss = { isCapacityPopupOpened = false },
                selectedCapacity = selectedCapacity,
                onSelectionChanged = onSelectionChanged
            )
        }

        if (isEditMembersDialogOpened) {
            EditParticipantsDialog(
                participants,
                onUpdateParticipants = onParticipantsChanged,
                onClickDismiss = { isEditMembersDialogOpened = false }
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

@Composable
private fun EditParticipantsDialog(
    currentParticipatedMembers: List<ParticipatedMember>,
    onUpdateParticipants: (List<ParticipatedMember>) -> Unit,
    onClickDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var participatedMembers by remember { mutableStateOf(currentParticipatedMembers) }

    Dialog(
        onDismissRequest = onClickDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(16.dp))
                .background(SkyFitColor.background.surfaceSecondary)
        ) {
            Row(
                Modifier.fillMaxWidth().background(SkyFitColor.background.default).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BodyLargeMediumText("Üye Ekle/Sil")
                Spacer(Modifier.weight(1f))
                ActionIcon(Res.drawable.ic_close, onClick = onClickDismiss)
            }

            Spacer(Modifier.height(16.dp))

            SkyFitSearchTextInputComponent(
                hint = stringResource(Res.string.search_action),
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(participatedMembers) { participant ->
                    AddRemoveMemberItem(
                        imageUrl = participant.member.profileImageUrl,
                        username = participant.member.username,
                        fullName = "${participant.member.name} ${participant.member.surname}",
                        actionContent = {

                            if (participant.added) {
                                PrimaryMediumButton(
                                    text = stringResource(Res.string.delete_action),
                                    onClick = {
                                        participatedMembers = participatedMembers.map {
                                            if (it.member.userId == participant.member.userId) {
                                                it.copy(added = false)
                                            } else it
                                        }
                                    }
                                )
                            } else {
                                SecondaryMediumButton(
                                    text = stringResource(Res.string.add_action),
                                    onClick = {
                                        participatedMembers = participatedMembers.map {
                                            if (it.member.userId == participant.member.userId) {
                                                it.copy(added = true)
                                            } else it
                                        }
                                    }
                                )
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buttons Row
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                SecondaryMicroButton(
                    text = stringResource(Res.string.cancel_action),
                    onClick = onClickDismiss
                )
                Spacer(Modifier.width(16.dp))
                PrimaryMicroButton(
                    text = stringResource(Res.string.confirm_action),
                    onClick = {
                        onUpdateParticipants(participatedMembers)
                        onClickDismiss()
                    }
                )
            }
        }
    }
}