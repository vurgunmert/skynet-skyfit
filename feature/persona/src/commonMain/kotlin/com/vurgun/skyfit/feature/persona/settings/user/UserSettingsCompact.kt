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
import com.vurgun.skyfit.feature.persona.settings.facility.member.FacilityMemberSettingsScreen
import com.vurgun.skyfit.feature.persona.settings.model.SettingsNavigationRoute
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsUiAction
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsUiEffect
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsUiState
import com.vurgun.skyfit.feature.persona.settings.shared.SettingsViewModel
import com.vurgun.skyfit.feature.persona.settings.shared.component.SettingsHomeAccountTypesColumn
import com.vurgun.skyfit.feature.persona.settings.shared.helpsupport.SettingsSupportHelpScreen
import com.vurgun.skyfit.feature.persona.settings.trainer.notification.TrainerNotificationSettingsScreen
import com.vurgun.skyfit.feature.persona.settings.trainer.payment.TrainerPaymentHistorySettingsScreen
import com.vurgun.skyfit.feature.persona.settings.trainer.profile.TrainerSettingsManageProfileScreen
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
internal fun UserSettingsCompact(viewModel: SettingsViewModel) {

    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val settingsNavigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            SettingsUiEffect.NavigateToBack -> appNavigator.pop()
            SettingsUiEffect.NavigateToSplash -> appNavigator.replaceAll(SharedScreen.Splash)

            is SettingsUiEffect.NavigateToRoute -> {
                val screen = when (effect.route) {
                    SettingsNavigationRoute.Account ->
                        TrainerSettingsManageProfileScreen()

                    SettingsNavigationRoute.PaymentHistory ->
                        TrainerPaymentHistorySettingsScreen()

                    SettingsNavigationRoute.Notifications ->
                        TrainerNotificationSettingsScreen()

                    SettingsNavigationRoute.Members ->
                        FacilityMemberSettingsScreen() // assuming shared for both trainer/facility

                    SettingsNavigationRoute.Support ->
                        SettingsSupportHelpScreen()

                    else -> null
                }

                screen?.let { settingsNavigator.push(it) } ?: appNavigator.pop()
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
            val content = uiState as SettingsUiState.Content
            UserSettingsCompactComponent.Content(content, viewModel::onAction)
        }

    }
}

private object UserSettingsCompactComponent {

    @Composable
    fun Content(
        content: SettingsUiState.Content,
        onAction: (SettingsUiAction) -> Unit = {}
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
                    iconRes = Res.drawable.ic_profile,
                    text = stringResource(Res.string.settings_account_label),
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Account)) }
                )

                MobileSettingsMenuItemComponent(
                    iconRes = Res.drawable.ic_credit_card,
                    text = stringResource(Res.string.settings_payment_history_label),
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.PaymentHistory)) }
                )

                MobileSettingsMenuItemDividerComponent()

                MobileSettingsMenuItemComponent(
                    iconRes = Res.drawable.ic_bell,
                    text = stringResource(Res.string.notifications_label),
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Notifications)) }
                )

                MobileSettingsMenuItemDividerComponent()

                MobileSettingsMenuItemComponent(
                    iconRes = Res.drawable.ic_question_circle,
                    text = stringResource(Res.string.settings_support_label),
                    onClick = { onAction(SettingsUiAction.OnChangeRoute(SettingsNavigationRoute.Support)) }
                )

                SettingsHomeAccountTypesColumn(
                    accounts = content.accountTypes,
                    selectedTypeId = content.selectedAccountTypeId,
                    onSelectType = { onAction(SettingsUiAction.ChangeUserType(it)) }
                )
            }
        }
    }
}