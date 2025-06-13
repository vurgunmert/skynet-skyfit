package com.vurgun.skyfit.feature.persona.settings.facility

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
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
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
import com.vurgun.skyfit.feature.persona.settings.facility.branch.FacilityBranchSettingsScreen
import com.vurgun.skyfit.feature.persona.settings.facility.member.FacilityMemberSettingsScreen
import com.vurgun.skyfit.feature.persona.settings.facility.notification.FacilityNotificationSettingsScreen
import com.vurgun.skyfit.feature.persona.settings.facility.packages.FacilityPackageSettingsScreen
import com.vurgun.skyfit.feature.persona.settings.facility.payment.FacilityPaymentHistorySettingsScreen
import com.vurgun.skyfit.feature.persona.settings.facility.profile.FacilitySettingsManageProfileScreen
import com.vurgun.skyfit.feature.persona.settings.facility.trainer.FacilityTrainerSettingsScreen
import com.vurgun.skyfit.feature.persona.settings.model.SettingsNavigationRoute
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsUiAction
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsUiEffect
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsUiState
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.shared.component.SettingsHomeAccountTypesColumn
import com.vurgun.skyfit.feature.persona.settings.shared.helpsupport.SettingsSupportHelpScreen
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
fun FacilitySettingsCompact(viewModel: SettingsViewModel) {
    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val settingsNavigator = LocalNavigator.currentOrThrow
    val accountTypes by viewModel.accountTypes.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            SettingsUiEffect.NavigateToBack ->
                appNavigator.pop()

            SettingsUiEffect.NavigateToSplash ->
                appNavigator.replaceAll(SharedScreen.Splash)

            is SettingsUiEffect.NavigateToRoute -> {
                val screen = when (effect.route) {
                    SettingsNavigationRoute.Account ->
                        FacilitySettingsManageProfileScreen()

                    SettingsNavigationRoute.PaymentHistory ->
                        FacilityPaymentHistorySettingsScreen()

                    SettingsNavigationRoute.Notifications ->
                        FacilityNotificationSettingsScreen()

                    SettingsNavigationRoute.Members ->
                        FacilityMemberSettingsScreen()

                    SettingsNavigationRoute.Trainers ->
                        FacilityTrainerSettingsScreen()

                    SettingsNavigationRoute.Branches ->
                        FacilityBranchSettingsScreen()

                    SettingsNavigationRoute.Support ->
                        SettingsSupportHelpScreen()

                    SettingsNavigationRoute.Packages ->
                        FacilityPackageSettingsScreen()
                }
                settingsNavigator.push(screen)
            }
        }
    }

    when (uiState) {
        is SettingsUiState.Loading -> FullScreenLoaderContent()
        is SettingsUiState.Error -> {
            val message = (uiState as SettingsUiState.Error).message
            ErrorScreen(message = message, onConfirm = { appNavigator.pop() })
        }

        is SettingsUiState.Content ->
            FacilitySettingsCompactComponents.Content(viewModel, accountTypes, viewModel::onAction)
    }
}

private object FacilitySettingsCompactComponents {
    @Composable
    fun Content(
        viewModel: SettingsViewModel,
        accountTypes: List<AccountType>,
        onAction: (SettingsUiAction) -> Unit,
    ) {

        SkyFitMobileScaffold(
            topBar = {
                SkyFitScreenHeader(
                    title = stringResource(Res.string.settings_title),
                    onClickBack = { onAction(SettingsUiAction.OnClickBack) }
                )
            },
            bottomBar = {
                PrimaryLargeButton(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    text = stringResource(Res.string.logout_action),
                    onClick = { onAction(SettingsUiAction.OnClickLogout) }
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
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Account)) }
                )

                MobileSettingsMenuItemComponent(
                    text = stringResource(Res.string.settings_payment_history_label),
                    iconRes = Res.drawable.ic_credit_card,
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.PaymentHistory)) }
                )


                MobileSettingsMenuItemComponent(
                    text = stringResource(Res.string.packages_label),
                    iconRes = Res.drawable.ic_package,
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Packages)) }
                )

                MobileSettingsMenuItemDividerComponent()

                MobileSettingsMenuItemComponent(
                    text = stringResource(Res.string.notifications_label),
                    iconRes = Res.drawable.ic_bell,
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Notifications)) }
                )

                MobileSettingsMenuItemDividerComponent()

                MobileSettingsMenuItemComponent(
                    text = stringResource(Res.string.members_label),
                    iconRes = Res.drawable.ic_posture_fill,
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Members)) }
                )

                MobileSettingsMenuItemComponent(
                    text = stringResource(Res.string.trainers_label),
                    iconRes = Res.drawable.ic_athletic_performance,
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Trainers)) }
                )

                MobileSettingsMenuItemComponent(
                    text = stringResource(Res.string.branches_label),
                    iconRes = Res.drawable.ic_building,
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Branches)) }
                )

                MobileSettingsMenuItemDividerComponent()

                MobileSettingsMenuItemComponent(
                    text = stringResource(Res.string.settings_support_label),
                    iconRes = Res.drawable.ic_question_circle,
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Support)) }
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














