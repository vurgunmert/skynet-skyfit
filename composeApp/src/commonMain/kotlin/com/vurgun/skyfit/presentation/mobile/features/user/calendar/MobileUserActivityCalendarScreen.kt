package com.vurgun.skyfit.presentation.mobile.features.user.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.components.calendar.SkyFitDailyActivityCanvas
import com.vurgun.skyfit.presentation.shared.components.calendar.SkyFitDailyActivityItem
import com.vurgun.skyfit.presentation.shared.features.calendar.SkyFitCalendarGridComponent
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.utils.now
import kotlinx.datetime.LocalDate
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserActivityCalendarScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Takvim", onBackClick = { navigator.popBackStack() })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserActivityGridCalendarComponent()
            MobileUserActivityHourlyCalendarComponent()
        }
    }
}

@Composable
private fun MobileUserActivityGridCalendarComponent() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    SkyFitCalendarGridComponent(
        initialSelectedDate = selectedDate,
        isSingleSelect = true,
        onDateSelected = { selectedDate = it }
    )
}

@Composable
private fun MobileUserActivityHourlyCalendarComponent() {
    var activities by remember {
        mutableStateOf(
            listOf(
                SkyFitDailyActivityItem(emoji = "🔥", name = "Yürüyüş", startHourMinutes = 900, startBlock = 2),
                SkyFitDailyActivityItem(emoji = "🔥", name = "Yürüyüş", startHourMinutes = 1200, startBlock = 4),
                SkyFitDailyActivityItem(emoji = "🔥", name = "Yürüyüş", startHourMinutes = 1800, startBlock = 5)
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
