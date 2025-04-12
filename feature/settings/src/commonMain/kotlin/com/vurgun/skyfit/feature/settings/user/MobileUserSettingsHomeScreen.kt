package com.vurgun.skyfit.feature.settings.user

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
import com.vurgun.skyfit.ui.core.components.menu.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.ui.core.components.menu.SettingsMenuItem
import com.vurgun.skyfit.ui.core.components.button.PrimaryLargeButton
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_bell
import skyfit.ui.core.generated.resources.ic_credit_card
import skyfit.ui.core.generated.resources.ic_profile
import skyfit.ui.core.generated.resources.ic_question_circle
import skyfit.ui.core.generated.resources.logout_action
import skyfit.ui.core.generated.resources.settings_account_label
import skyfit.ui.core.generated.resources.settings_notifications_label
import skyfit.ui.core.generated.resources.settings_payment_history_label
import skyfit.ui.core.generated.resources.settings_support_label
import skyfit.ui.core.generated.resources.settings_title

@Composable
fun MobileUserSettingsHomeScreen(
    goToBack: () -> Unit,
    goToLogin: () -> Unit,
    goToAccount: () -> Unit,
    goToPaymentHistory: () -> Unit,
    goToNotifications: () -> Unit,
    goToHelp: () -> Unit,
) {

    val viewModel: SettingsHomeViewModel = koinInject()

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                UserSettingsViewEvent.GoToLogin -> {
                    goToLogin()
                }
            }
        }
    }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(title = stringResource(Res.string.settings_title), onClickBack = goToBack)
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
                onClick = goToAccount
            )

            SettingsMenuItem(
                iconRes = Res.drawable.ic_credit_card,
                text = stringResource(Res.string.settings_payment_history_label),
                onClick = goToPaymentHistory
            )

            MobileSettingsMenuItemDividerComponent()

            SettingsMenuItem(
                iconRes = Res.drawable.ic_bell,
                text = stringResource(Res.string.settings_notifications_label),
                onClick = goToNotifications
            )

            MobileSettingsMenuItemDividerComponent()

            SettingsMenuItem(
                iconRes = Res.drawable.ic_question_circle,
                text = stringResource(Res.string.settings_support_label),
                onClick = goToHelp
            )
        }
    }
}