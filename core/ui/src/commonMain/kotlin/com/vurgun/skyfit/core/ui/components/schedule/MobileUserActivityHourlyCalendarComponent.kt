package com.vurgun.skyfit.core.ui.components.schedule

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun MobileUserActivityHourlyCalendarComponent() {
    val activities = listOf(
        CalendarWorkoutTimeBlockItem(name = "Yürüyüş", startTime = "15:00"),
        CalendarWorkoutTimeBlockItem(name = "Öğün Hazırlığı", startTime = "18:00"),
        CalendarWorkoutTimeBlockItem(name = "Bacak Antrenmanı", startTime = "21:00")
    )

    CalendarWorkoutTimeBlockGrid(activities = activities)
}
