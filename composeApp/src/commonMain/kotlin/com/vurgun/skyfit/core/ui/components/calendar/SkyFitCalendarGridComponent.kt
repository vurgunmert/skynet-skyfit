package com.vurgun.skyfit.core.ui.components.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.calendar.SkyFitCalendarGridComponent.CalendarGrid
import com.vurgun.skyfit.core.ui.components.calendar.SkyFitCalendarGridComponent.CalendarHeader
import com.vurgun.skyfit.core.ui.components.calendar.SkyFitCalendarGridComponent.CalendarMonthSelector
import com.vurgun.skyfit.core.ui.components.calendar.SkyFitCalendarGridComponent.DaysOfWeekRow
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.core.utils.generateDaysInMonth
import com.vurgun.skyfit.core.utils.getTurkishMonthName
import com.vurgun.skyfit.core.utils.isAfter
import com.vurgun.skyfit.core.utils.isBefore
import com.vurgun.skyfit.core.utils.nextMonth
import com.vurgun.skyfit.core.utils.now
import com.vurgun.skyfit.core.utils.previousMonth
import com.vurgun.skyfit.core.utils.updateRangeSelection
import kotlinx.datetime.LocalDate
import kotlinx.datetime.number
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_calendar_dots
import skyfit.composeapp.generated.resources.ic_chevron_left
import skyfit.composeapp.generated.resources.ic_chevron_right

@Composable
fun SkyFitCalendarGridComponent(
    initialSelectedDate: LocalDate = LocalDate.now(),
    enabledStartDate: LocalDate? = null,
    enabledEndDate: LocalDate? = null,
    isSingleSelect: Boolean = false,
    onDateSelected: (LocalDate) -> Unit = {},
    onRangeSelected: (Pair<LocalDate, LocalDate>?) -> Unit = {}
) {
    var currentMonth by remember { mutableStateOf(initialSelectedDate) }
    val daysInMonth = remember(currentMonth) { generateDaysInMonth(currentMonth) }

    var selectedDate by remember { mutableStateOf<LocalDate?>(initialSelectedDate) }
    var selectedRange by remember { mutableStateOf<Pair<LocalDate, LocalDate>?>(null) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        CalendarHeader(isSingleSelect)
        Spacer(modifier = Modifier.height(16.dp))
        CalendarMonthSelector(
            month = currentMonth,
            onPreviousMonth = { currentMonth = previousMonth(currentMonth) },
            onNextMonth = { currentMonth = nextMonth(currentMonth) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        DaysOfWeekRow()
        Spacer(modifier = Modifier.height(8.dp))
        CalendarGrid(
            days = daysInMonth,
            selectedMonth = currentMonth,
            enabledStartDate = enabledStartDate,
            enabledEndDate = enabledEndDate,
            isSingleSelect = isSingleSelect,
            selectedDate = selectedDate,
            selectedRange = selectedRange,
            onDayClicked = { day ->
                if (isSingleSelect) {
                    selectedDate = day
                    selectedRange = null
                    onDateSelected(day)
                } else {
                    selectedRange = updateRangeSelection(day, selectedRange)
                    selectedDate = null
                    onRangeSelected(selectedRange)
                }
            }
        )
    }
}

private object SkyFitCalendarGridComponent {
    @Composable
    fun CalendarHeader(isSingleSelect: Boolean) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_calendar_dots),
                contentDescription = null,
                tint = SkyFitColor.text.default,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = if (isSingleSelect) "Tarih Belirle" else "Tarih Aralığı Belirle",
                style = SkyFitTypography.bodyLargeSemibold,
                color = SkyFitColor.text.default,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }

    @Composable
    fun CalendarMonthSelector(month: LocalDate, onPreviousMonth: () -> Unit, onNextMonth: () -> Unit) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_chevron_left),
                contentDescription = null,
                tint = SkyFitColor.text.default,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onPreviousMonth() }
            )
            Text(
                modifier = Modifier.width(80.dp),
                text = getTurkishMonthName(month.month.number),
                style = SkyFitTypography.bodyLargeSemibold,
                color = SkyFitColor.text.default,
                textAlign = TextAlign.Center
            )
            Icon(
                painter = painterResource(Res.drawable.ic_chevron_right),
                contentDescription = null,
                tint = SkyFitColor.text.default,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onNextMonth() }
            )
        }
    }


    @Composable
    fun DaysOfWeekRow() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmrt", "Paz").forEach { day ->
                Text(
                    text = day,
                    style = SkyFitTypography.bodyMediumRegular,
                    color = SkyFitColor.text.default,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }

    @Composable
    fun CalendarGrid(
        days: List<LocalDate?>,
        selectedMonth: LocalDate,
        enabledStartDate: LocalDate?,
        enabledEndDate: LocalDate?,
        isSingleSelect: Boolean,
        selectedDate: LocalDate?,
        selectedRange: Pair<LocalDate, LocalDate>?,
        onDayClicked: (LocalDate) -> Unit
    ) {
        val rows = days.chunked(7)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            rows.forEach { week ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    week.forEach { day ->
                        if (day != null) {
                            val isInCurrentMonth = day.monthNumber == selectedMonth.monthNumber
                            val isEnabled = (enabledStartDate == null || !day.isBefore(enabledStartDate)) &&
                                    (enabledEndDate == null || !day.isAfter(enabledEndDate))
                            val isSelected = if (isSingleSelect) {
                                day == selectedDate
                            } else {
                                selectedRange?.let { day in it.first..it.second } ?: false
                            }
                            DayBox(
                                day = day,
                                isSelected = isSelected,
                                isInCurrentMonth = isInCurrentMonth,
                                isEnabled = isEnabled,
                                onClick = { if (isEnabled) onDayClicked(day) }
                            )
                        } else {
                            Spacer(modifier = Modifier.size(40.dp))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun DayBox(day: LocalDate, isSelected: Boolean, isInCurrentMonth: Boolean, isEnabled: Boolean, onClick: () -> Unit) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = when {
                        isSelected -> SkyFitColor.border.secondaryButton
                        isInCurrentMonth -> SkyFitColor.background.surfaceSecondary
                        else -> Color.Transparent
                    },
                    shape = CircleShape
                )
                .clickable(enabled = isEnabled) { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = day.dayOfMonth.toString(),
                style = SkyFitTypography.bodyMediumRegular,
                color = when {
                    !isEnabled -> Color.Gray
                    isInCurrentMonth -> Color.White
                    else -> Color.Gray
                }
            )
        }
    }
}