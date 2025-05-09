package com.vurgun.skyfit.feature.persona.settings.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.menu.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.core.ui.components.menu.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsHomeViewModel
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsMainAction
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsMainEffect
import com.vurgun.skyfit.feature.persona.settings.shared.component.SettingsHomeAccountTypesColumn
import com.vurgun.skyfit.feature.persona.settings.shared.helpsupport.SettingsSupportHelpScreen
import com.vurgun.skyfit.feature.persona.settings.user.notification.UserSettingsNotificationsScreen
import com.vurgun.skyfit.feature.persona.settings.user.payment.UserSettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature.persona.settings.user.profile.UserSettingsManageProfileScreen
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_bell
import skyfit.core.ui.generated.resources.ic_credit_card
import skyfit.core.ui.generated.resources.ic_profile
import skyfit.core.ui.generated.resources.ic_question_circle
import skyfit.core.ui.generated.resources.logout_action
import skyfit.core.ui.generated.resources.settings_account_label
import skyfit.core.ui.generated.resources.settings_notifications_label
import skyfit.core.ui.generated.resources.settings_payment_history_label
import skyfit.core.ui.generated.resources.settings_support_label
import skyfit.core.ui.generated.resources.settings_title

class UserSettingsMainScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val settingsNavigator = LocalNavigator.currentOrThrow
        val viewModel: SettingsHomeViewModel = koinScreenModel()

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

        MobileUserSettingsHomeScreen(viewModel = viewModel)
    }
}

@Composable
private fun MobileUserSettingsHomeScreen(viewModel: SettingsHomeViewModel) {
    val accountTypes by viewModel.accountTypes.collectAsState()

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.settings_title),
                onClickBack = { viewModel.onAction(SettingsMainAction.NavigateToBack) }
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
                onClick = { viewModel.onAction(SettingsMainAction.NavigateToManageProfile) }
            )

            MobileSettingsMenuItemComponent(
                iconRes = Res.drawable.ic_credit_card,
                text = stringResource(Res.string.settings_payment_history_label),
                onClick = { viewModel.onAction(SettingsMainAction.NavigateToPaymentHistory) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                iconRes = Res.drawable.ic_bell,
                text = stringResource(Res.string.settings_notifications_label),
                onClick = { viewModel.onAction(SettingsMainAction.NavigateToNotifications) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                iconRes = Res.drawable.ic_question_circle,
                text = stringResource(Res.string.settings_support_label),
                onClick = { viewModel.onAction(SettingsMainAction.NavigateToSupport) }
            )

            SettingsHomeAccountTypesColumn(
                accounts = accountTypes,
                selectedTypeId = viewModel.selectedTypeId,
                onSelectType = viewModel::selectUserType
            )
        }
    }
}