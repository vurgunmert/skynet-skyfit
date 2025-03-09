package com.vurgun.skyfit.feature_auth.ui.mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.SkyFitPasswordInputComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.SkyFitLogoComponent
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileForgotPasswordResetScreen(navigator: Navigator) {

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(36.dp))
            SkyFitLogoComponent()
            Spacer(Modifier.height(48.dp))
            MobileForgotPasswordResetScreenTitleComponent()
            Spacer(Modifier.height(48.dp))
            MobileForgotPasswordResetScreenInputGroupComponent()
            Spacer(Modifier.weight(1f))
            MobileForgotPasswordResetScreenActionsComponent(
                onClickContinue = {
                    navigator.jumpAndTakeover(NavigationRoute.ForgotPasswordReset, NavigationRoute.Dashboard)
                },
                onClickCancel = {}
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun MobileForgotPasswordResetScreenTitleComponent() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Şifre Sıfırla",
            style = SkyFitTypography.heading3
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Yeni şifreniz eskisinden farklı olmalıdır",
            style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
        )
    }
}

@Composable
private fun MobileForgotPasswordResetScreenInputGroupComponent() {
    var password by remember { mutableStateOf("") }
    var confirmedPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SkyFitPasswordInputComponent(
            hint = "Şifrenizi girin",
            value = password,
            onValueChange = { password = it }
        )
        Spacer(Modifier.height(16.dp))
        SkyFitPasswordInputComponent(
            hint = "Yeni şifrenizi tekrar girin",
            value = confirmedPassword,
            onValueChange = { confirmedPassword = it }
        )
    }
}

@Composable
private fun MobileForgotPasswordResetScreenActionsComponent(
    onClickContinue: () -> Unit,
    onClickCancel: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "Devam Et",
            onClick = onClickContinue,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
        Spacer(Modifier.height(14.dp))
        SkyFitButtonComponent(
            modifier = Modifier.fillMaxWidth(), text = "İptal",
            onClick = onClickCancel,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            state = ButtonState.Rest
        )
        Spacer(Modifier.height(44.dp))
    }
}