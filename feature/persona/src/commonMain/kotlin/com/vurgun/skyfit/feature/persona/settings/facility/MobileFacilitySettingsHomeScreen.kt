package com.vurgun.skyfit.feature.persona.settings.facility

import androidx.compose.foundation.layout.*
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
import com.vurgun.skyfit.core.data.persona.domain.model.UserAccountType
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
import com.vurgun.skyfit.feature.persona.settings.facility.branch.FacilitySettingsManageBranchesScreen
import com.vurgun.skyfit.feature.persona.settings.facility.member.FacilityManageMembersScreen
import com.vurgun.skyfit.feature.persona.settings.facility.notification.FacilitySettingsNotificationsScreen
import com.vurgun.skyfit.feature.persona.settings.facility.packages.FacilityPackageListingScreen
import com.vurgun.skyfit.feature.persona.settings.facility.payment.FacilitySettingsPaymentHistoryScreen
import com.vurgun.skyfit.feature.persona.settings.facility.profile.FacilitySettingsManageProfileScreen
import com.vurgun.skyfit.feature.persona.settings.facility.trainer.FacilityManageTrainersScreen
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsHomeUiState
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsHomeViewModel
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsMainAction
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsMainEffect
import com.vurgun.skyfit.feature.persona.settings.shared.component.SettingsHomeAccountTypesColumn
import com.vurgun.skyfit.feature.persona.settings.shared.helpsupport.SettingsSupportHelpScreen
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class FacilitySettingsMainScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val settingsNavigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<SettingsHomeViewModel>()
        val accountTypes by viewModel.accountTypes.collectAsState()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                SettingsMainEffect.NavigateToBack ->
                    appNavigator.pop()

                SettingsMainEffect.NavigateToSplash ->
                    appNavigator.replaceAll(SharedScreen.Splash)

                SettingsMainEffect.NavigateToManageProfile ->
                    settingsNavigator.push(FacilitySettingsManageProfileScreen())

                SettingsMainEffect.NavigateToPaymentHistory ->
                    settingsNavigator.push(FacilitySettingsPaymentHistoryScreen())

                SettingsMainEffect.NavigateToNotifications ->
                    settingsNavigator.push(FacilitySettingsNotificationsScreen())

                SettingsMainEffect.NavigateToManageMembers ->
                    settingsNavigator.push(FacilityManageMembersScreen())

                SettingsMainEffect.NavigateToManageTrainers ->
                    settingsNavigator.push(FacilityManageTrainersScreen())

                SettingsMainEffect.NavigateToManageBranches ->
                    settingsNavigator.push(FacilitySettingsManageBranchesScreen())

                SettingsMainEffect.NavigateToSupport ->
                    settingsNavigator.push(SettingsSupportHelpScreen())

                SettingsMainEffect.NavigateToPackages ->
                    settingsNavigator.push(FacilityPackageListingScreen())

            }
        }

        when (uiState) {
            is SettingsHomeUiState.Loading -> FullScreenLoaderContent()
            is SettingsHomeUiState.Error -> {
                val message = (uiState as SettingsHomeUiState.Error).message
                ErrorScreen(message = message, onConfirm = { appNavigator.pop() })
            }

            is SettingsHomeUiState.Content ->
                MobileFacilitySettingsHomeScreen(viewModel, accountTypes, viewModel::onAction)
        }
    }
}

@Composable
private fun MobileFacilitySettingsHomeScreen(
    viewModel: SettingsHomeViewModel,
    accountTypes: List<UserAccountType>,
    onAction: (SettingsMainAction) -> Unit,
) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.settings_title),
                onClickBack = { onAction(SettingsMainAction.OnClickBack) }
            )
        },
        bottomBar = {
            PrimaryLargeButton(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                text = stringResource(Res.string.logout_action),
                onClick = { onAction(SettingsMainAction.OnClickLogout) }
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
                onClick = { onAction(SettingsMainAction.OnClickManageProfile) }
            )

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_payment_history_label),
                iconRes = Res.drawable.ic_credit_card,
                onClick = { onAction(SettingsMainAction.OnClickPaymentHistory) }
            )


            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.packages_label),
                iconRes = Res.drawable.ic_package,
                onClick = { onAction(SettingsMainAction.OnClickPackages) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.notifications_label),
                iconRes = Res.drawable.ic_bell,
                onClick = { onAction(SettingsMainAction.OnClickNotifications) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.members_label),
                iconRes = Res.drawable.ic_posture_fill,
                onClick = { onAction(SettingsMainAction.OnClickManageMembers) }
            )

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.trainers_label),
                iconRes = Res.drawable.ic_athletic_performance,
                onClick = { onAction(SettingsMainAction.OnClickManageTrainers) }
            )

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.branches_label),
                iconRes = Res.drawable.ic_building,
                onClick = { onAction(SettingsMainAction.OnClickManageBranches) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_support_label),
                iconRes = Res.drawable.ic_question_circle,
                onClick = { onAction(SettingsMainAction.OnClickSupport) }
            )

            SettingsHomeAccountTypesColumn(
                accounts = accountTypes,
                selectedTypeId = viewModel.selectedTypeId,
                onSelectType = viewModel::selectUserType
            )
        }
    }
}












