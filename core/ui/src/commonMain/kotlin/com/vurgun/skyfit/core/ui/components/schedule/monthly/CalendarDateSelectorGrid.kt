package com.vurgun.skyfit.core.ui.components.schedule.monthly

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.schedule.monthly.DatePickerCalendar.CalendarMonthDayItemState
import com.vurgun.skyfit.core.ui.components.schedule.monthly.DatePickerCalendar.CalendarMonthDaySelectorItem
import com.vurgun.skyfit.core.ui.components.schedule.monthly.DatePickerCalendar.CalendarMonthDaySelectorItemModel
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import kotlinx.datetime.LocalDate
import kotlin.collections.chunked
import kotlin.collections.forEach

object DatePickerCalendar {

    sealed class CalendarMonthDayItemState(val backgroundColor: Color, val textColor: Color) {

        object Filter {
            data object Selected :
                CalendarMonthDayItemState(SkyFitColor.border.secondaryButton, SkyFitColor.text.inverse)

            data object InMonth :
                CalendarMonthDayItemState(SkyFitColor.background.surfaceSecondary, SkyFitColor.text.default)

            data object OutMonth : CalendarMonthDayItemState(SkyFitColor.transparent, SkyFitColor.text.secondary)
            data object Label : CalendarMonthDayItemState(SkyFitColor.transparent, SkyFitColor.text.default)
        }
    }

    data class CalendarMonthDaySelectorItemModel(
        val label: String,
        val date: LocalDate,
        val state: CalendarMonthDayItemState
    )

    @Composable
    private fun CalendarMonthDaySelectorItem(
        value: String,
        state: CalendarMonthDayItemState,
        size: Dp = 40.dp,
        onClick: (() -> Unit)? = null,
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .clickable(enabled = onClick != null) { onClick?.invoke() }
                .background(state.backgroundColor)
                .size(size),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = if (state == CalendarMonthDayItemState.Filter.Label) SkyFitTypography.bodyMediumRegular else SkyFitTypography.bodyXSmall,
                color = state.textColor
            )
        }
    }

    @Composable
    fun CalendarMonthDaySelectorGrid(
        monthDays: List<CalendarMonthDaySelectorItemModel>,
        onDateClick: (LocalDate) -> Unit,
        modifier: Modifier = Modifier
    ) {
        BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
            val size = (maxWidth / 7).coerceIn(40.dp, 50.dp)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Day labels
                Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                    listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmrt", "Paz").forEach { dayName ->
                        CalendarMonthDaySelectorItem(
                            value = dayName,
                            state = CalendarMonthDayItemState.Filter.Label,
                            size = size
                        )
                    }
                }

                // Days
                monthDays.chunked(7).forEach { week ->
                    Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                        week.forEach { day ->
                            CalendarMonthDaySelectorItem(
                                value = day.label,
                                state = day.state,
                                size = size,
                                onClick = { onDateClick(day.date) }
                            )
                        }
                    }
                }
            }
        }
    }
}


object EventCalendar {

    sealed class EventCalendarMonthDayItemState(
        val backgroundColor: Color,
        val borderColor: Color,
        val textColor: Color
    ) {
        object Filter {
            data object Selected :
                EventCalendarMonthDayItemState(
                    SkyFitColor.transparent,
                    SkyFitColor.border.secondaryButton,
                    SkyFitColor.text.default
                )

            data object InMonth :
                EventCalendarMonthDayItemState(
                    SkyFitColor.background.surfaceSecondary,
                    SkyFitColor.transparent,
                    SkyFitColor.text.default
                )

            data object OutMonth : EventCalendarMonthDayItemState(
                SkyFitColor.transparent,
                SkyFitColor.transparent,
                SkyFitColor.text.secondary
            )

            data object Label : EventCalendarMonthDayItemState(
                SkyFitColor.transparent,
                SkyFitColor.transparent,
                SkyFitColor.text.default
            )

            data object Activated : EventCalendarMonthDayItemState(
                SkyFitColor.border.secondaryButton,
                SkyFitColor.transparent,
                SkyFitColor.text.inverse
            )

            data object Completed : EventCalendarMonthDayItemState(
                SkyFitColor.icon.critical,
                SkyFitColor.transparent,
                SkyFitColor.text.default
            )
        }
    }

    data class EventCalendarMonthDaySelectorItemModel(
        val label: String,
        val date: LocalDate,
        val state: EventCalendarMonthDayItemState
    )

    @Composable
    private fun EventCalendarMonthDaySelectorItem(
        value: String,
        state: EventCalendarMonthDayItemState,
        size: Dp = 40.dp,
        onClick: (() -> Unit)? = null,
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(state.backgroundColor)
                .border(1.dp, state.borderColor, CircleShape)
                .clickable(enabled = onClick != null) { onClick?.invoke() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = if (state is EventCalendarMonthDayItemState.Filter.Label)
                    SkyFitTypography.bodyMediumRegular
                else
                    SkyFitTypography.bodyXSmall,
                color = state.textColor
            )
        }
    }

    @Composable
    fun EventCalendarMonthDaySelectorGrid(
        monthDays: List<EventCalendarMonthDaySelectorItemModel>,
        onDateClick: (LocalDate) -> Unit,
        modifier: Modifier = Modifier
    ) {
        BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
            val size = (maxWidth / 7).coerceIn(40.dp, 50.dp)

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Day labels
                Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                    listOf("Pzt", "Sal", "Çar", "Per", "Cum", "Cmrt", "Paz").forEach { dayName ->
                        EventCalendarMonthDaySelectorItem(
                            value = dayName,
                            state = EventCalendarMonthDayItemState.Filter.Label,
                            size = size
                        )
                    }
                }

                // Days
                monthDays.chunked(7).forEach { week ->
                    Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                        week.forEach { day ->
                            EventCalendarMonthDaySelectorItem(
                                value = day.label,
                                state = day.state,
                                size = size,
                                onClick = { onDateClick(day.date) }
                            )
                        }
                    }
                }
            }
        }
    }
}

