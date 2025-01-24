package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitPasswordInputComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextInputComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitLogoComponent
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileRegisterScreen(navigator: Navigator) {

    var saveEnabled = false
    var showTermsDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    SkyFitScaffold {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(36.dp))
            SkyFitLogoComponent()
            Spacer(Modifier.height(48.dp))
            MobileRegisterScreenTitleComponent()
            Spacer(Modifier.height(48.dp))
            MobileRegisterInputGroupComponent()
            Spacer(Modifier.weight(1f))
            MobileRegisterScreenActionComponent(enabled = saveEnabled) {
                navigator.jumpAndTakeover(SkyFitNavigationRoute.Register, SkyFitNavigationRoute.Dashboard)
            }
            Spacer(Modifier.height(16.dp))
            MobileRegisterScreenLegalActionsComponent(
                onPrivacyPolicyClick = { showPrivacyDialog = true },
                onTermsOfServiceClick = { showTermsDialog = true }
            )
            Spacer(Modifier.height(48.dp))
        }

        if (showTermsDialog) {
            MobileAppLegalDialog(
                policyId = "termsOfService",
                onDismissRequest = { showTermsDialog = false }
            )
        }

        if (showPrivacyDialog) {
            MobileAppLegalDialog(
                policyId = "privacyPolicy",
                onDismissRequest = { showPrivacyDialog = false }
            )
        }
    }
}

@Composable
private fun MobileRegisterScreenTitleComponent() {
    Text(
        text = "Skyfit’e Hoşgeldin \uD83D\uDC4B\uD83C\uDFFC",
        style = SkyFitTypography.heading3
    )
}

@Composable
private fun ColumnScope.MobileRegisterInputGroupComponent() {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    SkyFitTextInputComponent("Kullanıcı Adı", username, leftIconPainter = painterResource(Res.drawable.logo_skyfit))
    Spacer(Modifier.height(16.dp))
    SkyFitTextInputComponent("Kullanıcı Adı", email, leftIconPainter = painterResource(Res.drawable.logo_skyfit))
    Spacer(Modifier.height(16.dp))
    SkyFitPasswordInputComponent("Şifrenizi girin", password)
    Spacer(Modifier.height(16.dp))
    SkyFitPasswordInputComponent("Şifrenizi tekrar girin", confirmPassword)
}

@Composable
private fun MobileRegisterScreenActionComponent(enabled: Boolean = false, onRegisterClick: () -> Unit) {
    SkyFitButtonComponent(
        Modifier.fillMaxWidth(), text = "Kayıt Ol",
        onClick = onRegisterClick,
        variant = ButtonVariant.Primary,
        size = ButtonSize.Large,
        initialState = ButtonState.Disabled,
        leftIconPainter = painterResource(Res.drawable.logo_skyfit)
    )
}


@Composable
private fun MobileRegisterScreenLegalActionsComponent(
    onPrivacyPolicyClick: () -> Unit,
    onTermsOfServiceClick: () -> Unit
) {
    val annotatedText = buildAnnotatedString {
        append("Kaydolduğunuzda ")

        pushStringAnnotation(tag = "PRIVACY_POLICY", annotation = "Privacy Policy")
        withStyle(style = SpanStyle(color = SkyFitColor.text.secondary, textDecoration = TextDecoration.Underline)) {
            append("Gizlilik Politikamızı")
        }
        pop()

        append(" ve ")

        pushStringAnnotation(tag = "TERMS_OF_SERVICE", annotation = "Terms of Service")
        withStyle(style = SpanStyle(color = SkyFitColor.text.secondary, textDecoration = TextDecoration.Underline)) {
            append("Hizmet Şartlarımızı")
        }
        pop()

        append(" kabul etmiş olacaksınız.")
    }

    @Suppress("DEPRECATION")
    ClickableText(
        text = annotatedText,
        style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.secondary),
        onClick = { offset ->
            annotatedText.getStringAnnotations(start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    when (annotation.tag) {
                        "PRIVACY_POLICY" -> onPrivacyPolicyClick()
                        "TERMS_OF_SERVICE" -> onTermsOfServiceClick()
                    }
                }
        }
    )
}
