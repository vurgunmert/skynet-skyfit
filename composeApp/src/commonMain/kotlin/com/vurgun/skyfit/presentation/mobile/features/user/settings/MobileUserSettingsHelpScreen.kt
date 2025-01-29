package com.vurgun.skyfit.presentation.mobile.features.user.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserSettingsHelpScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Destek ve Yardım", onBackClick = { navigator.popBackStack() })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = "Sık sorulan soruların yanıtlarını bulun veya doğrudan bizimle iletişime geçin",
                style = SkyFitTypography.bodyMediumMedium,
                color = SkyFitColor.text.secondary
            )
            Spacer(Modifier.height(32.dp))
            MobileSettingsMenuItemComponent("Sık Sorulan Sorular")
            Spacer(Modifier.height(32.dp))
            MobileSettingsMenuItemComponent("Chat")
            Spacer(Modifier.height(32.dp))
            MobileSettingsMenuItemComponent("Canlı Yardım")
        }
    }
}