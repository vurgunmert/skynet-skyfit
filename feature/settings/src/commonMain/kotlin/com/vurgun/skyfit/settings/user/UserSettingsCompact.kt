package com.vurgun.skyfit.settings.user

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
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.settings.shared.helpsupport.SettingsSupportHelpScreen
import com.vurgun.skyfit.settings.user.account.UserAccountSettingsScreen
import com.vurgun.skyfit.settings.user.notification.UserNotificationSettingsScreen
import com.vurgun.skyfit.settings.user.payment.UserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.settings.component.SettingsCompactComponent
import com.vurgun.skyfit.settings.shared.SettingsUiEffect
import com.vurgun.skyfit.settings.shared.SettingsUiState
import com.vurgun.skyfit.settings.shared.SettingsViewModel

@Composable
internal fun UserSettingsCompact(viewModel: SettingsViewModel) {

    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val settingsNavigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            SettingsUiEffect.NavigateToBack -> appNavigator.pop()
            SettingsUiEffect.NavigateToAuth -> appNavigator.replaceAll(SharedScreen.Splash)
            SettingsUiEffect.NavigateToAccount -> settingsNavigator.push(UserAccountSettingsScreen())
            SettingsUiEffect.NavigateToNotificationSettings -> settingsNavigator.push(UserNotificationSettingsScreen())
            SettingsUiEffect.NavigateToPaymentSettings -> settingsNavigator.push(UserSettingsPaymentHistoryScreen())
            SettingsUiEffect.NavigateToSupportSettings -> settingsNavigator.push(SettingsSupportHelpScreen())
            else -> {
                print("Effect is not handled: $effect")
            }
        }
    }

    when (uiState) {
        is SettingsUiState.Loading -> FullScreenLoaderContent()
        is SettingsUiState.Error -> {
            val message = (uiState as SettingsUiState.Error).message
            ErrorScreen(message = message, onConfirm = { appNavigator.pop() })
        }

        is SettingsUiState.Content -> {
            val content = (uiState as SettingsUiState.Content)
            SettingsCompactComponent.LandingContent(content, viewModel::onAction)
        }
    }
}