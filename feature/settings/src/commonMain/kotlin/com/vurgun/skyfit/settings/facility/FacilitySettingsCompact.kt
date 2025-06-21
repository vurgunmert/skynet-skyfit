package com.vurgun.skyfit.settings.facility

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
import com.vurgun.skyfit.feature.persona.settings.facility.notification.FacilityNotificationSettingsScreen
import com.vurgun.skyfit.settings.shared.helpsupport.SettingsSupportHelpScreen
import com.vurgun.skyfit.settings.component.SettingsCompactComponent
import com.vurgun.skyfit.settings.facility.account.FacilityAccountSettingsScreen
import com.vurgun.skyfit.settings.facility.branch.FacilityBranchSettingsScreen
import com.vurgun.skyfit.settings.facility.member.FacilityMemberSettingsScreen
import com.vurgun.skyfit.settings.facility.packages.FacilityPackageSettingsScreen
import com.vurgun.skyfit.settings.facility.payment.FacilityPaymentHistorySettingsScreen
import com.vurgun.skyfit.settings.facility.trainer.FacilityTrainerSettingsScreen
import com.vurgun.skyfit.settings.shared.SettingsUiEffect
import com.vurgun.skyfit.settings.shared.SettingsUiState
import com.vurgun.skyfit.settings.shared.SettingsViewModel

@Composable
fun FacilitySettingsCompact(viewModel: SettingsViewModel) {
    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val settingsNavigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            SettingsUiEffect.NavigateToBack -> appNavigator.pop()
            SettingsUiEffect.NavigateToAuth -> appNavigator.replaceAll(SharedScreen.Splash)
            SettingsUiEffect.NavigateToAccount -> settingsNavigator.push(FacilityAccountSettingsScreen())
            SettingsUiEffect.NavigateToBranchSettings -> settingsNavigator.push(FacilityBranchSettingsScreen())
            SettingsUiEffect.NavigateToLessonPackageSettings -> settingsNavigator.push(FacilityPackageSettingsScreen())
            SettingsUiEffect.NavigateToMemberSettings -> settingsNavigator.push(FacilityMemberSettingsScreen())
            SettingsUiEffect.NavigateToTrainerSettings -> settingsNavigator.push(FacilityTrainerSettingsScreen())
            SettingsUiEffect.NavigateToNotificationSettings -> settingsNavigator.push(FacilityNotificationSettingsScreen())
            SettingsUiEffect.NavigateToPaymentSettings -> settingsNavigator.push(FacilityPaymentHistorySettingsScreen())
            SettingsUiEffect.NavigateToSupportSettings -> settingsNavigator.push(SettingsSupportHelpScreen())
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














