package com.vurgun.skyfit.feature.access.maintenance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyMediumRegularText
import com.vurgun.skyfit.core.ui.styling.LocalPadding

class MaintenanceScreen : Screen {

    @Composable
    override fun Content() {
        SkyFitMobileScaffold {
            Box(
                modifier = Modifier
                    .padding(LocalPadding.current.medium)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                BodyMediumRegularText(
                    text = "Spor yapmadan önce biraz esnemek gerekir… Biz de aynısını yapıyoruz. \uD83E\uDD38\n" +
                            "Uygulama şu an minik bir bakımda.\n" +
                            "Ne zaman döneceğiz mi?\n" +
                            "Sürpriz! Ama çok bekletmeyeceğiz. \uD83D\uDE09\n" +
                            "Bu sırada su içmeyi unutma! \uD83D\uDCA7"
                )
            }
        }
    }
}