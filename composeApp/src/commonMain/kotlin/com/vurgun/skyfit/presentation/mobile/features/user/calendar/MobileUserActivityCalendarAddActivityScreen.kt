package com.vurgun.skyfit.presentation.mobile.features.user.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.SkyFitWheelPickerComponent
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitDailyActivityCanvas
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitDailyActivityItem
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitFourDigitClockComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

private enum class MobileUserActivityCalendarAddStep {
    ADDING,
    TIMING,
    CONFIRM
}

@Composable
fun MobileUserActivityCalendarAddActivityScreen(navigator: Navigator) {

    val step = MobileUserActivityCalendarAddStep.CONFIRM
    var activityName by remember { mutableStateOf("Yuruyus") }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Yeni Aktivite", onClickBack = { navigator.popBackStack() })
        },
        bottomBar = {
            when (step) {
                MobileUserActivityCalendarAddStep.ADDING -> Unit
                MobileUserActivityCalendarAddStep.TIMING -> Unit
                MobileUserActivityCalendarAddStep.CONFIRM -> {
                    MobileUserActivityCalendarAddActivityScreenConfirmActionComponent(onClick = {

                    })
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

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
                text = "Aktivite başlığı",
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
            Modifier.fillMaxWidth(), text = "Sure ekle",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.logo_skyfit)
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
            Modifier.fillMaxWidth(), text = "Devam Et",
            onClick = onClickContinue,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )
        Spacer(Modifier.height(14.dp))
        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "İptal",
            onClick = onClickCancel,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
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
private fun MobileUserActivityCalendarAddActivityScreenConfirmActionComponent(onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().background(SkyFitColor.background.default).padding(16.dp), contentAlignment = Alignment.Center) {
        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "Aktiviteyi ekle",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest,
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