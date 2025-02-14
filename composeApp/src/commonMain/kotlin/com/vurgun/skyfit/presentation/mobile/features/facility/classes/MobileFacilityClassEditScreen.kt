package com.vurgun.skyfit.presentation.mobile.features.facility.classes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
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
import com.vurgun.skyfit.presentation.mobile.resources.MobileStyleGuide
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitCheckBoxComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitDropdownComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitRadioButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextInputComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitIcon
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import cz.kudladev.DatePicker
import kotlinx.datetime.LocalDate
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileFacilityClassEditScreen(navigator: Navigator) {

    val viewModel: MobileFacilityClassEditScreenViewModel = remember { MobileFacilityClassEditScreenViewModel() }
    val facilityClass = viewModel.facilityClassState.collectAsState().value // 🔹 Get latest value immediately

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
            MobileFacilityClassEditScreenActionComponent(enabled = facilityClass.isSaveButtonEnabled, onClick = {})
        }
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = MobileStyleGuide.screenWithMax)
                .padding(MobileStyleGuide.padding16)
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
            FacilityClassTrainerInputComponent(
                trainers = facilityClass.trainers,
                selectedTrainer = facilityClass.selectedTrainer,
                onTrainerSelected = viewModel::updateSelectedTrainer
            )

            FacilityClassTrainerNoteInputGroup(
                trainerNote = facilityClass.trainerNote,
                onNoteChanged = viewModel::updateTrainerNote
            )
            // endregion

            // region: Date & Time Selection
            FacilityClassDateInputGroup(
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
    selectedIcon: String,
    title: String,
    onIconSelected: (String) -> Unit,
    onTitleChanged: (String) -> Unit
) {
    var isIconPickerOpen by remember { mutableStateOf(false) }
    val icons = SkyFitIcon.iconMap.keys.toList()

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
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(percent = 50))
                        .clickable { isIconPickerOpen = !isIconPickerOpen }
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SkyFitIcon.getIconResourcePainter(selectedIcon)?.let {
                        Icon(
                            painter = it,
                            contentDescription = selectedIcon.replace("_", " "),
                            tint = SkyFitColor.icon.default,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Icon(
                        painter = painterResource(Res.drawable.logo_skyfit),
                        contentDescription = "Arrow",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Antreman Başlığı",
                    style = SkyFitTypography.bodyMediumSemibold,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(Modifier.height(8.dp))
                SkyFitTextInputComponent(
                    "Antreman Başlığı",
                    value = title,
                    onValueChange = onTitleChanged
                )
            }
        }

        if (isIconPickerOpen) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                icons.forEach { iconName ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (iconName == selectedIcon) SkyFitColor.icon.default else SkyFitColor.background.surfaceSecondary)
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
fun FacilityClassTrainerInputComponent(
    trainers: List<FacilityTrainerViewData>,
    selectedTrainer: FacilityTrainerViewData?,
    onTrainerSelected: (FacilityTrainerViewData) -> Unit
) {
    selectedTrainer ?: return
    var isOpen by remember { mutableStateOf(false) }

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
                .clickable { isOpen = true }
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
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown Arrow",
                    tint = SkyFitColor.icon.default,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        // Dropdown Menu
        DropdownMenu(
            expanded = isOpen,
            onDismissRequest = { isOpen = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary)
        ) {
            trainers.forEach { trainer ->
                DropdownMenuItem(
                    onClick = {
                        onTrainerSelected(trainer)
                        isOpen = false
                    }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        AsyncImage(
                            model = trainer.imageUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                        )

                        Text(
                            text = trainer.name,
                            style = SkyFitTypography.bodyMediumRegular,
                            modifier = Modifier.weight(1f)
                        )

                        if (trainer == selectedTrainer) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Selected",
                                tint = Color.Cyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

//endregion Trainer

//region Date Time Components
@Composable
fun FacilityClassDateInputGroup(
    selectedDate: LocalDate = LocalDate(2024, 12, 21),
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
                text = selected.formatToDisplay(),
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.secondary,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Pick Date",
                tint = SkyFitColor.icon.default,
                modifier = Modifier.size(16.dp)
            )
        }
    }

    // Show Date Picker Dialog
    if (isDatePickerOpen) {
        DatePickerDialog(
            onDateSelected = {
                selected = it
                onDateSelected(it)
                isDatePickerOpen = false
            },
            onDismiss = { isDatePickerOpen = false }
        )
    }
}

fun LocalDate.formatToDisplay(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0') // Ensures 01, 02, ...
    val month = this.monthNumber.toString().padStart(2, '0') // Ensures 01, 02, ...
    val year = this.year.toString()
    return "$day / $month / $year"
}

@Composable
fun DatePickerDialog(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    DatePicker(
        onSelectDate = { selectedDate ->
            onDateSelected(selectedDate)
        }
    )
}


@Composable
private fun FacilityClassTimeInputGroup(
    selectedStartTime: String,
    onStartTimeSelected: (String) -> Unit,
    selectedEndTime: String,
    onEndTimeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FacilityClassStartTimeInputGroup(
            selectedTime = selectedStartTime,
            onTimeSelected = onStartTimeSelected
        )

        FacilityClassEndTimeInputGroup(
            selectedTime = selectedEndTime,
            onTimeSelected = onEndTimeSelected
        )
    }
}

@Composable
private fun RowScope.FacilityClassStartTimeInputGroup(
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    val timeOptions = generateTimeSlots(startHour = 8, endHour = 22, intervalMinutes = 30)

    SkyFitDropdownComponent(
        title = "Başlangıç Saati",
        options = timeOptions,
        selectedOption = selectedTime,
        onOptionSelected = onTimeSelected,
        modifier = Modifier.weight(1f) // Ensures equal width in Row
    )
}

@Composable
private fun RowScope.FacilityClassEndTimeInputGroup(
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    val timeOptions = generateTimeSlots(startHour = 8, endHour = 22, intervalMinutes = 30)

    SkyFitDropdownComponent(
        title = "Bitiş Saati",
        options = timeOptions,
        selectedOption = selectedTime,
        onOptionSelected = onTimeSelected,
        modifier = Modifier.weight(1f) // Ensures equal width in Row
    )
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
            options = listOf("Hergün", "Haftada belirli günler", "Ayda bir kez", "Tekrar yok"),
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
    val daysOfWeek = listOf("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar")

    Column {
        Text("Gün Seçimi", style = SkyFitTypography.bodyMediumSemibold)
        Spacer(Modifier.height(8.dp))

        FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            daysOfWeek.forEach { day ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onDaySelected(day) }
                ) {
                    Checkbox(
                        checked = selectedDays.contains(day),
                        onCheckedChange = { onDaySelected(day) }
                    )
                    Text(day, style = SkyFitTypography.bodyMediumRegular)
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
    val options = listOf("Her ayın ilk pazartesi", "Her ayın 15. günü")

    Column {
        Text("Format Seçimi", style = SkyFitTypography.bodyMediumSemibold)
        Text("Lütfen dersin her ay tekrarını hangi formatta yapmak istediğinizi seçin.", color = SkyFitColor.text.secondary)
        Spacer(Modifier.height(8.dp))

        options.forEach { option ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onOptionSelected(option) }
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { onOptionSelected(option) }
                )
                Text(option, style = SkyFitTypography.bodyMediumRegular)
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
                text = "Hayir",
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
            text = "Kaydet",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.logo_skyfit),
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






