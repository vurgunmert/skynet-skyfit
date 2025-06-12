package com.vurgun.skyfit.feature.home.screen.facility

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.home.component.DefaultCompactTopBar
import com.vurgun.skyfit.feature.home.model.FacilityHomeAction
import com.vurgun.skyfit.feature.home.model.FacilityHomeEffect
import com.vurgun.skyfit.feature.home.model.FacilityHomeUiState
import com.vurgun.skyfit.feature.home.model.FacilityHomeViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.refresh_action

@Composable
internal fun FacilityHomeCompact(viewModel: FacilityHomeViewModel) {

    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {

            is FacilityHomeEffect.ShowOverlay -> {
                appNavigator.push(effect.screen)
            }

            FacilityHomeEffect.NavigateToManageLessons -> {
                appNavigator.push(SharedScreen.FacilityManageLessons)
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
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    FacilityHomeCompactComponent.TopBar(content, viewModel::onAction)
                },
                content = {
                    FacilityHomeCompactComponent.Content(content, viewModel::onAction)
                }
            )
        }
    }
}

private object FacilityHomeCompactComponent {

    @Composable
    fun TopBar(
        content: FacilityHomeUiState.Content,
        onAction: (FacilityHomeAction) -> Unit
    ) {
        DefaultCompactTopBar(
            state = content.topBarState,
            onNavigate = { onAction(FacilityHomeAction.OnOverlayRequest(it)) },
        )
    }

    @Composable
    fun Content(
        content: FacilityHomeUiState.Content,
        onAction: (FacilityHomeAction) -> Unit
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