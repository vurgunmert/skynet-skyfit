package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.schedule.LegacySkyFitCalendarGridComponent
import com.vurgun.skyfit.core.ui.components.schedule.MobileUserActivityHourlyCalendarComponent
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.calendar_label

class UserActivityCalendarScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        MobileUserActivityCalendarScreen(
            goToBack = { navigator.pop() }
        )
    }

}

@Composable
private fun MobileUserActivityCalendarScreen(goToBack: () -> Unit) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.calendar_label),
                onClickBack = goToBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
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
