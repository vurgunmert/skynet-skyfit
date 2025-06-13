package com.vurgun.skyfit.feature.home.screen.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findParentByKey
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.layout.ExpandedLayout
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalOverlayController
import com.vurgun.skyfit.feature.home.model.UserHomeAction
import com.vurgun.skyfit.feature.home.model.UserHomeEffect.*
import com.vurgun.skyfit.feature.home.model.UserHomeUiState
import com.vurgun.skyfit.feature.home.model.UserHomeViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.refresh_action

@Composable
internal fun UserHomeExpanded(viewModel: UserHomeViewModel) {

    val dashboardNavigator = LocalNavigator.currentOrThrow.findParentByKey("dashboard")
    val overlayController = LocalOverlayController.current
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is NavigateToVisitFacility ->
                dashboardNavigator?.push(SharedScreen.FacilityProfileVisitor(effect.facilityId))

            NavigateToConversations ->
                overlayController.invoke(SharedScreen.Conversations)

            NavigateToNotifications ->
                overlayController.invoke(SharedScreen.Notifications)

            NavigateToAppointments ->
                overlayController.invoke(SharedScreen.Appointments)

            NavigateToChatbot ->
                overlayController.invoke(SharedScreen.ChatBot)

            is NavigateToActivityCalendar ->
                overlayController.invoke(SharedScreen.UserActivityCalendar())
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
        ExpandedLayout.LeftLargeMultiLaneScaffold(
            leftContent = {
                Column {
                    CalendarContent()
                    FeaturedContent()
                    StatisticCardsContent()
                    StatisticGraphContent()
                }
            },
            rightContent = {
                Box(
                    Modifier.fillMaxSize()
                        .background(
                            SkyFitColor.specialty.secondaryButtonRest,
                            RoundedCornerShape(16.dp)
                        )
                ) {

                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }


    @Composable
    private fun CalendarContent() {
        Box(
            modifier = Modifier.size(761.dp, 354.dp)
                .background(
                    SkyFitColor.background.default,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }

    @Composable
    private fun FeaturedContent() {
        Box(
            modifier = Modifier.size(761.dp, 342.dp)
                .background(
                    SkyFitColor.background.default,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }

    @Composable
    private fun StatisticCardsContent() {
        Box(
            modifier = Modifier.size(761.dp, 100.dp)
                .background(
                    SkyFitColor.background.default,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }

    @Composable
    private fun StatisticGraphContent() {
        Box(
            modifier = Modifier.size(761.dp, 200.dp)
                .background(
                    SkyFitColor.background.default,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }

}