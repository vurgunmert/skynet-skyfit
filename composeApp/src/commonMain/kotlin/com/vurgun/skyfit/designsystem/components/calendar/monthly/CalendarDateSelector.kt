package com.vurgun.skyfit.designsystem.components.calendar.monthly

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.utils.getTurkishMonthName
import com.vurgun.skyfit.designsystem.components.icon.ActionIcon
import kotlinx.datetime.LocalDate
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_chevron_left
import skyfit.composeapp.generated.resources.ic_chevron_right

@Composable
fun CalendarDateSelector(
    viewModel: CalendarDateSelectorViewModel,
    modifier: Modifier = Modifier,
    onSelectionChanged: (start: LocalDate, end: LocalDate?) -> Unit
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
            ActionIcon(res = Res.drawable.ic_chevron_left, onClick = viewModel::loadPreviousMonth)

            BodyMediumSemiboldText(text = getTurkishMonthName(state.visibleMonth.monthNumber))

            ActionIcon(res = Res.drawable.ic_chevron_right, onClick = viewModel::loadNextMonth)
        }

        CalendarMonthDaySelectorGrid(
            monthDays = state.days,
            onDateClick = { viewModel.onDayClick(it) }
        )
    }
}