package com.vurgun.skyfit.feature.home.screen.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findParentByKey
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonState
import com.vurgun.skyfit.core.ui.components.layout.ExpandedLayout
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.schedule.monthly.EventCalendarController
import com.vurgun.skyfit.core.ui.components.schedule.monthly.HomeEventCalendarSelector
import com.vurgun.skyfit.core.ui.components.schedule.monthly.rememberEventCalendarController
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.special.FiweLogoLight
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.feature.home.model.UserHomeAction
import com.vurgun.skyfit.feature.home.model.UserHomeEffect.*
import com.vurgun.skyfit.feature.home.model.UserHomeUiState
import com.vurgun.skyfit.feature.home.model.UserHomeViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.background_chatbot
import skyfit.core.ui.generated.resources.refresh_action

@Composable
internal fun UserHomeExpanded(viewModel: UserHomeViewModel) {

    val dashboardNavigator = LocalNavigator.currentOrThrow
    val overlayController = LocalCompactOverlayController.current
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is NavigateToVisitFacility ->
                dashboardNavigator.push(SharedScreen.FacilityProfile(effect.facilityId))

            NavigateToConversations ->
                overlayController?.invoke(SharedScreen.Conversations)

            NavigateToNotifications ->
                overlayController?.invoke(SharedScreen.Notifications)

            NavigateToAppointments ->
                overlayController?.invoke(SharedScreen.UserAppointmentListing)

            NavigateToChatbot -> {
                dashboardNavigator.replace(SharedScreen.ChatBot)
            }

            is NavigateToActivityCalendar ->
                overlayController?.invoke(SharedScreen.UserActivityCalendar())
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
            UserHomeExpandedComponent.Content(content, viewModel::onAction)
        }
    }
}

private object UserHomeExpandedComponent {

    @Composable
    fun Content(
        content: UserHomeUiState.Content,
        onAction: (UserHomeAction) -> Unit
    ) {
        val eventCalendarController = rememberEventCalendarController(
            activatedDatesProvider = { content.calendarState?.activeCalendarDates.orEmpty() },
            completedDatesProvider = { emptySet() } // TODO : Check state
        )

        ExpandedLayout.LeftLargeMultiLaneScaffold(
            leftContent = {
                FeatureVisible(false) { //statisticsEnabled
                    StatisticsCard(content, onAction)
                }

                CalendarGroup(content, eventCalendarController, onAction)

                FeatureVisible(false) { //featuredItemsEnabled
                    FeaturedItemsGroup(content, onAction)
                }

                FeatureVisible(false) { //statisticsEnabled
                    StatisticGraphGroup()
                }
            },
            rightContent = {
                FeatureVisible(true) { //chatbotEnabled
                    ChatBotCardMini(onClick = { onAction(UserHomeAction.OnClickChatBot) })
                }

                AppointmentGroup(content, onAction)

                FeatureVisible(false) { //nutritionEnabled
                    DietGroup()
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }


    @Composable
    private fun CalendarGroup(
        content: UserHomeUiState.Content,
        eventCalendarController: EventCalendarController,
        onAction: (UserHomeAction) -> Unit
    ) {
        Row(
            Modifier.fillMaxWidth()
                .background(SkyFitColor.background.default, shape = RoundedCornerShape(16.dp))
        ) {
            content.calendarState?.let {
                HomeEventCalendarSelector(
                    controller = eventCalendarController,
                    onDateSelected = { onAction(UserHomeAction.OnClickShowCalendar(it)) },
                    onClickShowAll = { onAction(UserHomeAction.OnClickShowCalendar(null)) },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    @Composable
    private fun FeaturedItemsGroup(
        content: UserHomeUiState.Content,
        onAction: (UserHomeAction) -> Unit
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            content.featuredContentState?.featuredFacilities?.takeUnless { it.isEmpty() }?.let {
//                TODO()
            }
            content.featuredContentState?.featuredTrainers?.takeUnless { it.isEmpty() }?.let {
//                TODO()
            }
        }
    }

    @Composable
    private fun StatisticsCard(content: UserHomeUiState.Content, onAction: (UserHomeAction) -> Unit) {
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(100.dp)
                .background(
                    SkyFitColor.background.default,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }

    @Composable
    private fun StatisticGraphGroup(enabled: Boolean = false) {
        FeatureVisible(enabled) {
            Box(
                modifier = Modifier.fillMaxWidth().height(200.dp)
                    .background(
                        SkyFitColor.background.default,
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }
    }

    @Composable
    private fun AppointmentGroup(
        content: UserHomeUiState.Content,
        onAction: (UserHomeAction) -> Unit
    ) {

        content.appointmentsState?.appointments?.let {
            Box(
                Modifier.fillMaxWidth()
                    .background(
                        color = SkyFitColor.background.default,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                UserUpcomingAppointmentsGroup(it, onClickShowAll = {
                    onAction(UserHomeAction.OnClickAppointments)
                })
            }
        }
    }

    @Composable
    private fun DietGroup() {
        Box(
            modifier = Modifier.size(761.dp, 200.dp)
                .background(
                    SkyFitColor.background.default,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }


    @Composable
    private fun ChatBotCardMini(
        onClick: () -> Unit = {}
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .height(96.dp)
                .background(SkyFitColor.specialty.buttonBgRest)
        ) {
            Image(
                painter = painterResource(Res.drawable.background_chatbot),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 12.dp),
            ) {
                SkyText(
                    text = "İhtiyacın olan postür analizini anında keşfet!",
                    styleType = TextStyleType.BodyMediumSemibold,
                    modifier = Modifier.width(293.dp),
                    color = SkyFitColor.text.inverse
                )
                Spacer(Modifier.height(8.dp))
                SkyButton(
                    label = "AI Antrenorunu Baslat",
                    onClick = onClick,
                    size = SkyButtonSize.Medium,
                    state = SkyButtonState.Disabled
                )
            }

            FiweLogoLight(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 12.dp)
                    .size(54.dp)
            )
        }
    }

}