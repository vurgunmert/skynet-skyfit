package com.vurgun.skyfit.feature_auth.ui.mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitTextInputComponent
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_envelope_closed

@Composable
fun MobileForgotPasswordScreen(navigator: Navigator) {
    var email by remember { mutableStateOf("") }

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                .fillMaxHeight()
                .padding(SkyFitStyleGuide.Mobile.contentPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SkyFitLogoComponent()
            Spacer(Modifier.height(36.dp))
            MobileForgotPasswordScreenTitleComponent()
            Spacer(Modifier.height(48.dp))

            SkyFitTextInputComponent(
                hint = "Email’inizi girin",
                value = email,
                onValueChange = { email = it },
                leftIconPainter = painterResource(Res.drawable.ic_envelope_closed)
            )

            Spacer(Modifier.weight(1f))

            SkyFitButtonComponent(
                text = "Devam Et",
                modifier = Modifier.fillMaxWidth(),
                onClick = { navigator.jumpAndStay(NavigationRoute.ForgotPasswordCode) },
                variant = ButtonVariant.Primary,
            )
            Spacer(Modifier.height(14.dp))
            SkyFitButtonComponent(
                modifier = Modifier.fillMaxWidth(), text = "İptal",
                onClick = { navigator.popBackStack() },
                variant = ButtonVariant.Secondary
            )
        }
    }
}


@Composable
private fun MobileForgotPasswordScreenTitleComponent() {
    Text(
        text = "Şifremi Unuttum",
        style = SkyFitTypography.heading3
    )
    Spacer(Modifier.height(16.dp))
    Text(
        text = "Şifrenizi sıfırlamak için E-postanızı girin",
        style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
    )
}
