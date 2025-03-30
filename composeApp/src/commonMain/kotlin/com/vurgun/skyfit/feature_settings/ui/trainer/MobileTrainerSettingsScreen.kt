package com.vurgun.skyfit.feature_settings.ui.trainer

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
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.feature_settings.ui.user.UserSettingsViewEvent
import com.vurgun.skyfit.feature_settings.ui.user.UserSettingsViewModel
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
fun MobileTrainerSettingsScreen(navigator: Navigator) {

    val viewModel: UserSettingsViewModel = koinInject()

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collectLatest {
            when (it) {
                UserSettingsViewEvent.GoToLogin -> {
                    navigator.jumpAndTakeover(NavigationRoute.Login)
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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_account_label),
                iconRes = Res.drawable.ic_profile,
                onClick = { navigator.jumpAndStay(NavigationRoute.TrainerSettingsAccount) }
            )

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_payment_history_label),
                iconRes = Res.drawable.ic_credit_card,
                onClick = { navigator.jumpAndStay(NavigationRoute.TrainerSettingsPaymentHistory) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_notifications_label),
                iconRes = Res.drawable.ic_bell,
                onClick = { navigator.jumpAndStay(NavigationRoute.TrainerSettingsNotifications) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.settings_support_label),
                iconRes = Res.drawable.ic_question_circle,
                onClick = { navigator.jumpAndStay(NavigationRoute.TrainerSettingsHelp) }
            )
        }
    }
}