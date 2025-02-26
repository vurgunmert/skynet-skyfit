package com.vurgun.skyfit.feature_settings.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScreenHeader
import com.vurgun.skyfit.feature_settings.ui.MobileSettingsMenuItemComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_chat
import skyfit.composeapp.generated.resources.ic_phone
import skyfit.composeapp.generated.resources.ic_question_circle

@Composable
fun MobileUserSettingsHelpScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Destek ve Yardım", onClickBack = { navigator.popBackStack() })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "Sık sorulan soruların yanıtlarını bulun veya doğrudan bizimle iletişime geçin",
                style = SkyFitTypography.bodyMediumMedium,
                color = SkyFitColor.text.secondary,
                textAlign = TextAlign.Center
            )

            MobileSettingsMenuItemComponent(
                text = "Sık Sorulan Sorular",
                iconRes = Res.drawable.ic_question_circle,
                onClick = { }
            )

            MobileSettingsMenuItemComponent(
                text = "Chat",
                iconRes = Res.drawable.ic_chat,
                onClick = { }
            )

            MobileSettingsMenuItemComponent(
                text = "Canlı Yardım",
                iconRes = Res.drawable.ic_phone,
                onClick = { }
            )
        }
    }
}