package com.vurgun.skyfit.feature.dashboard.home.mobile

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
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.schedule.monthly.HomeEventCalendarSelector
import com.vurgun.skyfit.core.ui.components.schedule.monthly.rememberEventCalendarController
import com.vurgun.skyfit.core.ui.components.special.CharacterImage
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.feature.dashboard.component.MobileUserHomeUpcomingAppointmentsComponent
import com.vurgun.skyfit.feature.dashboard.home.UserHomeAction
import com.vurgun.skyfit.feature.dashboard.home.UserHomeEffect
import com.vurgun.skyfit.feature.dashboard.home.UserHomeUiState
import com.vurgun.skyfit.feature.dashboard.home.UserHomeViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.refresh_action

class UserHomeScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val viewModel = koinScreenModel<UserHomeViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                is UserHomeEffect.NavigateToVisitFacility -> {
                    appNavigator.push(SharedScreen.FacilityProfileVisitor(effect.facilityId))
                }

                UserHomeEffect.NavigateToConversations -> {
                    appNavigator.push(SharedScreen.Conversations)
                }

                UserHomeEffect.NavigateToAppointments -> {
                    appNavigator.push(SharedScreen.UserAppointmentListing)
                }

                UserHomeEffect.NavigateToNotifications -> {
                    appNavigator.push(SharedScreen.NotificationsCompact)
                }

                is UserHomeEffect.NavigateToActivityCalendar -> {
                    appNavigator.push(SharedScreen.UserActivityCalendar(effect.date))
                }
            }
        }

        LaunchedEffect(viewModel) {
            viewModel.loadData()
        }

        when (uiState) {
            UserHomeUiState.Loading -> FullScreenLoaderContent()
            is UserHomeUiState.Error -> {
                val message = (uiState as UserHomeUiState.Error).message
                ErrorScreen(
                    message = message,
                    confirmText = stringResource(Res.string.refresh_action),
                    onConfirm = { viewModel.loadData() }
                )
            }

            is UserHomeUiState.Content -> {
                val content = uiState as UserHomeUiState.Content
                UserHomeCompact(content, viewModel::onAction)
            }
        }
    }
}

@Composable
private fun UserHomeCompact(
    content: UserHomeUiState.Content,
    onAction: (UserHomeAction) -> Unit
) {
    val eventCalendarController = rememberEventCalendarController(
        activatedDatesProvider = { content.activeCalendarDates },
        completedDatesProvider = { emptySet() }
    )

    LaunchedEffect(eventCalendarController) {
        eventCalendarController.refreshEvents()
    }

    SkyFitMobileScaffold(
        topBar = {
            MobileUserHomeTopBar(
                profile = content.profile,
                memberFacility = content.memberFacility,
                onClickFacility = { onAction(UserHomeAction.OnClickFacility(it)) },
                notificationsEnabled = false,
                onClickNotifications = { onAction(UserHomeAction.OnClickNotifications) },
                conversationsEnabled = false,
                onClickConversations = { onAction(UserHomeAction.OnClickConversations) }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            CharacterImage(
                characterType = content.characterType,
                modifier = Modifier
            )

            HomeEventCalendarSelector(
                controller = eventCalendarController,
                onDateSelected = { selectedDate ->
                    onAction(UserHomeAction.OnChangeSelectedDate(selectedDate))
                },
                onClickShowAll = {
                    onAction(UserHomeAction.OnClickShowCalendar)
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )

            FeatureVisible(content.appointments.isNotEmpty()) {
                MobileUserHomeUpcomingAppointmentsComponent(
                    appointments = content.appointments,
                    onClickShowAll = { onAction(UserHomeAction.OnClickAppointments) }
                )
            }

            Spacer(Modifier.height(128.dp))
        }
    }
}