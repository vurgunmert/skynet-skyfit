package com.vurgun.skyfit.core.ui.components.schedule.monthly

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.datetime.LocalDate

//// TODO: Probably use this directly in the filters
//@Composable
//fun CalendarSingleDateSelectorFilter(
//    modifier: Modifier = Modifier,
//    onSelectionChanged: (start: LocalDate) -> Unit
//) {
//    val dateSelectorController = rememberNonEmptyCalendarSelectorController(CalendarSelectionMode.Single)
//    val state by dateSelectorController.state.collectAsState()
//
//    LaunchedEffect(state.selectedStartDate) {
//        onSelectionChanged(state.selectedStartDate)
//    }
//
//    var expanded by remember { mutableStateOf(false) }
//
//    Column(
//        modifier
//            .fillMaxWidth()
//            .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
//            .padding(16.dp)
//    ) {
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Start
//        ) {
//            BodyMediumSemiboldText("Tarih", modifier = Modifier.weight(1f), color = SkyFitColor.text.secondary)
//            ActionIcon(if (expanded) IconAsset.Minus.resource else IconAsset.Plus.resource) { expanded = !expanded }
//        }
//
//        if (expanded) {
//            Spacer(Modifier.height(16.dp))
//
//            CalendarDateSelector(dateSelectorController) { _, _ -> }
//        }
//
//        Spacer(Modifier.height(16.dp))
//
//        Divider(Modifier.fillMaxWidth(), color = SkyFitColor.border.default)
//    }
//}

@Composable
fun CalendarSingleDateSelector(
    onSelectionChanged: (start: LocalDate) -> Unit
) {
    val dateSelectorController = rememberNonEmptyCalendarSelectorController(CalendarSelectionMode.Single)
    val state by dateSelectorController.state.collectAsState()

    LaunchedEffect(state.selectedStartDate) {
        state.selectedStartDate?.let { onSelectionChanged(it) }
    }

    CalendarDateSelector(dateSelectorController) { _, _ -> }
}
