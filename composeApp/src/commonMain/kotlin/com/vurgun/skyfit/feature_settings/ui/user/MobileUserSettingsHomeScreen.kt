package com.vurgun.skyfit.feature_settings.ui.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.feature_settings.ui.SettingsMenuItem
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_bell
import skyfit.composeapp.generated.resources.ic_credit_card
import skyfit.composeapp.generated.resources.ic_profile
import skyfit.composeapp.generated.resources.ic_question_circle
import skyfit.composeapp.generated.resources.logout_action
import skyfit.composeapp.generated.resources.settings_account_label
import skyfit.composeapp.generated.resources.settings_notifications_label
import skyfit.composeapp.generated.resources.settings_payment_history_label
import skyfit.composeapp.generated.resources.settings_support_label
import skyfit.composeapp.generated.resources.settings_title

@Composable
fun MobileUserSettingsHomeScreen(navigator: Navigator) {

    val viewModel: UserSettingsViewModel = koinInject()

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                UserSettingsViewEvent.GoToLogin -> {
                    navigator.jumpAndTakeover(MobileNavRoute.Login)
                }
            }
        }
    }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(title = stringResource(Res.string.settings_title), onClickBack = { navigator.popBackStack() })
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
                .verticalScroll(rememberScrollState())
        ) {

            SettingsMenuItem(
                iconRes = Res.drawable.ic_profile,
                text = stringResource(Res.string.settings_account_label),
                onClick = { navigator.jumpAndStay(MobileNavRoute.Settings.User.Account) }
            )

            SettingsMenuItem(
                iconRes = Res.drawable.ic_credit_card,
                text = stringResource(Res.string.settings_payment_history_label),
                onClick = { navigator.jumpAndStay(MobileNavRoute.Settings.User.PaymentHistory) }
            )

            MobileSettingsMenuItemDividerComponent()

            SettingsMenuItem(
                iconRes = Res.drawable.ic_bell,
                text = stringResource(Res.string.settings_notifications_label),
                onClick = { navigator.jumpAndStay(MobileNavRoute.Settings.User.Notifications) }
            )

            MobileSettingsMenuItemDividerComponent()

            SettingsMenuItem(
                iconRes = Res.drawable.ic_question_circle,
                text = stringResource(Res.string.settings_support_label),
                onClick = { navigator.jumpAndStay(MobileNavRoute.Settings.User.Help) }
            )
        }
    }
}