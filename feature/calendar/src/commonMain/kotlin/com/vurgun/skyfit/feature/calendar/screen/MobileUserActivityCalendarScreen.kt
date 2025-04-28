package com.vurgun.skyfit.feature.calendar.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.feature.calendar.component.LegacySkyFitCalendarGridComponent
import com.vurgun.skyfit.feature.calendar.component.MobileUserActivityHourlyCalendarComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import kotlinx.datetime.LocalDate

@Composable
fun MobileUserActivityCalendarScreen(goToBack: () -> Unit) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Takvim", onClickBack = goToBack)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserActivityGridCalendarComponent()
            MobileUserActivityHourlyCalendarComponent()
        }
    }
}

@Composable
private fun MobileUserActivityGridCalendarComponent() {
    var selectedDate: LocalDate by remember { mutableStateOf(LocalDate.now()) }

    LegacySkyFitCalendarGridComponent(
        initialSelectedDate = selectedDate,
        isSingleSelect = true,
        onDateSelected = { selectedDate = it }
    )
}
