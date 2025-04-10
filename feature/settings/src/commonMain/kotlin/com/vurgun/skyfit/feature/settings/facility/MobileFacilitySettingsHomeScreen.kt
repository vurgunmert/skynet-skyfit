package com.vurgun.skyfit.feature.settings.facility

import androidx.compose.foundation.layout.Arrangement
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
import com.vurgun.skyfit.ui.core.components.menu.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.ui.core.components.menu.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.feature.settings.user.UserSettingsViewEvent
import com.vurgun.skyfit.feature.settings.user.UserSettingsViewModel
import com.vurgun.skyfit.ui.core.components.button.PrimaryLargeButton
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.branches_label
import skyfit.ui.core.generated.resources.ic_athletic_performance
import skyfit.ui.core.generated.resources.ic_bell
import skyfit.ui.core.generated.resources.ic_building
import skyfit.ui.core.generated.resources.ic_credit_card
import skyfit.ui.core.generated.resources.ic_posture_fill
import skyfit.ui.core.generated.resources.ic_profile
import skyfit.ui.core.generated.resources.ic_question_circle
import skyfit.ui.core.generated.resources.logout_action
import skyfit.ui.core.generated.resources.members_label
import skyfit.ui.core.generated.resources.settings_account_label
import skyfit.ui.core.generated.resources.settings_notifications_label
import skyfit.ui.core.generated.resources.settings_payment_history_label
import skyfit.ui.core.generated.resources.settings_support_label
import skyfit.ui.core.generated.resources.settings_title
import skyfit.ui.core.generated.resources.trainers_label

@Composable
fun MobileFacilitySettingsHomeScreen(
    goToBack: () -> Unit,
    goToLogin: () -> Unit,
    goToAccountSettings: () -> Unit,
    goToPaymentHistory: () -> Unit,
    goToNotifications: () -> Unit,
    goToManageTrainers: () -> Unit,
    goToManageMembers: () -> Unit,
    goToManageBranches: () -> Unit,
    goToSupport: () -> Unit,
) {

    val viewModel: UserSettingsViewModel = koinInject()

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                UserSettingsViewEvent.GoToLogin -> { goToLogin() }
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
                text = stringResource(Res.string.settings_account_label),
                iconRes = Res.drawable.ic_profile,
                onClick = goToAccountSettings
            )

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_payment_history_label),
                iconRes = Res.drawable.ic_credit_card,
                onClick = goToPaymentHistory
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_notifications_label),
                iconRes = Res.drawable.ic_bell,
                onClick = goToNotifications
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.members_label),
                iconRes = Res.drawable.ic_posture_fill,
                onClick = goToManageMembers
            )

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.trainers_label),
                iconRes = Res.drawable.ic_athletic_performance,
                onClick = goToManageTrainers
            )

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.branches_label),
                iconRes = Res.drawable.ic_building,
                onClick = goToManageBranches
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_support_label),
                iconRes = Res.drawable.ic_question_circle,
                onClick = goToSupport
            )
        }
    }
}