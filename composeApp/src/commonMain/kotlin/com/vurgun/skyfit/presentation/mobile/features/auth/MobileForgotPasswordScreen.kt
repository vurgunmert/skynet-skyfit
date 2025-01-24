package com.vurgun.skyfit.presentation.mobile.features.auth

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
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitLogoComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextInputComponent
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileForgotPasswordScreen(navigator: Navigator) {

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(36.dp))
            SkyFitLogoComponent()
            Spacer(Modifier.height(48.dp))
            MobileForgotPasswordScreenTitleComponent()
            Spacer(Modifier.height(48.dp))
            MobileForgotPasswordScreenInputComponent()
            Spacer(Modifier.weight(1f))
            MobileForgotPasswordScreenActionsComponent(
                onClickContinue = {
                    navigator.jumpAndStay(SkyFitNavigationRoute.ForgotPasswordCode)
                },
                onClickCancel = {
                    navigator.jumpAndTakeover(SkyFitNavigationRoute.ForgotPasswordCode, SkyFitNavigationRoute.Login)
                }
            )
            Spacer(Modifier.height(48.dp))
        }
    }
}

@Composable
private fun MobileForgotPasswordScreenInputComponent() {
    var email by remember { mutableStateOf("") }

    SkyFitTextInputComponent(
        hint = "Email’inizi girin",
        value = email,
        onValueChange = { email = it },
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )
}

@Composable
private fun MobileForgotPasswordScreenTitleComponent() {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Şifremi Unuttum",
            style = SkyFitTypography.heading3
        )
        Text(
            text = "Şifrenizi sıfırlamak için E-postanızı girin",
            style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
        )
    }
}

@Composable
private fun MobileForgotPasswordScreenActionsComponent(
    onClickContinue: () -> Unit,
    onClickCancel: () -> Unit
) {
    Column {
        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "Devam Et",
            onClick = onClickContinue,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )
        Spacer(Modifier.height(14.dp))
        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "İptal",
            onClick = onClickCancel,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )
        Spacer(Modifier.height(44.dp))
    }
}