package com.vurgun.skyfit.feature.calendar.components.component.calendar.monthly

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import kotlinx.datetime.LocalDate

sealed class CalendarMonthDayItemState(val backgroundColor: Color, val textColor: Color) {

    object Filter {
        data object Selected : CalendarMonthDayItemState(SkyFitColor.border.secondaryButton, SkyFitColor.text.inverse)
        data object InMonth : CalendarMonthDayItemState(SkyFitColor.background.surfaceSecondary, SkyFitColor.text.default)
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
