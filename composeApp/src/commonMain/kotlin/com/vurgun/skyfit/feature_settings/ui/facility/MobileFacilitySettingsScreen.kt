package com.vurgun.skyfit.feature_settings.ui.facility

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import moe.tlaster.precompose.navigation.Navigator
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_athletic_performance
import skyfit.composeapp.generated.resources.ic_bell
import skyfit.composeapp.generated.resources.ic_credit_card
import skyfit.composeapp.generated.resources.ic_posture_fill
import skyfit.composeapp.generated.resources.ic_profile
import skyfit.composeapp.generated.resources.ic_question_circle

@Composable
fun MobileFacilitySettingsScreen(navigator: Navigator) {

    SkyFitScaffold(
        topBar = {
            SkyFitScreenHeader(title = "Ayarlar", onClickBack = { navigator.popBackStack() })
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
                onClick = { navigator.jumpAndStay(NavigationRoute.FacilitySettingsAccount) }
            )

            MobileSettingsMenuItemComponent(
                text = "Ödeme Geçmişi",
                iconRes = Res.drawable.ic_credit_card,
                onClick = { navigator.jumpAndStay(NavigationRoute.FacilitySettingsPaymentHistory) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = "Bildirimler",
                iconRes = Res.drawable.ic_bell,
                onClick = { navigator.jumpAndStay(NavigationRoute.FacilitySettingsNotifications) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = "Üyeler",
                iconRes = Res.drawable.ic_posture_fill,
                onClick = { navigator.jumpAndStay(NavigationRoute.FacilitySettingsSearchMembers) }
            )


            MobileSettingsMenuItemComponent(
                text = "Eğitmenler",
                iconRes = Res.drawable.ic_athletic_performance,
                onClick = { navigator.jumpAndStay(NavigationRoute.FacilitySettingsTrainers) }
            )

            MobileSettingsMenuItemDividerComponent()

            MobileSettingsMenuItemComponent(
                text = "Destek ve Yardim",
                iconRes = Res.drawable.ic_question_circle,
                onClick = { navigator.jumpAndStay(NavigationRoute.FacilitySettingsHelp) }
            )
        }
    }
}