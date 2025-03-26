package com.vurgun.skyfit.feature_settings.ui.trainer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemDividerComponent
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.account_settings
import skyfit.composeapp.generated.resources.action_logout
import skyfit.composeapp.generated.resources.ic_bell
import skyfit.composeapp.generated.resources.ic_credit_card
import skyfit.composeapp.generated.resources.ic_profile
import skyfit.composeapp.generated.resources.ic_question_circle
import skyfit.composeapp.generated.resources.notifications
import skyfit.composeapp.generated.resources.payment_history
import skyfit.composeapp.generated.resources.settings
import skyfit.composeapp.generated.resources.support_and_assistance

@Composable
fun MobileTrainerSettingsScreen(navigator: Navigator) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(title = stringResource(Res.string.settings), onClickBack = { navigator.popBackStack() })
        },
        bottomBar = {
            PrimaryLargeButton(
                modifier = Modifier.fillMaxWidth().padding(24.dp),
                text = stringResource(Res.string.action_logout),
                onClick = { }
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
                text = stringResource(Res.string.account_settings),
                iconRes = Res.drawable.ic_profile,
                onClick = { navigator.jumpAndStay(NavigationRoute.TrainerSettingsAccount) }
            )

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.payment_history),
                iconRes = Res.drawable.ic_credit_card,
                onClick = { navigator.jumpAndStay(NavigationRoute.TrainerSettingsPaymentHistory) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.notifications),
                iconRes = Res.drawable.ic_bell,
                onClick = { navigator.jumpAndStay(NavigationRoute.TrainerSettingsNotifications) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = stringResource(Res.string.support_and_assistance),
                iconRes = Res.drawable.ic_question_circle,
                onClick = { navigator.jumpAndStay(NavigationRoute.TrainerSettingsHelp) }
            )
        }
    }
}