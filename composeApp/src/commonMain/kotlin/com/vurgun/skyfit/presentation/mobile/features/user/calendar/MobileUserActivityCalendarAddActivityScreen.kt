package com.vurgun.skyfit.presentation.mobile.features.user.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.calendar.SkyFitDailyActivityCanvas
import com.vurgun.skyfit.presentation.shared.components.calendar.SkyFitDailyActivityItem
import com.vurgun.skyfit.presentation.shared.components.calendar.SkyFitFourDigitClockComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

private enum class MobileUserActivityCalendarAddStep {
    INTRODUCE,
    TIME,
    CONFIRM
}

@Composable
fun MobileUserActivityCalendarAddActivityScreen(navigator: Navigator) {

    val title = null ?: "Yeni aktivite" //TODO: logic
    val step = MobileUserActivityCalendarAddStep.INTRODUCE //TODO: logic

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserActivityCalendarAddActivityScreenToolbarComponent()
        },
        bottomBar = {
            when(step) {
                MobileUserActivityCalendarAddStep.INTRODUCE -> {
                    MobileUserActivityCalendarAddActivityScreenAddTimeActionComponent(onClick = {

                    })
                }
                MobileUserActivityCalendarAddStep.TIME -> Unit
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
                MobileUserActivityCalendarAddStep.INTRODUCE -> {
                    MobileUserActivityCalendarAddActivityScreenInputComponent()
                    MobileUserActivityCalendarHourlyComponent()
                }

                MobileUserActivityCalendarAddStep.TIME -> {
                    MobileUserActivityCalendarAddActivityScreenTimerComponent()
                    MobileUserActivityCalendarAddActivityScreenSavedTimePeriodsComponent()
                    Spacer(Modifier.weight(1f))
                    MobileUserActivityCalendarAddActivityScreenContinueActionsComponent()
                    Spacer(Modifier.height(48.dp))
                }

                MobileUserActivityCalendarAddStep.CONFIRM -> {
                    MobileUserActivityCalendarAddActivityScreenTimeHolderComponent(1, 30)
                    MobileUserActivityCalendarAddActivityScreenTextHolderComponent()

                }
            }
        }
    }
}


@Composable
private fun MobileUserActivityCalendarAddActivityScreenToolbarComponent() {
    TodoBox("MobileUserActivityCalendarAddActivityScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileUserActivityCalendarAddActivityScreenInputComponent() {
    TodoBox("MobileUserActivityCalendarAddActivityScreenInputComponent", Modifier.size(430.dp, 88.dp))
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
    TodoBox("MobileUserActivityCalendarAddActivityScreenTimerComponent", Modifier.size(430.dp, 204.dp))
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
private fun MobileUserActivityCalendarAddActivityScreenContinueActionsComponent() {
    TodoBox("MobileUserActivityCalendarAddActivityScreenContinueActionComponent", Modifier.size(430.dp, 144.dp))
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
private fun MobileUserActivityCalendarAddActivityScreenTextHolderComponent() {
    TodoBox("MobileUserActivityCalendarAddActivityScreenTextHolderComponent", Modifier.size(430.dp, 88.dp))
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
fun ExampleScreen() {

}
