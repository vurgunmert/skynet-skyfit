package com.vurgun.skyfit.feature.settings.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vurgun.skyfit.feature.settings.component.SettingsHomeAccountTypesColumn
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.menu.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.core.ui.components.menu.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
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

@Composable
fun MobileUserSettingsHomeScreen(
    goToBack: () -> Unit,
    goToLogin: () -> Unit,
    goToAccount: () -> Unit,
    goToPaymentHistory: () -> Unit,
    goToNotifications: () -> Unit,
    goToHelp: () -> Unit,
    viewModel: SettingsHomeViewModel = koinViewModel<SettingsHomeViewModel>()
) {
    val accountTypes by viewModel.accountTypes.collectAsStateWithLifecycle()

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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            MobileSettingsMenuItemComponent(
                iconRes = Res.drawable.ic_profile,
                text = stringResource(Res.string.settings_account_label),
                onClick = goToAccount
            )

            MobileSettingsMenuItemComponent(
                iconRes = Res.drawable.ic_credit_card,
                text = stringResource(Res.string.settings_payment_history_label),
                onClick = goToPaymentHistory
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                iconRes = Res.drawable.ic_bell,
                text = stringResource(Res.string.settings_notifications_label),
                onClick = goToNotifications
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                iconRes = Res.drawable.ic_question_circle,
                text = stringResource(Res.string.settings_support_label),
                onClick = goToHelp
            )

            SettingsHomeAccountTypesColumn(
                accounts = accountTypes,
                selectedTypeId = viewModel.selectedTypeId,
                onSelectType = viewModel::selectUserType
            )
        }
    }
}