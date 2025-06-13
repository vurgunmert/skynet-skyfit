package com.vurgun.skyfit.feature.home.screen.user

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findParentByKey
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.image.SkyImage
import com.vurgun.skyfit.core.ui.components.image.SkyImageShape
import com.vurgun.skyfit.core.ui.components.image.SkyImageSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.schedule.monthly.HomeEventCalendarSelector
import com.vurgun.skyfit.core.ui.components.schedule.monthly.rememberEventCalendarController
import com.vurgun.skyfit.core.ui.components.special.CharacterImage
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.special.MembershipRequestCard
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.home.component.HomeCompactComponent
import com.vurgun.skyfit.feature.home.component.MobileUserHomeUpcomingAppointmentsComponent
import com.vurgun.skyfit.feature.home.model.UserHomeAction
import com.vurgun.skyfit.feature.home.model.UserHomeEffect.*
import com.vurgun.skyfit.feature.home.model.UserHomeUiState
import com.vurgun.skyfit.feature.home.model.UserHomeViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.member_since_day_zero
import skyfit.core.ui.generated.resources.member_since_days
import skyfit.core.ui.generated.resources.refresh_action

@Composable
internal fun UserHomeCompact(viewModel: UserHomeViewModel) {

    val dashboardNavigator = LocalNavigator.currentOrThrow.findParentByKey("dashboard")
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        val screen = when (effect) {
            is NavigateToVisitFacility -> SharedScreen.FacilityProfileVisitor(effect.facilityId)
            NavigateToConversations -> SharedScreen.Conversations
            NavigateToAppointments -> SharedScreen.Appointments
            NavigateToNotifications -> SharedScreen.Notifications
            NavigateToChatbot -> SharedScreen.ChatBot
            is NavigateToActivityCalendar -> SharedScreen.UserActivityCalendar(null)
        }
        dashboardNavigator?.push(screen)
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
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    UserHomeCompactComponent.TopBar(content, viewModel::onAction)
                },
                content = {
                    UserHomeCompactComponent.Content(content, viewModel::onAction)
                }
            )
        }
    }
}

private object UserHomeCompactComponent {

    @Composable
    fun TopBar(
        content: UserHomeUiState.Content,
        onAction: (UserHomeAction) -> Unit
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            FacilityMembershipGroup(content, onAction)

            Spacer(Modifier.weight(1f))

            HomeCompactComponent.BasicTopBar(
                onClickNotifications = { onAction(UserHomeAction.OnClickNotifications) },
                onClickConversations = { onAction(UserHomeAction.OnClickConversations) },
            )
        }
    }

    @Composable
    fun Content(
        content: UserHomeUiState.Content,
        onAction: (UserHomeAction) -> Unit
    ) {
        val eventCalendarController = rememberEventCalendarController(
            activatedDatesProvider = { content.calendarState?.activeCalendarDates.orEmpty() },
            completedDatesProvider = { emptySet() } // TODO : Check state
        )

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

            content.membershipState?.takeIf { it.requestReceived }?.let {
                MembershipRequestCard(
                    facilityId = 1,
                    facilityName = "SkyTesis",
                    facilityImageUrl = "https://fastly.picsum.photos/id/84/300/300.jpg?hmac=6V7k9m6F8nydgjpa_pSyib_6Z_0iBePGa5sHDUS8bVs",
                    onConfirm = { },
                    onDecline = { }
                )
            }

            content.calendarState?.let {
                HomeEventCalendarSelector(
                    controller = eventCalendarController,
                    onDateSelected = { onAction(UserHomeAction.OnClickShowCalendar) },
                    onClickShowAll = { onAction(UserHomeAction.OnClickShowCalendar) },
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                )
            }

            content.appointmentsState?.appointments?.let { appointments ->
                FeatureVisible(appointments.isNotEmpty()) {
                    MobileUserHomeUpcomingAppointmentsComponent(
                        appointments = appointments,
                        onClickShowAll = { onAction(UserHomeAction.OnClickAppointments) }
                    )
                }
            }

            Spacer(Modifier.height(128.dp))
        }
    }

    @Composable
    private fun FacilityMembershipGroup(
        content: UserHomeUiState.Content,
        onAction: (UserHomeAction) -> Unit,
        modifier: Modifier = Modifier,
    ) {
        content.membershipState?.memberFacility?.let { facilityProfile ->
            Row(
                modifier = modifier
                    .clickable { onAction(UserHomeAction.OnClickFacility(facilityProfile.gymId)) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                SkyImage(
                    url = facilityProfile.backgroundImageUrl,
                    shape = SkyImageShape.Circle,
                    size = SkyImageSize.Size48
                )

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    SkyText(
                        text = facilityProfile.facilityName,
                        styleType = TextStyleType.BodyMediumSemibold
                    )
                    Spacer(Modifier.height(4.dp))

                    val memberText = content.membershipState.memberDurationDays.let { days ->
                        if (days == 0) {
                            stringResource(Res.string.member_since_day_zero)
                        } else {
                            stringResource(Res.string.member_since_days, days)
                        }
                    }

                    SkyText(
                        text = memberText,
                        styleType = TextStyleType.BodySmall,
                        color = SkyFitColor.text.secondary
                    )
                }
            }
        }
    }
}