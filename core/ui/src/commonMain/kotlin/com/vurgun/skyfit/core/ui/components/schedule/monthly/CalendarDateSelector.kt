package com.vurgun.skyfit.core.ui.components.schedule.monthly

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.data.utility.getTurkishMonthName
import com.vurgun.skyfit.core.ui.components.icon.ActionIcon
import com.vurgun.skyfit.core.ui.components.icon.IconAsset
import com.vurgun.skyfit.core.ui.components.schedule.monthly.DatePickerCalendar.CalendarMonthDaySelectorGrid
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.calendar_label
import skyfit.core.ui.generated.resources.show_all_action

@Composable
fun CalendarDateSelector(
    viewModel: CalendarDateSelectorController,
    modifier: Modifier = Modifier,
    onSelectionChanged: (start: LocalDate?, end: LocalDate?) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.selectedStartDate, state.selectedEndDate) {
        onSelectionChanged(state.selectedStartDate, state.selectedEndDate)
    }

    Column(modifier.fillMaxWidth()) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionIcon(res = IconAsset.ChevronLeft.resource, onClick = viewModel::loadPreviousMonth)

            BodyMediumSemiboldText(text = getTurkishMonthName(state.visibleMonth.monthNumber))

            ActionIcon(res = IconAsset.ChevronRight.resource, onClick = viewModel::loadNextMonth)
        }

        CalendarMonthDaySelectorGrid(
            monthDays = state.days,
            onDateClick = { viewModel.onDayClick(it) }
        )
    }
}

@Composable
fun EventCalendarSelector(
    controller: EventCalendarController,
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit
) {
    val state by controller.state.collectAsState()

    Column(modifier.fillMaxWidth()) {

        // Month navigation header
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            ActionIcon(
                res = IconAsset.ChevronLeft.resource,
                onClick = controller::loadPreviousMonth
            )

            SkyText(
                text = getTurkishMonthName(state.visibleMonth.monthNumber),
                styleType = TextStyleType.BodyMediumSemibold
            )

            ActionIcon(
                res = IconAsset.ChevronRight.resource,
                onClick = controller::loadNextMonth
            )
        }

        // Day grid
        EventCalendar.EventCalendarMonthDaySelectorGrid(
            monthDays = state.days,
            onDateClick = {
                controller.onDateClick(it)
                onDateSelected(it)
            }
        )

        // Legend
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            LegendDot(SkyFitColor.icon.success, "Tamamlandı")
            LegendDot(SkyFitColor.icon.critical, "Tamamlanmadı")
        }
    }
}


@Composable
fun HomeEventCalendarSelector(
    controller: EventCalendarController,
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate) -> Unit,
    onClickShowAll: () -> Unit
) {
    val state by controller.state.collectAsState()

    Column(modifier.widthIn(max = 430.dp)) {

        // Month navigation header
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            SkyText(
                text = stringResource(Res.string.calendar_label),
                styleType = TextStyleType.BodyLargeSemibold
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                ActionIcon(
                    res = IconAsset.ChevronLeft.resource,
                    onClick = controller::loadPreviousMonth
                )

                SkyText(
                    text = getTurkishMonthName(state.visibleMonth.monthNumber),
                    styleType = TextStyleType.BodyMediumSemibold
                )

                ActionIcon(
                    res = IconAsset.ChevronRight.resource,
                    onClick = controller::loadNextMonth
                )
            }

            SkyText(
                text = stringResource(Res.string.show_all_action),
                styleType = TextStyleType.BodyXSmall,
                color = SkyFitColor.border.secondaryButton,
                modifier = Modifier.clickable(onClick = onClickShowAll)
            )
        }

        // Day grid
        EventCalendar.EventCalendarMonthDaySelectorGrid(
            monthDays = state.days,
            onDateClick = {
                controller.onDateClick(it)
                onDateSelected(it)
            }
        )

        // Legend
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            LegendDot(SkyFitColor.icon.success, "Tamamlandı")
            LegendDot(SkyFitColor.icon.critical, "Tamamlanmadı")
        }
    }
}

@Composable
private fun LegendDot(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 6.dp),
            style = SkyFitTypography.bodyXSmall,
            color = SkyFitColor.text.secondary
        )
    }
}
