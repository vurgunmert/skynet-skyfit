package com.vurgun.skyfit.feature.dashboard.home.mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.feature.dashboard.component.EmptyFacilityAppointmentContent
import com.vurgun.skyfit.feature.dashboard.component.MobileDashboardHomeUpcomingAppointmentsComponent
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeAction
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeEffect
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeUiState
import com.vurgun.skyfit.feature.dashboard.home.FacilityHomeViewModel
import com.vurgun.skyfit.feature.dashboard.home.desktop.FacilityHomeExpandedScreen
import com.vurgun.skyfit.feature.dashboard.home.desktop.StatCardComponent
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.refresh_action

class FacilityHomeScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val viewModel = koinScreenModel<FacilityHomeViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                FacilityHomeEffect.NavigateToConversations -> {
                    appNavigator.push(SharedScreen.Conversations)
                }

                FacilityHomeEffect.NavigateToManageLessons -> {
                    appNavigator.push(SharedScreen.FacilityManageLessons)
                }

                FacilityHomeEffect.NavigateToNotifications -> {
                    appNavigator.push(SharedScreen.NotificationsCompact)
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.loadData()
        }

        when (uiState) {
            FacilityHomeUiState.Loading ->
                FullScreenLoaderContent()

            is FacilityHomeUiState.Error -> {
                val message = (uiState as FacilityHomeUiState.Error).message
                ErrorScreen(
                    message = message,
                    confirmText = stringResource(Res.string.refresh_action),
                    onConfirm = { viewModel.loadData() }
                )
            }

            is FacilityHomeUiState.Content -> {
                val content = uiState as FacilityHomeUiState.Content

                when (windowSize) {
                    WindowSize.COMPACT,
                    WindowSize.MEDIUM  -> FacilityHomeCompact(content, viewModel::onAction)
                    WindowSize.EXPANDED -> FacilityHomeExpandedScreen(content, viewModel::onAction)
                }
            }
        }
    }
}

@Composable
private fun FacilityHomeCompact(
    content: FacilityHomeUiState.Content,
    onAction: (FacilityHomeAction) -> Unit
) {

    SkyFitMobileScaffold(
        topBar = {
            MobileHomeTopBar(
                notificationsEnabled = false,
                onClickNotifications = { onAction(FacilityHomeAction.OnClickNotifications) },
                conversationsEnabled = false,
                onClickConversations = { onAction(FacilityHomeAction.OnClickConversations) }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            StatCardComponent(content)

            FacilityDashboardAppointments(content, onAction)

//            ExpandedHomeFacilityLessonTable(content.appointments)

            Spacer(Modifier.height(128.dp))
        }
    }
}

@Composable
internal fun FacilityDashboardAppointments(
    content: FacilityHomeUiState.Content,
    onAction: (FacilityHomeAction) -> Unit
) {
    if (content.activeLessons.isEmpty()) {
        EmptyFacilityAppointmentContent(
            assignedFacilityId = content.facility.gymId,
            onClickAdd = { onAction(FacilityHomeAction.OnClickLessons) }
        )
    } else {
        MobileDashboardHomeUpcomingAppointmentsComponent(
            appointments = content.activeLessons,
            onClickShowAll = { onAction(FacilityHomeAction.OnClickLessons) }
        )
    }
}

