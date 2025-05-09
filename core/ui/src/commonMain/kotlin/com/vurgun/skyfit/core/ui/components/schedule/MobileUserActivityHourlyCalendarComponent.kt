package com.vurgun.skyfit.core.ui.components.schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MobileUserActivityHourlyCalendarComponent() {
    var activities by remember {
        mutableStateOf(
            listOf(
                SkyFitDailyActivityItem(emoji = "🔥", name = "Yürüyüş", startHourMinutes = 900, startBlock = 2),
                SkyFitDailyActivityItem(emoji = "🔥", name = "Ogun Hazirligi", startHourMinutes = 1200, startBlock = 4),
                SkyFitDailyActivityItem(emoji = "🔥", name = "Bacak Antrenmani", startHourMinutes = 1800, startBlock = 5)
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