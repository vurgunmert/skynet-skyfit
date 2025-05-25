package com.vurgun.skyfit.core.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.data.utility.Platform
import com.vurgun.skyfit.core.ui.components.special.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

class UnauthorizedAccessScreen(private val showBack: Boolean = false) : Screen {

    @Composable
    override fun Content() {
        SkyFitScaffold {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                //TODO: showBack

                BodyMediumRegularText(
                    text = "Yetkisiz ekran erişimi. Bu alana erişim izniniz bulunmamaktadır.",
                    color = SkyFitColor.text.criticalActive
                )
            }
        }
    }
}