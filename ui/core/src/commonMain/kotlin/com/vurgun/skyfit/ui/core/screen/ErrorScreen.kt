package com.vurgun.skyfit.ui.core.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.ui.core.components.text.BodyMediumRegularText

@Composable
fun ErrorScreen(message: String?, onBack: () -> Unit) {

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader("Hata", onClickBack = onBack)
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BodyMediumRegularText(text = message ?: "Default Error")
        }
    }
}