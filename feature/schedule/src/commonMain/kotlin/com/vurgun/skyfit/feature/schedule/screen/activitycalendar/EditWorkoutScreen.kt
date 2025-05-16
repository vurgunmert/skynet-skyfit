package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.picker.DurationWheelPicker
import com.vurgun.skyfit.core.ui.components.picker.TimeWheelPicker
import com.vurgun.skyfit.core.ui.components.picker.rememberPickerState
import com.vurgun.skyfit.core.ui.components.schedule.LessonSelectTimePopupMenu
import com.vurgun.skyfit.core.ui.components.schedule.SkyFitDailyActivityCanvas
import com.vurgun.skyfit.core.ui.components.schedule.SkyFitDailyActivityItem
import com.vurgun.skyfit.core.ui.components.schedule.SkyFitFourDigitClockComponent
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.special.SkyFitWheelPickerComponent
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.activity_title
import skyfit.core.ui.generated.resources.ic_chevron_down
import skyfit.core.ui.generated.resources.ic_clock
import skyfit.core.ui.generated.resources.logo_skyfit
import skyfit.core.ui.generated.resources.new_activity_label

private enum class EditWorkoutStep {
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
                EditWorkoutEffect.NavigateToBack -> {
                    navigator.pop()
                }
                is EditWorkoutEffect.NavigateToEditTime -> {
                    navigator.push(EditWorkoutTimeScreen(effect.name, effect.date, effect.workoutId))
                }
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

    var step by remember { mutableStateOf<EditWorkoutStep>(EditWorkoutStep.ADDING) }
    var activityName by remember { mutableStateOf(content.workoutName.orEmpty()) }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.new_activity_label),
                onClickBack = { onAction(EditWorkoutAction.OnClickBack) })
        },
        bottomBar = {
            when (step) {
                EditWorkoutStep.ADDING -> Unit
                EditWorkoutStep.TIMING -> Unit
                EditWorkoutStep.CONFIRM -> {
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
            when (step) {
                EditWorkoutStep.ADDING -> {
                    MobileUserActivityCalendarAddActivityScreenInputComponent(
                        activityName = activityName,
                        editable = true,
                        onNameChanged = { activityName = it }
                    )
                    MobileUserActivityCalendarAddActivityScreenAddTimeActionComponent(onClick = {
                        onAction(EditWorkoutAction.OnClickEditTime)
                    })
                    EditWorkoutTimeRow(content)
                    MobileUserActivityCalendarHourlyComponent()
                }

                EditWorkoutStep.TIMING -> {
                    MobileUserActivityCalendarAddActivityScreenTimerComponent({

                    })
                    MobileUserActivityCalendarAddActivityScreenSavedTimePeriodsComponent()
                    Spacer(Modifier.weight(1f))
                    MobileUserActivityCalendarAddActivityScreenContinueActionsComponent(
                        onClickContinue = {},
                        onClickCancel = {}
                    )
                }

                EditWorkoutStep.CONFIRM -> {
//                    MobileUserActivityCalendarAddActivityScreenTimeHolderComponent(1, 30)
//                    MobileUserActivityCalendarAddActivityScreenTextHolderComponent(activityName)
//                    MobileUserActivityCalendarHourlyComponent()

                }
            }
        }
    }
}

@Composable
private fun EditWorkoutTimeRow(
    content: EditWorkoutUiState.Content
) {
    var isHoursMenuVisible by remember { mutableStateOf(false) }
    var selectedTime by remember { mutableStateOf(content.startTime ?: "09:00") }

    Row(modifier = Modifier.fillMaxWidth()) {

        SkyText(
            text = content.startDate.orEmpty(),
            styleType = TextStyleType.BodyMediumSemibold
        )

        Spacer(Modifier.weight(1f))
        Row(
            modifier = Modifier.clickable { isHoursMenuVisible = true },
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkyText(
                text = selectedTime,
                styleType = TextStyleType.BodySmallSemibold
            )
            Spacer(Modifier.width(4.dp))
            SkyIcon(Res.drawable.ic_chevron_down,
                size = SkyIconSize.Small)
        }
    }

    LessonSelectTimePopupMenu(
        isOpen = isHoursMenuVisible,
        onDismiss = { isHoursMenuVisible = false },
        selectedTime = selectedTime,
        onSelectionChanged = { selectedTime = it },
        modifier = Modifier.width(124.dp)
    )
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
    Box(
        Modifier.fillMaxWidth().background(SkyFitColor.background.default).padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
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
fun MobileUserActivityCalendarHourlyComponent(modifier: Modifier = Modifier) {
    var activities by remember {
        mutableStateOf(
            listOf(
                SkyFitDailyActivityItem(
                    emoji = "🔥",
                    name = "Yürüyüş",
                    startHourMinutes = 900,
                    startBlock = 2
                )
            )
        )
    }
    var selectedBlock by remember { mutableStateOf(2) }

    Box(modifier.fillMaxSize()) {
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
}

@Composable
private fun MobileUserActivityCalendarAddActivityScreenTimerComponent(
    onAction: (EditWorkoutAction) -> Unit
) {

}


@Composable
private fun MobileUserActivityCalendarAddActivityScreenSavedTimePeriodsComponent() {
    var activities by remember {
        mutableStateOf(
            listOf(
                SkyFitDailyActivityItem(
                    emoji = "🔥",
                    name = "Yürüyüş",
                    startHourMinutes = 900,
                    startBlock = 2
                )
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
fun MobileUserActivityCalendarAddActivityScreenTextHolderComponent(activityName: String) {
    MobileUserActivityCalendarAddActivityScreenInputComponent(
        activityName = activityName,
        editable = false
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
