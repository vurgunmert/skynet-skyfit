package com.vurgun.skyfit.feature_onboarding.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileOnboardingCompletedScreen(onClickContinue: () -> Unit) {
    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(68.dp))
            OnboardingTitleGroupComponent(
                title = "Hazırsınız \uD83D\uDE80",
                subtitle = "Vakit ayırdığınız için teşekkür ederiz uygulamayı sizin içn hazırlıyoruz"
            )
            Spacer(Modifier.height(24.dp))
            OnboardingCharacterComponent()
            Spacer(Modifier.weight(1f))
            SkyFitButtonComponent(
                modifier = Modifier.fillMaxWidth(), text = "Haydi Başla",
                onClick = onClickContinue,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = ButtonState.Rest
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
fun OnboardingCharacterComponent() {
    Image(
        painter = painterResource(Res.drawable.logo_skyfit),
        contentDescription = null,
        modifier = Modifier.size(386.dp, 378.dp)
    )
}