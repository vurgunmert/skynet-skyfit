package com.vurgun.skyfit.feature.profile.facility

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.data.core.utility.now
import com.vurgun.skyfit.feature.calendar.components.component.calendar.monthly.CalendarRangeDateSelectorCard
import com.vurgun.skyfit.ui.core.components.special.ButtonSize
import com.vurgun.skyfit.ui.core.components.special.ButtonState
import com.vurgun.skyfit.ui.core.components.special.ButtonVariant
import com.vurgun.skyfit.ui.core.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.components.event.LessonSessionColumn
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.appointment_book_action
import skyfit.ui.core.generated.resources.appointment_create_action
import skyfit.ui.core.generated.resources.ic_check

@Composable
fun MobileFacilityCalendarVisitedScreen(
    goToBack: () -> Unit,
    goToAppointmentDetail: () -> Unit,
) {

    val viewModel = remember { FacilityCalendarVisitedViewModel() }
    val lessonsColumnViewData by viewModel.lessonsColumnViewData.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()
    val isAppointmentAllowed by viewModel.isBookingEnabled.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadData(LocalDate.now())
    }

    LaunchedEffect(navigationEvent) {
        when (navigationEvent) {
            FacilityCalendarVisitedEvent.GoToSessionDetail -> {

            }

            null -> Unit
        }
    }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.appointment_book_action), onClickBack = goToBack)
        },
        bottomBar = {
            if (isAppointmentAllowed) {
                MobileFacilityCalendarVisitedScreenCreateActionComponent(onClick = goToAppointmentDetail)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CalendarRangeDateSelectorCard(
                onSelectionChanged = { start, end ->
                    // TODO: Set date
                }
            )

            lessonsColumnViewData?.let {
                LessonSessionColumn(
                    lessons = it.items,
                    onClickItem = viewModel::handleClassSelection
                )
            }

            Spacer(Modifier.height(112.dp))
        }
    }
}

@Composable
fun MobileFacilityCalendarVisitedScreenCreateActionComponent(onClick: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(32.dp)) {
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = stringResource(Res.string.appointment_create_action),
            onClick = onClick,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            state = ButtonState.Rest,
            leftIconPainter = painterResource(Res.drawable.ic_check)
        )
    }
}