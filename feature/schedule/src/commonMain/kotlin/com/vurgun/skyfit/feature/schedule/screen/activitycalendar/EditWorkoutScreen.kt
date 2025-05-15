package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutCategory
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutType
import com.vurgun.skyfit.core.ui.components.icon.ActionIcon
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.schedule.SkyFitDailyActivityCanvas
import com.vurgun.skyfit.core.ui.components.schedule.SkyFitDailyActivityItem
import com.vurgun.skyfit.core.ui.components.schedule.SkyFitFourDigitClockComponent
import com.vurgun.skyfit.core.ui.components.schedule.monthly.CalendarDateSelector
import com.vurgun.skyfit.core.ui.components.schedule.monthly.rememberEmptySelectCalendarSelectorController
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.schedule.screen.lessons.LessonEditHoursRow
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

private enum class MobileUserActivityCalendarAddStep {
    ADDING,
    TIMING,
    CONFIRM
}

class EditWorkoutScreen(
    val date: LocalDate,
    val workoutType: WorkoutType? = null,
    val workoutCategory: WorkoutCategory? = null
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<EditWorkoutViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                EditWorkoutEffect.NavigateToBack -> navigator::pop
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData(date, workoutType, workoutCategory)
        }

        when (uiState) {
            is EditWorkoutUiState.Content -> {
                val content = (uiState as EditWorkoutUiState.Content)
                EditWorkoutScreen_Compact(content, viewModel::onAction)
            }

            is EditWorkoutUiState.Error -> {
                val message = (uiState as EditWorkoutUiState.Error).message
                ErrorScreen(message = message, onConfirm = { navigator.pop() })
            }

            EditWorkoutUiState.Loading -> FullScreenLoaderContent()
        }
    }
}

@Composable
private fun EditWorkoutScreen_Compact(
    content: EditWorkoutUiState.Content,
    onAction: (EditWorkoutAction) -> Unit,
) {

    val step = MobileUserActivityCalendarAddStep.ADDING
    var activityName by remember { mutableStateOf(content.workoutName.orEmpty()) }
    val isDateSectionExpanded = remember { mutableStateOf(false) }
    val dateController = rememberEmptySelectCalendarSelectorController(initialStartDate = content.initialDate)

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.new_activity_label),
                onClickBack = { onAction(EditWorkoutAction.OnClickBack) })
        },
        bottomBar = {
            when (step) {
                MobileUserActivityCalendarAddStep.ADDING -> Unit
                MobileUserActivityCalendarAddStep.TIMING -> Unit
                MobileUserActivityCalendarAddStep.CONFIRM -> {
                    EditWorkoutSubmitAction(onClick = {

                    })
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

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
                    onAction(EditWorkoutAction.OnSelectDate(selectedDate))
                }
            }

            Spacer(Modifier.height(16.dp))
            LessonEditHoursRow(
                selectedStartTime = content.startTime.toString(),
                selectedEndTime = content.endTime.toString(),
                onStartTimeSelected = { onAction(EditWorkoutAction.OnUpdateStartTime(it)) },
                onEndTimeSelected = { onAction(EditWorkoutAction.OnUpdateEndTime(it)) }
            )

            Spacer(Modifier.height(16.dp))
            when (step) {
                MobileUserActivityCalendarAddStep.ADDING -> {
                    MobileUserActivityCalendarAddActivityScreenInputComponent(
                        activityName = activityName,
                        editable = true,
                        onNameChanged = { activityName = it }
                    )
                    MobileUserActivityCalendarAddActivityScreenAddTimeActionComponent(onClick = {

                    })
                    MobileUserActivityCalendarHourlyComponent()
                }

                MobileUserActivityCalendarAddStep.TIMING -> {
                    MobileUserActivityCalendarAddActivityScreenTimerComponent()
                    MobileUserActivityCalendarAddActivityScreenSavedTimePeriodsComponent()
                    Spacer(Modifier.weight(1f))
                    MobileUserActivityCalendarAddActivityScreenContinueActionsComponent(
                        onClickContinue = {},
                        onClickCancel = {}
                    )
                }

                MobileUserActivityCalendarAddStep.CONFIRM -> {
                    MobileUserActivityCalendarAddActivityScreenTimeHolderComponent(1, 30)
                    MobileUserActivityCalendarAddActivityScreenTextHolderComponent(activityName)
                    MobileUserActivityCalendarHourlyComponent()

                }
            }
        }
    }
}

