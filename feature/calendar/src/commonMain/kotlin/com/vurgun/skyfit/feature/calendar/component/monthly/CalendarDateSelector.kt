package com.vurgun.skyfit.feature.calendar.component.monthly

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
import com.vurgun.skyfit.core.data.utility.getTurkishMonthName
import com.vurgun.skyfit.core.ui.components.icon.ActionIcon
import com.vurgun.skyfit.core.ui.components.icon.IconAsset
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import kotlinx.datetime.LocalDate

@Composable
fun CalendarDateSelector(
    viewModel: CalendarDateSelectorController,
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