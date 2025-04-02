package com.vurgun.skyfit.designsystem.components.calendar.monthly

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.text.BodyMediumSemiboldText
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.designsystem.components.icon.ActionIcon
import kotlinx.datetime.LocalDate
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_minus
import skyfit.composeapp.generated.resources.ic_plus

// TODO: Probably use this directly in the filters
@Composable
fun CalendarSingleDateSelectorFilter(
    modifier: Modifier = Modifier,
    onSelectionChanged: (start: LocalDate) -> Unit
) {
    val viewModel = remember { CalendarDateSelectorViewModel(CalendarSelectionMode.Single) }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.selectedStartDate) {
        onSelectionChanged(state.selectedStartDate)
    }

    var expanded by remember { mutableStateOf(false) }

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
            BodyMediumSemiboldText("Tarih", modifier = Modifier.weight(1f), color = SkyFitColor.text.secondary)
            ActionIcon(if (expanded) Res.drawable.ic_minus else Res.drawable.ic_plus) { expanded = !expanded }
        }

        if (expanded) {
            Spacer(Modifier.height(16.dp))

            CalendarDateSelector(viewModel) { _, _ -> }
        }

        Spacer(Modifier.height(16.dp))

        Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
    }
}

@Composable
fun CalendarSingleDateSelector(
    onSelectionChanged: (start: LocalDate) -> Unit
) {
    val viewModel = remember { CalendarDateSelectorViewModel(CalendarSelectionMode.Single) }

    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.selectedStartDate) {
        onSelectionChanged(state.selectedStartDate)
    }

    CalendarDateSelector(viewModel) { _, _ -> }
}