@Composable
private fun MobileUserActivityCalendarAddActivityScreenInputComponent(
    activityName: String,
    editable: Boolean = true,
    onNameChanged: (String) -> Unit = {}
) {

    Box(Modifier.fillMaxWidth().padding(16.dp)) {

        Column(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.activity_title),
                style = SkyFitTypography.bodySmallMedium
            )
            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(SkyFitColor.background.surfaceSecondary, shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                BasicTextField(
                    modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(),
                    value = activityName,
                    enabled = editable,
                    onValueChange = onNameChanged,
                    textStyle = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary),
                    cursorBrush = SolidColor(SkyFitColor.specialty.buttonBgRest),
                )
            }
        }
    }
}

@Composable
private fun MobileUserActivityCalendarAddActivityScreenAddTimeActionComponent(onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().background(SkyFitColor.background.default).padding(16.dp), contentAlignment = Alignment.Center) {
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "Sure ekle",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.ic_clock)
        )
    }
}

@Composable
private fun MobileUserActivityCalendarHourlyComponent() {
    var activities by remember {
        mutableStateOf(
            listOf(
                SkyFitDailyActivityItem(emoji = "🔥", name = "Yürüyüş", startHourMinutes = 900, startBlock = 2)
            )
        )
    }
    var selectedBlock by remember { mutableStateOf(2) }

    SkyFitDailyActivityCanvas(
        activities = activities,
        selectedBlock = selectedBlock,
        onActivityUpdate = { updatedActivity ->
            activities = activities.map {
                if (it.name == updatedActivity.name) updatedActivity else it
            }
            selectedBlock = updatedActivity.startBlock
        }
    )
}

@Composable
private fun MobileUserActivityCalendarAddActivityScreenTimerComponent() {
    var selectedHour by remember { mutableStateOf(0) }
    var selectedMinute by remember { mutableStateOf(1) }

    Box(Modifier.fillMaxWidth().height(204.dp).padding(16.dp), contentAlignment = Alignment.Center) {

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HourPicker(
                selected = selectedHour,
                onSelected = { selectedHour = it }
            )
            Text("saat")
            MinutePicker(
                selected = selectedMinute,
                onSelected = { selectedMinute = it }
            )
            Text("dakika")
        }
    }

}

@Composable
private fun MobileUserActivityCalendarAddActivityScreenSavedTimePeriodsComponent() {
    var activities by remember {
        mutableStateOf(
            listOf(
                SkyFitDailyActivityItem(emoji = "🔥", name = "Yürüyüş", startHourMinutes = 900, startBlock = 2)
            )
        )
    }
    var selectedBlock by remember { mutableStateOf(2) }

    SkyFitDailyActivityCanvas(
        activities = activities,
        selectedBlock = selectedBlock,
        onActivityUpdate = { updatedActivity ->
            activities = activities.map {
                if (it.name == updatedActivity.name) updatedActivity else it
            }
            selectedBlock = updatedActivity.startBlock
        }
    )
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
private fun MobileUserActivityCalendarAddActivityScreenTimeHolderComponent(hour: Int, minute: Int) {
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
private fun MobileUserActivityCalendarAddActivityScreenTextHolderComponent(activityName: String) {
    MobileUserActivityCalendarAddActivityScreenInputComponent(
        activityName = activityName,
        editable = false
    )
}

@Composable
private fun EditWorkoutSubmitAction(onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().background(SkyFitColor.background.default).padding(16.dp), contentAlignment = Alignment.Center) {
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

@Composable
fun TimeDurationWorkoutCard(
    duration: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SkyFitColor.background.surfaceSecondary)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_clock),
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = SkyFitColor.icon.default
            )
            Text(
                text = duration,
                style = SkyFitTypography.bodyXSmall
            )
        }
        Spacer(Modifier.height(24.dp))
        Text(
            text = label,
            style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary)
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WorkoutCardsList(cards: List<Pair<String, String>>) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        cards.forEach { (duration, label) ->
            TimeDurationWorkoutCard(
                duration = duration,
                label = label,
                modifier = Modifier
                    .weight(1f, fill = true)
                    .aspectRatio(1f) // Optional: square look
            )
        }
    }
}
