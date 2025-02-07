package com.vurgun.skyfit.presentation.mobile.features.user.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.features.settings.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.features.settings.MobileSettingsMenuItemDividerComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import moe.tlaster.precompose.navigation.Navigator

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
        MobileUserSettingsScreenOptionsComponent()
    }
}

@Composable
private fun MobileUserSettingsScreenOptionsComponent() {
    Column(Modifier.fillMaxWidth().padding(top = 32.dp, start = 32.dp, end = 22.dp)) {

        MobileSettingsMenuItemComponent("Hesap Ayarlari")
        Spacer(Modifier.height(32.dp))
        MobileSettingsMenuItemComponent("Ödeme Geçmişi")
        Spacer(Modifier.height(32.dp))
        MobileSettingsMenuItemDividerComponent()
        Spacer(Modifier.height(32.dp))
        MobileSettingsMenuItemComponent("Bildirimler")
        Spacer(Modifier.height(32.dp))
        MobileSettingsMenuItemDividerComponent()
        Spacer(Modifier.height(32.dp))
        MobileSettingsMenuItemComponent("Destek ve Yardim")
    }
}