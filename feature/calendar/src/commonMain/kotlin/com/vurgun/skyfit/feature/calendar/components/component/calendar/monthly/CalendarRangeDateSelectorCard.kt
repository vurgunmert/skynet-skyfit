package com.vurgun.skyfit.feature.calendar.components.component.calendar.monthly

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.calendar_select_date_range
import skyfit.ui.core.generated.resources.ic_calendar_dots

@Composable
fun CalendarRangeDateSelectorCard(
    modifier: Modifier = Modifier,
    onSelectionChanged: (start: LocalDate, end: LocalDate?) -> Unit
) {
    val viewModel = remember { CalendarDateSelectorViewModel(CalendarSelectionMode.Range) }

    Column(
        modifier
            .fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {

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
                text = stringResource(Res.string.calendar_select_date_range),
                style = SkyFitTypography.bodyLargeSemibold,
                color = SkyFitColor.text.default,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(Modifier.height(16.dp))
        CalendarDateSelector(viewModel, onSelectionChanged = onSelectionChanged)
    }
}