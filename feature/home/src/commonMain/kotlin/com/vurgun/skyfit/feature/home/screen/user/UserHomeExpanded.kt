package com.vurgun.skyfit.feature.home.screen.user

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.registry.ScreenProvider
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findParentByKey
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.box.TodoBox
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalOverlayController
import com.vurgun.skyfit.feature.home.component.ExpandedDefaultTopBar
import com.vurgun.skyfit.feature.home.component.HomeTopBarState
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
                overlayController.invoke(SharedScreen.UserActivityCalendar(effect.date))
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

            SharedHomeComponent.ExpandedLayout(
                topbar = { UserHomeExpandedComponent.TopBar(content, viewModel::onAction) },
                content = { UserHomeExpandedComponent.Content(content, viewModel::onAction) }
            )
        }
    }
}

private object UserHomeExpandedComponent {

    @Composable
    fun TopBar(
        content: UserHomeUiState.Content,
        onAction: (ScreenProvider) -> Unit
    ) {
        ExpandedDefaultTopBar(
            state = HomeTopBarState(),
            onNavigate = onScreenAction,
            modifier = Modifier.fillMaxWidth()
        )
    }

    @Composable
    fun Content(
        content: UserHomeUiState.Content,
        onAction: (UserHomeAction) -> Unit
    ) {
        TodoBox("UserHomeExpandedComponent-Content")

    }
}