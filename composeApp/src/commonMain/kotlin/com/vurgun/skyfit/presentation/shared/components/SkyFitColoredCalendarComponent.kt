package com.vurgun.skyfit.presentation.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import com.vurgun.skyfit.utils.lengthOfMonth
import com.vurgun.skyfit.utils.now
import com.vurgun.skyfit.utils.withDayOfMonth
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

// Parses "dd/MM/yyyy" formatted date strings into LocalDate
fun parseDate(dateStr: String): LocalDate {
    val parts = dateStr.split("/")
    return LocalDate(parts[2].toInt(), parts[1].toInt(), parts[0].toInt())
}

// Determines the 5x7 calendar grid range for a given month
fun getCalendarRange(startDate: String): Pair<LocalDate, LocalDate> {
    val firstDayOfMonth = parseDate(startDate)
    val firstMonday = firstDayOfMonth.minus(firstDayOfMonth.dayOfWeek.ordinal, DateTimeUnit.DAY) // Adjust to Monday start
    val lastVisibleDay = firstMonday.plus(34, DateTimeUnit.DAY) // Ensure 35-day range

    return Pair(firstMonday, lastVisibleDay)
}

fun generateDayList(
    startDate: String, // "dd/MM/yyyy" (first day of the month)
    completedDays: List<String>,
    uncompletedDays: List<String>,
    currentDate: String
): List<Pair<String, DayCellStyle>> {
    val (start, end) = getCalendarRange(startDate)
    val today = parseDate(currentDate)
    val firstDayOfMonth = parseDate(startDate)
    val lastDayOfMonth = firstDayOfMonth.plus(1, DateTimeUnit.MONTH).minus(1, DateTimeUnit.DAY)

    val completedSet = completedDays.map { parseDate(it) }.toSet()
    val uncompletedSet = uncompletedDays.map { parseDate(it) }.toSet()

    return (start..end).map { date ->
        val formattedDay = date.dayOfMonth.toString()

        val style = when {
            date == today -> DayCellStyle.Current
            date in completedSet -> DayCellStyle.Completed
            date in uncompletedSet -> DayCellStyle.Uncompleted
            date < firstDayOfMonth || date > lastDayOfMonth -> if (date < today) DayCellStyle.Past else DayCellStyle.Future
            else -> DayCellStyle.Default
        }
        formattedDay to style
    }.toList()
}

// Helper function for iterating over LocalDate range
operator fun LocalDate.rangeTo(other: LocalDate) = generateSequence(this) { it.plus(1, DateTimeUnit.DAY) }
    .takeWhile { it <= other }


@Composable
fun SkyFitColoredCalendarComponent() {
    val completedDays = listOf("07/02/2025", "08/02/2025", "09/02/2025", "10/02/2025", "14/02/2025")
    val uncompletedDays = listOf("11/02/2025", "12/02/2025", "13/02/2025")
    val currentDate = "14/02/2025"

    // Automatically adjusts to 5x7 format
    val dayList = generateDayList(
        startDate = "01/02/2025",
        completedDays = completedDays,
        uncompletedDays = uncompletedDays,
        currentDate = currentDate
    )

    BoxWithConstraints(
        modifier = Modifier.fillMaxWidth()
    ) {
        val totalWidth = maxWidth
        val calculatedSize = (totalWidth / 7).coerceIn(40.dp, 60.dp) // Min 40dp, Max 60dp

        Column {
            // Headers
            Row {
                listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmrt", "Paz").forEach { day ->
                    DayCell(day, DayCellStyle.Label, calculatedSize)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Render the calendar
            dayList.chunked(7).forEach { week ->
                Row {
                    week.forEach { (day, style) ->
                        DayCell(day, style, calculatedSize)
                    }
                }
            }
        }
    }
}


@Composable
private fun DayCell(value: String, style: DayCellStyle, size: Dp) {
    Box(
        modifier = Modifier
            .size(size) // Dynamic size between 40dp - 60dp
            .background(style.backgroundColor, shape = CircleShape)
            .then(
                if (style.borderColor != null) {
                    Modifier.border(2.dp, style.borderColor, CircleShape)
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value,
            style = SkyFitTypography.bodyXSmall,
            color = style.textColor
        )
    }
}

sealed class DayCellStyle(
    val textColor: Color,
    val backgroundColor: Color,
    val borderColor: Color? = null
) {
    data object Label : DayCellStyle(
        textColor = SkyFitColor.text.default,
        backgroundColor = Color.Transparent
    )

    data object Default : DayCellStyle(
        textColor = SkyFitColor.text.default,
        backgroundColor = SkyFitColor.background.surfaceSecondary,
    )

    data object Completed : DayCellStyle(
        textColor = SkyFitColor.text.inverse,
        backgroundColor = SkyFitColor.border.secondaryButton
    )

    data object Current : DayCellStyle(
        textColor = SkyFitColor.text.default,
        backgroundColor = SkyFitColor.background.surfaceSecondary,
        borderColor = SkyFitColor.border.secondaryButton
    )

    data object Uncompleted : DayCellStyle(
        textColor = SkyFitColor.text.default,
        backgroundColor = SkyFitColor.icon.critical
    )

    data object Future : DayCellStyle(
        textColor = SkyFitColor.text.secondary,
        backgroundColor = SkyFitColor.transparent
    )

    data object Past : DayCellStyle(
        textColor = SkyFitColor.text.secondary,
        backgroundColor = SkyFitColor.transparent
    )
}
