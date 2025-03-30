package com.vurgun.skyfit.designsystem.components.calendar.weekdayselector

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_chevron_left
import skyfit.composeapp.generated.resources.ic_chevron_right

data class WeekDayUiModel(
    val date: LocalDate,
    val dayName: String,  // "Pzt", "Sal", etc.
    val isSelected: Boolean
)

@Composable
fun WeekDaySelector(
    daysOfWeek: List<WeekDayUiModel>,
    onDaySelected: (LocalDate) -> Unit,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Labels (Pzt, Sal...)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            daysOfWeek.forEach { day ->
                Box(Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                    BodyMediumRegularText(text = day.dayName)
                }
            }
        }

        Spacer(Modifier.height(4.dp))

        // Days (13, 14, etc.)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_chevron_left),
                contentDescription = null,
                modifier = Modifier.size(24.dp).clickable(onClick = onPreviousWeek)
            )

            daysOfWeek.forEach { day ->
                WeekDaySelectorDayItem(
                    dayOfMonth = day.date.dayOfMonth,
                    selected = day.isSelected,
                    onClick = { onDaySelected(day.date) }
                )
            }

            Icon(
                painter = painterResource(Res.drawable.ic_chevron_right),
                contentDescription = null,
                modifier = Modifier.size(24.dp).clickable(onClick = onNextWeek)
            )
        }
    }
}

@Composable
fun WeekDaySelectorDayItem(dayOfMonth: Int, selected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                color = SkyFitColor.background.surfaceActive,
                shape = CircleShape
            )
            .then(
                if (selected) {
                    Modifier.border(
                        width = 1.dp,
                        color = SkyFitColor.border.secondaryButton,
                        shape = RoundedCornerShape(40.dp)
                    )
                } else Modifier
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = dayOfMonth.toString(),
            style = SkyFitTypography.bodyMediumRegular
        )
    }
}

@Composable
fun rememberWeekDaySelectorState(viewModel: WeekDaySelectorViewModel): WeekDaySelectorState {
    val selectedDate by viewModel.selectedDate.collectAsState()
    val weekDays by viewModel.weekDays.collectAsState()
    return WeekDaySelectorState(selectedDate, weekDays)
}