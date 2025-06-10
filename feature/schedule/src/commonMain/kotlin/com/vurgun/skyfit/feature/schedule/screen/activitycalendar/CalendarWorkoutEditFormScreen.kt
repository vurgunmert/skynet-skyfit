package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.data.workout.WorkoutTypeUiData
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.schedule.CalendarWorkoutTimeBlockGrid
import com.vurgun.skyfit.core.ui.components.schedule.CalendarWorkoutTimeBlockItem
import com.vurgun.skyfit.core.ui.components.schedule.LessonSelectTimePopupMenu
import com.vurgun.skyfit.core.ui.components.schedule.SkyFitFourDigitClockComponent
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.components.text.SingleLineInputText
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class CalendarWorkoutEditFormScreen(
    val date: LocalDate,
    val workoutType: WorkoutTypeUiData? = null
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<CalendarWorkoutEditViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                EditWorkoutEffect.NavigateToBack -> {
                    navigator.pop()
                }

                is EditWorkoutEffect.NavigateToEditTime -> {
                    navigator.push(
                        CalendarWorkoutEditDurationScreen(
                            name = effect.name,
                            startDateTime = effect.date,
                            workoutEventId = effect.workoutId
                        )
                    )
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData(date, workoutType)
        }

        when (uiState) {
            is CalendarWorkoutEditUiState.Content -> {
                val content = (uiState as CalendarWorkoutEditUiState.Content)
                EditWorkoutScreen_Compact(content, viewModel::onAction)
            }

            is CalendarWorkoutEditUiState.Error -> {
                val message = (uiState as CalendarWorkoutEditUiState.Error).message
                ErrorScreen(message = message, onConfirm = { navigator.pop() })
            }

            CalendarWorkoutEditUiState.Loading -> FullScreenLoaderContent()
        }
    }
}

@Composable
private fun EditWorkoutScreen_Compact(
    content: CalendarWorkoutEditUiState.Content,
    onAction: (EditWorkoutAction) -> Unit,
) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.new_activity_label),
                onClickBack = { onAction(EditWorkoutAction.OnClickBack) })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SingleLineInputText(
                title = stringResource(Res.string.activity_title),
                hint = stringResource(Res.string.add_activity_title_hint),
                value = content.workoutName,
                onValueChange = { onAction(EditWorkoutAction.OnUpdateName(it)) }
            )

            SkyButton(
                label = stringResource(Res.string.add_time_action),
                onClick = { onAction(EditWorkoutAction.OnClickEditTime) },
                modifier = Modifier.fillMaxWidth(),
                size = SkyButtonSize.Large,
                leftIcon = painterResource(Res.drawable.ic_clock)
            )

            EditWorkoutTimeRow(content, onAction)

            MobileUserActivityCalendarHourlyComponent(
                name = content.workoutName.toString(),
                startTime = content.startDateTime.time.toString(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun EditWorkoutTimeRow(
    content: CalendarWorkoutEditUiState.Content,
    onAction: (EditWorkoutAction) -> Unit
) {
    var isHoursMenuVisible by remember { mutableStateOf(false) }

    Row(modifier = Modifier.fillMaxWidth()) {

        SkyText(
            text = content.formattedDate,
            styleType = TextStyleType.BodyMediumSemibold
        )

        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier.clickable { isHoursMenuVisible = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkyText(
                text = content.startDateTime.time.toString(),
                styleType = TextStyleType.BodySmallSemibold
            )
            Spacer(Modifier.width(4.dp))
            SkyIcon(
                Res.drawable.ic_chevron_down,
                size = SkyIconSize.Small
            )
        }
    }

    LessonSelectTimePopupMenu(
        isOpen = isHoursMenuVisible,
        onDismiss = { isHoursMenuVisible = false },
        selectedTime = content.startDateTime.time.toString(),
        onSelectionChanged = { onAction(EditWorkoutAction.OnTimeChanged(it)) },
        modifier = Modifier.width(124.dp)
    )
}

@Composable
fun MobileUserActivityCalendarHourlyComponent(
    name: String,
    startTime: String,
    modifier: Modifier = Modifier
) {
    val activities = listOf(CalendarWorkoutTimeBlockItem(name, startTime))

    Box(modifier.fillMaxSize()) {
        CalendarWorkoutTimeBlockGrid(
            activities = activities,
            modifier = modifier
        )
    }
}


@Composable
private fun MobileUserActivityCalendarAddActivityScreenSavedTimePeriodsComponent(
    name: String, // 🚴‍♀️ Bisiklet
    startTime: String // 21:00
) {
    val activities = listOf(CalendarWorkoutTimeBlockItem(name, startTime))

    CalendarWorkoutTimeBlockGrid(activities = activities)
}


@Composable
private fun MobileUserActivityCalendarAddActivityScreenContinueActionsComponent(
    onClickContinue: () -> Unit,
    onClickCancel: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "Devam Et",
            onClick = onClickContinue,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
        Spacer(Modifier.height(14.dp))
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "İptal",
            onClick = onClickCancel,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
        Spacer(Modifier.height(44.dp))
    }
}

@Composable
fun MobileUserActivityCalendarAddActivityScreenTimeHolderComponent(hour: Int, minute: Int) {
    var hour by remember { mutableStateOf(hour) }
    var minute by remember { mutableStateOf(minute) }

    SkyFitFourDigitClockComponent(
        hour = hour,
        minute = minute,
        clickable = false,
        onTimeChange = { newHour, newMinute ->
            hour = newHour
            minute = newMinute
        }
    )
}

@Composable
private fun EditWorkoutSubmitAction(onClick: () -> Unit) {
    Box(
        Modifier.fillMaxWidth().background(SkyFitColor.background.default).padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "Aktiviteyi ekle",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.logo_skyfit)
        )
    }
}

@Composable
private fun MinutePicker(
    selected: Int,
    onSelected: (Int) -> Unit,
    min: Int = 1,
    max: Int = 59
) {
    val minutes = (min..max).toList()

    SkyFitWheelPickerComponent(
        items = minutes,
        selectedItem = selected,
        onItemSelected = onSelected,
        itemText = { "$it" },
        visibleItemCount = 5,
        modifier = Modifier.width(32.dp)
    )
}

@Composable
private fun HourPicker(
    selected: Int,
    onSelected: (Int) -> Unit,
    min: Int = 0,
    max: Int = 23
) {
    val minutes = (min..max).toList()

    SkyFitWheelPickerComponent(
        items = minutes,
        selectedItem = selected,
        onItemSelected = onSelected,
        itemText = { "$it" },
        visibleItemCount = 5,
        modifier = Modifier.width(32.dp)
    )
}
