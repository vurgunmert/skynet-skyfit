package com.vurgun.skyfit.core.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.SkyFitColor

class UnderDevelopmentScreen(
    val title: String = "🚧 Özellik Geliştiriliyor",
    val message: String = "Bu özellik şu anda geliştirme aşamasında"
) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        SkyFitMobileScaffold(
            topBar = {
                SkyFitScreenHeader(
                    title = title,
                    onClickBack = { navigator.pop() }
                )
            }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {
                    SkyText(
                        text = message,
                        styleType = TextStyleType.BodyLargeSemibold,
                        color = SkyFitColor.text.criticalOnBgFill
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    SkyText(
                        text = "👨‍💻 Ekip olarak üzerinde çalışıyoruz. Kısa süre içinde erişime açılacak!",
                        styleType = TextStyleType.BodyMediumRegular,
                        color = SkyFitColor.text.warningActive
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    SkyText(
                        text = "📲 Lütfen uygulamanın en güncel sürümünü kullandığınızdan emin olun.",
                        styleType = TextStyleType.BodyMediumRegular,
                        color = SkyFitColor.text.warningActive
                    )
                }
            }
        }
    }
}
