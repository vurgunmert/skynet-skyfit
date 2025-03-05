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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.material.TextField
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
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.DatePickerDialog
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitCheckBoxComponent
import com.vurgun.skyfit.core.ui.components.SkyFitDropdownComponent
import com.vurgun.skyfit.core.ui.components.SkyFitRadioButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.feature_settings.ui.AccountSettingsSelectToSetInputComponent
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterInputComponent
import com.vurgun.skyfit.feature_settings.ui.SkyFitSelectToEnterMultilineInputComponent
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndTakeover
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitIcon
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.core.utils.now
import kotlinx.datetime.LocalDate
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_calendar_dots
import skyfit.composeapp.generated.resources.ic_check
import skyfit.composeapp.generated.resources.ic_chevron_down
import skyfit.composeapp.generated.resources.ic_exercises
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileFacilityClassEditScreen(navigator: Navigator) {

    val viewModel = remember { MobileFacilityClassEditScreenViewModel() }
    val facilityClass = viewModel.facilityClassState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadClass("facilityId", "null")
    }

    SkyFitScaffold(
        topBar = {
            SkyFitScreenHeader("Dersi Düzenle", onClickBack = {
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
                        NavigationRoute.FacilityManageLessons,
                        NavigationRoute.FacilityClassEditCompleted
                    )
                })
        }
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                .padding(SkyFitStyleGuide.Padding.large)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // region: Head Info (Icon + Title)
            FacilityClassHeadInfoInputGroup(
                selectedIcon = facilityClass.icon,
                title = facilityClass.title,
                onIconSelected = viewModel::updateIcon,
                onTitleChanged = viewModel::updateTitle
            )
            // endregion

            // region: Trainer Selection + Trainer Note
            FacilityClassSelectTrainerInputComponent(
                trainers = facilityClass.trainers,
                initialTrainer = facilityClass.selectedTrainer,
                onTrainerSelected = viewModel::updateSelectedTrainer
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = "Açıklama",
                hint = "Açıklama ekle",
                value = facilityClass.description,
                onValueChange = { viewModel.updateDescription(it) }
            )

            SkyFitSelectToEnterMultilineInputComponent(
                title = "Eğitmenin Notu",
                hint = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
                value = facilityClass.trainerNote,
                onValueChange = viewModel::updateTrainerNote
            )
            // endregion

            // region: Date & Time Selection
            FacilityClassStartDateInputGroup(
                selectedDate = facilityClass.selectedDate,
                onDateSelected = viewModel::updateSelectedDate
            )

            FacilityClassTimeInputGroup(
                selectedStartTime = facilityClass.startTime,
                onStartTimeSelected = viewModel::updateStartTime,
                selectedEndTime = facilityClass.endTime,
                onEndTimeSelected = viewModel::updateEndTime
            )
            // endregion

            // region: Repeat Options
            FacilityClassCalendarRepeaterInputGroup(
                selectedOption = facilityClass.repeatOption,
                onOptionSelected = viewModel::updateRepeatOption,
                selectedDaysOfWeek = facilityClass.selectedDaysOfWeek,
                onDaySelected = viewModel::toggleDaySelection,
                selectedMonthlyOption = facilityClass.monthlyOption,
                onMonthlyOptionSelected = viewModel::updateMonthlyOption
            )
            // endregion

            // region: Capacity & User Group
            MobileFacilityClassEditCapacityInputGroup(
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
private fun FacilityClassHeadInfoInputGroup(
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
                    text = "İkon",
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
                        contentDescription = "Arrow",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            SkyFitSelectToEnterInputComponent(
                title = "Antreman Başlığı",
                hint = "Shoulders and Abs",
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
private fun FacilityClassTrainerNoteInputGroup(
    trainerNote: String,
    onNoteChanged: (String) -> Unit
) {
    Column(
        Modifier.fillMaxWidth()
            .wrapContentHeight()
    ) {
        Text(
            text = "Eğitmenin Notu",
            style = SkyFitTypography.bodyMediumSemibold,
            modifier = Modifier.padding(start = 8.dp)
        )
        TextField(
            placeholder = {
                Text(
                    "Açıklama ekle",
                    style = SkyFitTypography.bodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            },
            value = trainerNote,
            onValueChange = onNoteChanged,
            modifier = Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
                .padding(vertical = 12.dp, horizontal = 16.dp)
        )
    }
}

@Composable
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

//endregion Trainer

//region Date Time Components
@Composable
fun FacilityClassStartDateInputGroup(
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit = {}
) {
    var isDatePickerOpen by remember { mutableStateOf(false) }
    var selected by remember { mutableStateOf(selectedDate) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Title
        Text(
            text = "Başlangıç Tarihi",
            style = SkyFitTypography.bodyMediumSemibold,
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp)
        )

        // Date Input Row (Triggers Date Picker)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(SkyFitColor.background.surfaceSecondary)
                .clickable { isDatePickerOpen = true }
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selected.classDateFormatToDisplay(),
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.secondary,
                modifier = Modifier.weight(1f)
            )

            Icon(
                painter = painterResource(Res.drawable.ic_calendar_dots),
                contentDescription = "Pick Date",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(16.dp)
            )
        }
    }

    // Show Date Picker Dialog
    if (isDatePickerOpen) {
        DatePickerDialog(
            isOpen = isDatePickerOpen,
            onDateSelected = {
                selected = it
                onDateSelected(it)
                isDatePickerOpen = false
            },
            onDismiss = { isDatePickerOpen = false }
        )
    }
}


@Composable
private fun FacilityClassTimeInputGroup(
    selectedStartTime: String,
    onStartTimeSelected: (String) -> Unit,
    selectedEndTime: String,
    onEndTimeSelected: (String) -> Unit
) {
    var isStartDialogOpen by remember { mutableStateOf(false) }
    var isEndDialogOpen by remember { mutableStateOf(false) }
    var startTimeOptions by remember { mutableStateOf(generateTimeSlots(8, 22, 30)) }
    var endTimeOptions by remember { mutableStateOf(generateTimeSlots(8, 22, 30)) }

    // Update end time options when start time changes
    LaunchedEffect(selectedStartTime) {
        val startIndex = generateTimeSlots(8, 22, 30).indexOf(selectedStartTime)
        if (startIndex != -1) {
            endTimeOptions = generateTimeSlots(8, 22, 30).drop(startIndex + 1) // Ensure no earlier time is selectable
        }
        if (!endTimeOptions.contains(selectedEndTime)) {
            onEndTimeSelected(endTimeOptions.first()) // Adjust end time if no longer valid
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AccountSettingsSelectToSetInputComponent(
            modifier = Modifier.weight(1f).clickable { isStartDialogOpen = true },
            title = "Başlangıç Saati",
            value = selectedStartTime,
            rightIconRes = Res.drawable.ic_chevron_down
        )
        AccountSettingsSelectToSetInputComponent(
            modifier = Modifier.weight(1f).clickable { isEndDialogOpen = true },
            title = "Bitiş Saati",
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
            startTime = selectedStartTime, // ✅ Pass selected start time
            onDismiss = { isEndDialogOpen = false },
            onTimeSelected = onEndTimeSelected
        )
    }
}

/**
 * Generates a list of time slots with the given interval (e.g., 08:00, 08:30, 09:00...).
 */
private fun generateTimeSlots(startHour: Int, endHour: Int, intervalMinutes: Int): List<String> {
    val times = mutableListOf<String>()
    for (hour in startHour until endHour) {
        times.add("${hour.toString().padStart(2, '0')}:00")
        if (intervalMinutes < 60) {
            times.add("${hour.toString().padStart(2, '0')}:${intervalMinutes.toString().padStart(2, '0')}")
        }
    }
    return times
}

@Composable
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

            "Ayda bir kez" -> MonthlySelectionGroup(
                selectedOption = selectedMonthlyOption,
                onOptionSelected = onMonthlyOptionSelected
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

@Composable
private fun MonthlySelectionGroup(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column {
        Text("Format Seçimi", style = SkyFitTypography.bodyMediumSemibold)
        Text("Lütfen dersin her ay tekrarını hangi formatta yapmak istediğinizi seçin.", color = SkyFitColor.text.secondary)
        Spacer(Modifier.height(8.dp))

        classRepeatMonthlyOptions.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onOptionSelected(option) }
            ) {
                SkyFitRadioButtonComponent(
                    text = option,
                    selected = selectedOption == option,
                    onOptionSelected = { onOptionSelected(option) }
                )
            }
        }
    }
}

//endregion Date Time Components

@Composable
private fun MobileFacilityClassEditCapacityInputGroup(
    selectedCapacity: String,
    onCapacitySelected: (String) -> Unit
) {
    SkyFitDropdownComponent(
        title = "Kontenjan",
        options = (1..50).map { it.toString() }, // Generates "1" to "50"
        selectedOption = selectedCapacity,
        onOptionSelected = onCapacitySelected
    )
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
        val allSlots = generateTimeSlots(startHour, 22, selectedInterval)
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