package com.vurgun.skyfit.core.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

@Composable
fun UnauthorizedAccessScreen() {

    SkyFitMobileScaffold {

        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            BodyMediumRegularText(
                text = "UNAUTHORIZED SCREEN ACCESS",
                color = SkyFitColor.text.criticalActive
            )
        }
    }
}