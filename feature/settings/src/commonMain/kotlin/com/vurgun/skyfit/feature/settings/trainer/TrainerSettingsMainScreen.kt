package com.vurgun.skyfit.feature.settings.trainer

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
import com.vurgun.skyfit.feature.settings.facility.member.FacilityManageMembersScreen
import com.vurgun.skyfit.feature.settings.shared.SettingsHomeViewModel
import com.vurgun.skyfit.feature.settings.shared.SettingsMainAction
import com.vurgun.skyfit.feature.settings.shared.SettingsMainEffect
import com.vurgun.skyfit.feature.settings.shared.component.SettingsHomeAccountTypesColumn
import com.vurgun.skyfit.feature.settings.shared.helpsupport.SettingsSupportHelpScreen
import com.vurgun.skyfit.feature.settings.trainer.profile.TrainerSettingsManageProfileScreen
import com.vurgun.skyfit.feature.settings.trainer.notification.TrainerSettingsNotificationsScreen
import com.vurgun.skyfit.feature.settings.trainer.payment.TrainerSettingsPaymentHistoryScreen
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_bell
import skyfit.core.ui.generated.resources.ic_credit_card
import skyfit.core.ui.generated.resources.ic_posture
import skyfit.core.ui.generated.resources.ic_profile
import skyfit.core.ui.generated.resources.ic_question_circle
import skyfit.core.ui.generated.resources.logout_action
import skyfit.core.ui.generated.resources.members_label
import skyfit.core.ui.generated.resources.settings_account_label
import skyfit.core.ui.generated.resources.settings_notifications_label
import skyfit.core.ui.generated.resources.settings_payment_history_label
import skyfit.core.ui.generated.resources.settings_support_label
import skyfit.core.ui.generated.resources.settings_title

class TrainerSettingsMainScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val settingsNavigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SettingsHomeViewModel>()


        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                SettingsMainEffect.NavigateToBack -> appNavigator.pop()
                SettingsMainEffect.NavigateToSplash -> appNavigator.replaceAll(SharedScreen.Splash)
                SettingsMainEffect.NavigateToManageProfile -> settingsNavigator.push(TrainerSettingsManageProfileScreen())
                SettingsMainEffect.NavigateToPaymentHistory -> settingsNavigator.push(TrainerSettingsPaymentHistoryScreen())
                SettingsMainEffect.NavigateToNotifications -> settingsNavigator.push(TrainerSettingsNotificationsScreen())
                SettingsMainEffect.NavigateToManageMembers -> settingsNavigator.push(FacilityManageMembersScreen())
                SettingsMainEffect.NavigateToSupport -> settingsNavigator.push(SettingsSupportHelpScreen())
                else -> appNavigator.pop()
            }
        }

        MobileTrainerSettingsHomeScreen(viewModel = viewModel)
    }
}

@Composable
private fun MobileTrainerSettingsHomeScreen(viewModel: SettingsHomeViewModel) {

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
                text = stringResource(Res.string.settings_account_label),
                iconRes = Res.drawable.ic_profile,
                onClick = { viewModel.onAction(SettingsMainAction.NavigateToManageProfile) }
            )

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_payment_history_label),
                iconRes = Res.drawable.ic_credit_card,
                onClick = { viewModel.onAction(SettingsMainAction.NavigateToPaymentHistory) }
            )

//            MobileSettingsMenuItemDividerComponent()
//
//            MobileSettingsMenuItemComponent(
//                text = stringResource(Res.string.members_label),
//                iconRes = Res.drawable.ic_posture,
//                onClick = { viewModel.onAction(SettingsMainAction.NavigateToManageMembers) }
//            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_notifications_label),
                iconRes = Res.drawable.ic_bell,
                onClick = { viewModel.onAction(SettingsMainAction.NavigateToNotifications) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_support_label),
                iconRes = Res.drawable.ic_question_circle,
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