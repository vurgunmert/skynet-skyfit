package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.utility.toTurkishLongDate
import com.vurgun.skyfit.core.data.v1.domain.user.model.CalendarEvent
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.popUntil
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.event.BasicActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.event.BookedActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.schedule.monthly.EventCalendarSelector
import com.vurgun.skyfit.core.ui.components.schedule.monthly.rememberEventCalendarController
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class UserActivityCalendarScreen(private val selectedDate: LocalDate? = null) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<UserActivityCalendarViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                UserActivityCalendarEffect.NavigateToBack -> navigator.pop()
                is UserActivityCalendarEffect.NavigateToCalendarSearch -> {
                    navigator.push(UserActivityCalendarSearchScreen(effect.date))
                }
            }
        }

        LaunchedEffect(selectedDate) {
            viewModel.loadData(selectedDate)
        }

        when (uiState) {
            UserActivityCalendarUiState.Loading -> FullScreenLoaderContent()
            is UserActivityCalendarUiState.Error -> {
                val message = (uiState as UserActivityCalendarUiState.Error).message
                ErrorScreen(
                    message = message,
                    onConfirm = { navigator.popUntil(SharedScreen.Main) },
                    confirmText = stringResource(Res.string.go_to_home_action)
                )
            }

            is UserActivityCalendarUiState.Content -> {
                MobileUserActivityCalendarScreen(viewModel, viewModel::onAction)
            }
        }
    }
}

@Composable
private fun MobileUserActivityCalendarScreen(
    viewModel: UserActivityCalendarViewModel,
    onAction: (UserActivityCalendarAction) -> Unit,
) {
    val activeDays by viewModel.activeDays.collectAsState()
    val completedDays by viewModel.completedDays.collectAsState()
    val selectedDay by viewModel.selectedDate.collectAsState()
    val events by viewModel.filteredEvents.collectAsState()

    val controller = rememberEventCalendarController(
        initialSelectedDate = selectedDay,
        activatedDatesProvider = { activeDays },
        completedDatesProvider = { completedDays }
    )

    LaunchedEffect(controller) {
        controller.refreshEvents()
    }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.calendar_label),
                onClickBack = { onAction(UserActivityCalendarAction.OnClickBack) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            EventCalendarSelector(
                controller = controller,
                onDateSelected = { selectedDate ->
                    onAction(UserActivityCalendarAction.OnChangeSelectedDate(selectedDate))
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SkyText(
                    text = selectedDay.toTurkishLongDate(),
                    styleType = TextStyleType.BodyMediumSemibold,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(Modifier.weight(1f))
                SkyButton(
                    label = stringResource(Res.string.add_action),
                    leftIcon = painterResource(Res.drawable.ic_plus),
                    size = SkyButtonSize.Micro,
                    onClick = { onAction(UserActivityCalendarAction.OnClickAdd) }
                )
            }

            MobileUserActivityEventList(events)

            Spacer(Modifier.height(124.dp))
        }
    }
}

@Composable
private fun MobileUserActivityEventList(
    events: List<CalendarEvent>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        events.forEach { event ->

            if (event.isLesson) {
                BookedActivityCalendarEventItem(
                    title = event.name,
                    iconId = event.lessonIcon ?: SkyFitAsset.SkyFitIcon.EXERCISES.id,
                    date = event.startDate.toString(),
                    timePeriod = "${event.startTime} - ${event.endTime}",
                    location = event.gymName.toString(),
                    trainer = event.trainerFullName.toString(),
                    note = event.trainerNote,
                    enabled = event.isAfterNow
                )
            } else {
                BasicActivityCalendarEventItem(
                    title = event.name,
                    iconId = event.lessonIcon,
                    timePeriod = "${event.startTime} - ${event.endTime}",
                    enabled = event.isAfterNow
                )
            }
        }
    }
}