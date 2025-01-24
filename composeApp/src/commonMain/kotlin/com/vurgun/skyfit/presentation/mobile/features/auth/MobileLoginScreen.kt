package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
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
import com.vurgun.skyfit.presentation.shared.components.SkyFitPasswordInputComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextInputComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitLogoComponent
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
fun MobileLoginScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(36.dp))
            SkyFitLogoComponent()
            Spacer(Modifier.height(48.dp))
            MobileLoginScreenTitle()
            Spacer(Modifier.height(48.dp))
            MobileLoginInputGroupComponent(
                onLoginGoogle = {},
                onLoginFacebook = {},
                onLoginApple = {},
                onForgotPassword = {
                    navigator.jumpAndStay(SkyFitNavigationRoute.ForgotPassword)
                }
            )
            Spacer(Modifier.height(48.dp))
            MobileLoginActionsComponent(
                onClickLogin = {
                    navigator.jumpAndTakeover(SkyFitNavigationRoute.Login, SkyFitNavigationRoute.Dashboard)
                },
                onClickRegister = {
                    navigator.jumpAndStay(SkyFitNavigationRoute.Register)
                }
            )
        }
    }
}

@Composable
private fun MobileLoginScreenTitle() {
    Text(
        text = "Skyfit’e Hoşgeldin \uD83D\uDC4B\uD83C\uDFFC",
        style = SkyFitTypography.heading3
    )
}

@Composable
private fun ColumnScope.MobileLoginInputGroupComponent(
    onLoginGoogle: () -> Unit,
    onLoginFacebook: () -> Unit,
    onLoginApple: () -> Unit,
    onForgotPassword: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    SkyFitButtonComponent(
        Modifier.fillMaxWidth(), text = "Google ile giriş yap",
        onClick = onLoginGoogle,
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Large,
        initialState = ButtonState.Rest,
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )

    Spacer(Modifier.height(24.dp))

    SkyFitButtonComponent(
        Modifier.fillMaxWidth(), text = "Facebook ile giriş yap",
        onClick = onLoginFacebook,
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Large,
        initialState = ButtonState.Rest,
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )

    Spacer(Modifier.height(24.dp))

    SkyFitButtonComponent(
        Modifier.fillMaxWidth(), text = "Apple ile giriş yap",
        onClick = onLoginApple,
        variant = ButtonVariant.Secondary,
        size = ButtonSize.Large,
        initialState = ButtonState.Rest,
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )

    Spacer(Modifier.height(16.dp))

    Text(
        text = "ya da email ile devam et",
        style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
    )

    Spacer(Modifier.height(16.dp))

    SkyFitTextInputComponent(
        hint = "Email’inizi girin",
        value = email,
        onValueChange = { email = it },
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )

    Spacer(Modifier.height(16.dp))

    SkyFitPasswordInputComponent(
        hint = "Şifrenizi girin",
        value = password,
        onValueChange = { password = it }
    )

    Spacer(Modifier.height(16.dp))

    Text(
        text = "Şifremi Unuttum",
        style = SkyFitTypography.bodyMediumUnderlined.copy(SkyFitColor.text.secondary),
        modifier = Modifier.clickable(onClick = onForgotPassword)
    )
}

@Composable
private fun MobileLoginActionsComponent(
    onClickLogin: () -> Unit,
    onClickRegister: () -> Unit
) {
    Column {
        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "Giriş Yap",
            onClick = onClickLogin,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )
        Spacer(Modifier.height(14.dp))
        SkyFitButtonComponent(
            Modifier.fillMaxWidth(), text = "Kayıt Ol",
            onClick = onClickRegister,
            variant = ButtonVariant.Secondary,
            size = ButtonSize.Large,
            initialState = ButtonState.Rest
        )
        Spacer(Modifier.height(44.dp))
    }
}