package com.vurgun.skyfit.presentation.mobile.features.user.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.settings.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.features.settings.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import moe.tlaster.precompose.navigation.Navigator
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_bell
import skyfit.composeapp.generated.resources.ic_credit_card
import skyfit.composeapp.generated.resources.ic_profile
import skyfit.composeapp.generated.resources.ic_question_circle

@Composable
fun MobileUserSettingsScreen(navigator: Navigator) {

    SkyFitScaffold(
        topBar = {
            SkyFitScreenHeader(title = "Ayarlar", onClickBack = { })
        },
        bottomBar = {

            SkyFitButtonComponent(
                modifier = Modifier.fillMaxWidth().padding(24.dp), text = "Oturumu Kapat",
                onClick = { },
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = ButtonState.Rest
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 32.dp, start = 32.dp, end = 22.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {

            MobileSettingsMenuItemComponent(
                text = "Hesap Ayarlari",
                iconRes = Res.drawable.ic_profile,
                onClick = { navigator.jumpAndStay(SkyFitNavigationRoute.UserSettingsAccount) }
            )

            MobileSettingsMenuItemComponent(
                text = "Ödeme Geçmişi",
                iconRes = Res.drawable.ic_credit_card,
                onClick = { navigator.jumpAndStay(SkyFitNavigationRoute.UserSettingsPaymentHistory) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = "Bildirimler",
                iconRes = Res.drawable.ic_bell,
                onClick = { navigator.jumpAndStay(SkyFitNavigationRoute.UserSettingsNotifications) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = "Destek ve Yardim",
                iconRes = Res.drawable.ic_question_circle,
                onClick = { navigator.jumpAndStay(SkyFitNavigationRoute.UserSettingsHelp) }
            )
        }
    }
}