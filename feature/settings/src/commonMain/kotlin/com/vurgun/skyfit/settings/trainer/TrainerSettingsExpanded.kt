package com.vurgun.skyfit.settings.trainer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.screen.UnauthorizedAccessScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.settings.shared.helpsupport.SettingsSupportHelpScreen
import com.vurgun.skyfit.settings.trainer.notification.TrainerNotificationSettingsScreen
import com.vurgun.skyfit.settings.trainer.payment.TrainerPaymentHistorySettingsScreen
import com.vurgun.skyfit.settings.component.SettingsExpandedComponent
import com.vurgun.skyfit.settings.model.SettingsDestination
import com.vurgun.skyfit.settings.shared.SettingsUiEffect
import com.vurgun.skyfit.settings.shared.SettingsUiState
import com.vurgun.skyfit.settings.shared.SettingsViewModel
import com.vurgun.skyfit.settings.trainer.profile.TrainerAccountSettingsScreen

@Composable
fun TrainerSettingsExpanded(viewModel: SettingsViewModel) {

    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            SettingsUiEffect.NavigateToBack -> appNavigator.pop()
            SettingsUiEffect.NavigateToAuth -> appNavigator.replaceAll(SharedScreen.Splash)
            else -> {
                print("Effect is not handled: $effect")
            }
        }
    }

    when (uiState) {
        is SettingsUiState.Loading -> {
            FullScreenLoaderContent()
        }
        is SettingsUiState.Error -> {
            val message = (uiState as SettingsUiState.Error).message
            ErrorScreen(message = message, onConfirm = { appNavigator.pop() })
        }

        is SettingsUiState.Content -> {
            val content = (uiState as SettingsUiState.Content)

            val screen = when (content.destination) {
                SettingsDestination.Account -> TrainerAccountSettingsScreen()
                SettingsDestination.Notifications -> TrainerNotificationSettingsScreen()
                SettingsDestination.Payment -> TrainerPaymentHistorySettingsScreen()
                SettingsDestination.Support -> SettingsSupportHelpScreen()
                else -> UnauthorizedAccessScreen()
            }

            SettingsExpandedComponent.LandingContent(
                content = content,
                onAction = viewModel::onAction,
                container = { screen.Content() }
            )
        }
    }
}