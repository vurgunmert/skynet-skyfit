package com.vurgun.skyfit.core.ui.components.schedule.weekly

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
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.icon.IconAsset
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.icon.SkyIconTint
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.ic_chevron_left
import fiwe.core.ui.generated.resources.ic_chevron_right

data class CalendarWeekDayItemModel(
    val date: LocalDate,
    val dayName: String,  // "Pzt", "Sal", etc.
    val isSelected: Boolean
)

@Composable
fun CalendarWeekDaySelector(
    daysOfWeek: List<CalendarWeekDayItemModel>,
    onDaySelected: (LocalDate) -> Unit,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        // Labels (Pzt, Sal...)
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            daysOfWeek.forEach { day ->
                Box(
                    Modifier
                        .requiredSizeIn(minWidth = 36.dp, minHeight = 36.dp, maxWidth = 40.dp, maxHeight = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
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
            SkyIcon(
                res = Res.drawable.ic_chevron_left,
                size = SkyIconSize.Medium,
                tint = SkyIconTint.Default,
                onClick = onPreviousWeek
            )

            daysOfWeek.forEach { day ->
                DaySelectorDayItem(
                    dayOfMonth = day.date.dayOfMonth,
                    selected = day.isSelected,
                    onClick = { onDaySelected(day.date) }
                )
            }

            SkyIcon(
                res = Res.drawable.ic_chevron_right,
                size = SkyIconSize.Medium,
                tint = SkyIconTint.Default,
                onClick = onNextWeek
            )
        }
    }
}

@Composable
private fun DaySelectorDayItem(
    dayOfMonth: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .requiredSizeIn(minWidth = 36.dp, minHeight = 36.dp, maxWidth = 40.dp, maxHeight = 40.dp)
            .clip(CircleShape)
            .background(
                color = SkyFitColor.background.surfaceActive,
                shape = CircleShape
            )
            .then(
                if (selected) {
                    Modifier.border(
                        width = 1.dp,
                        color = SkyFitColor.border.secondaryButton,
                        shape = CircleShape
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
fun rememberWeekDaySelectorState(weekDaySelectorController: CalendarWeekDaySelectorController): CalendarWeekDaySelectorState {
    val selectedDate by weekDaySelectorController.selectedDate.collectAsState()
    val weekDays by weekDaySelectorController.weekDays.collectAsState()
    return CalendarWeekDaySelectorState(selectedDate, weekDays)
}