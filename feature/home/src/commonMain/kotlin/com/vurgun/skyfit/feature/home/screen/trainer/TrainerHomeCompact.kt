package com.vurgun.skyfit.feature.home.screen.trainer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findParentByKey
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.home.model.TrainerHomeAction
import com.vurgun.skyfit.feature.home.model.TrainerHomeEffect.*
import com.vurgun.skyfit.feature.home.model.TrainerHomeUiState
import com.vurgun.skyfit.feature.home.model.TrainerHomeViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.refresh_action

@Composable
internal fun TrainerHomeCompact(viewModel: TrainerHomeViewModel) {

    val dashboardNavigator = LocalNavigator.currentOrThrow.findParentByKey("dashboard")
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is NavigateToVisitFacility ->
                dashboardNavigator?.push(SharedScreen.FacilityProfileVisitor(effect.facilityId))

            NavigateToConversations ->
                dashboardNavigator?.push(SharedScreen.Conversations)

            NavigateToAppointments ->
                dashboardNavigator?.push(SharedScreen.Appointments)

            NavigateToNotifications ->
                dashboardNavigator?.push(SharedScreen.Notifications)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.loadData()
    }

    when (uiState) {
        TrainerHomeUiState.Loading -> FullScreenLoaderContent()
        is TrainerHomeUiState.Error -> {
            val message = (uiState as TrainerHomeUiState.Error).message
            ErrorScreen(
                message = message,
                confirmText = stringResource(Res.string.refresh_action),
                onConfirm = { viewModel.loadData() }
            )
        }

        is TrainerHomeUiState.Content -> {
            val content = uiState as TrainerHomeUiState.Content
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TrainerHomeCompactComponent.TopBar(content, viewModel::onAction)
                },
                content = {
                    TrainerHomeCompactComponent.Content(content, viewModel::onAction)
                }
            )
        }
    }
}

private object TrainerHomeCompactComponent {

    fun TopBar(
        content: TrainerHomeUiState.Content,
        onAction: (TrainerHomeAction) -> Unit
    ) {

    }

    fun Content(
        content: TrainerHomeUiState.Content,
        onAction: (TrainerHomeAction) -> Unit
    ) {

    }
}