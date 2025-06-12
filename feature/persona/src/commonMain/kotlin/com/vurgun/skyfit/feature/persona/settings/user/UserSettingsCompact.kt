package com.vurgun.skyfit.feature.persona.settings.user

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.menu.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.core.ui.components.menu.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsHomeUiState
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsMainAction
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsMainEffect
import com.vurgun.skyfit.feature.persona.settings.shared.component.SettingsHomeAccountTypesColumn
import com.vurgun.skyfit.feature.persona.settings.shared.helpsupport.SettingsSupportHelpScreen
import com.vurgun.skyfit.feature.persona.settings.user.notification.UserSettingsNotificationsScreen
import com.vurgun.skyfit.feature.persona.settings.user.payment.UserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature.persona.settings.user.profile.UserSettingsManageProfileScreen
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
internal fun UserSettingsCompact(viewModel: UserSettingViewModel) {

    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val settingsNavigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            SettingsMainEffect.NavigateToBack -> appNavigator.pop()
            SettingsMainEffect.NavigateToSplash -> appNavigator.replaceAll(SharedScreen.Splash)
            SettingsMainEffect.NavigateToManageProfile -> settingsNavigator.push(UserSettingsManageProfileScreen())
            SettingsMainEffect.NavigateToPaymentHistory -> settingsNavigator.push(UserSettingsPaymentHistoryScreen())
            SettingsMainEffect.NavigateToNotifications -> settingsNavigator.push(UserSettingsNotificationsScreen())
            SettingsMainEffect.NavigateToSupport -> settingsNavigator.push(SettingsSupportHelpScreen())
            else -> appNavigator.pop()
        }
    }

    when (uiState) {
        is SettingsHomeUiState.Loading -> FullScreenLoaderContent()
        is SettingsHomeUiState.Error -> {
            val message = (uiState as SettingsHomeUiState.Error).message
            ErrorScreen(message = message, onConfirm = { appNavigator.pop() })
        }

        is SettingsHomeUiState.Content -> {
            val content = uiState as SettingsHomeUiState.Content
            UserSettingsCompactComponent.Content(content: )
        }

    }
}

private object UserSettingsCompactComponent {


    @Composable
    private fun Content(
        content: UserSettingsUiState.Content,
        onAction: (UserSettingsUiAction) -> Unit = {}
    ) {
        val accountTypes by viewModel.accountTypes.collectAsState()

        SkyFitMobileScaffold(
            topBar = {
                SkyFitScreenHeader(
                    title = stringResource(Res.string.settings_title),
                    onClickBack = { viewModel.onAction(SettingsMainAction.OnClickBack) }
                )
            },
            bottomBar = {
                PrimaryLargeButton(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    text = stringResource(Res.string.logout_action),
                    onClick = viewModel::onLogout
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 21.dp, end = 11.dp, top = 24.dp, bottom = 96.dp)
                    .fillMaxSize()
                    .padding(12.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {

                MobileSettingsMenuItemComponent(
                    iconRes = Res.drawable.ic_profile,
                    text = stringResource(Res.string.settings_account_label),
                    onClick = { viewModel.onAction(SettingsMainAction.OnClickManageProfile) }
                )

                MobileSettingsMenuItemComponent(
                    iconRes = Res.drawable.ic_credit_card,
                    text = stringResource(Res.string.settings_payment_history_label),
                    onClick = { viewModel.onAction(SettingsMainAction.OnClickPaymentHistory) }
                )

                MobileSettingsMenuItemDividerComponent()

                MobileSettingsMenuItemComponent(
                    iconRes = Res.drawable.ic_bell,
                    text = stringResource(Res.string.notifications_label),
                    onClick = { viewModel.onAction(SettingsMainAction.OnClickNotifications) }
                )

                MobileSettingsMenuItemDividerComponent()

                MobileSettingsMenuItemComponent(
                    iconRes = Res.drawable.ic_question_circle,
                    text = stringResource(Res.string.settings_support_label),
                    onClick = { viewModel.onAction(SettingsMainAction.OnClickSupport) }
                )

                SettingsHomeAccountTypesColumn(
                    accounts = accountTypes,
                    selectedTypeId = viewModel.selectedTypeId,
                    onSelectType = viewModel::selectUserType
                )
            }
        }
    }
}