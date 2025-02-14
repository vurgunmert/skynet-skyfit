package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
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
import com.vurgun.skyfit.presentation.mobile.resources.MobileStyleGuide
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitLogoComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitPasswordInputComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitTextInputComponent
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_envelope_closed
import skyfit.composeapp.generated.resources.ic_profile
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileRegisterScreen(navigator: Navigator) {

    var isSaveButtonEnabled by remember { mutableStateOf(false) }
    var showTermsDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .imePadding()
                .widthIn(max = MobileStyleGuide.screenWithMax)
                .fillMaxHeight()
                .padding(MobileStyleGuide.padding24)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SkyFitLogoComponent()
            Spacer(Modifier.height(36.dp))
            MobileRegisterScreenTitleComponent()
            Spacer(Modifier.height(48.dp))
            MobileRegisterInputGroupComponent(
                onInputReadyState = { isSaveButtonEnabled = it }
            )
            Spacer(Modifier.weight(1f))
            MobileRegisterScreenActionComponent(enabled = isSaveButtonEnabled) {
                navigator.jumpAndTakeover(SkyFitNavigationRoute.Register, SkyFitNavigationRoute.Onboarding)
            }
            Spacer(Modifier.height(16.dp))
            MobileRegisterScreenLegalActionsComponent(
                onPrivacyPolicyClick = { showPrivacyDialog = true },
                onTermsOfServiceClick = { showTermsDialog = true }
            )
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
private fun MobileRegisterInputGroupComponent(onInputReadyState: (Boolean) -> Unit) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    fun validateInputs() {
        onInputReadyState(
            username.isNotBlank() &&
                    email.isNotBlank() &&
                    password.isNotBlank() &&
                    password == confirmPassword
        )
    }

    SkyFitTextInputComponent(
        hint = "Kullanıcı Adı",
        value = username,
        onValueChange = {
            username = it
            validateInputs()
        },
        leftIconPainter = painterResource(Res.drawable.ic_profile)
    )
    Spacer(Modifier.height(16.dp))
    SkyFitTextInputComponent(
        hint = "Email’inizi girin",
        value = email,
        onValueChange = {
            email = it
            validateInputs()
        },
        leftIconPainter = painterResource(Res.drawable.ic_envelope_closed)
    )
    Spacer(Modifier.height(16.dp))
    SkyFitPasswordInputComponent(
        hint = "Şifrenizi girin",
        value = password,
        onValueChange = {
            password = it
            validateInputs()
        },
    )
    Spacer(Modifier.height(16.dp))
    SkyFitPasswordInputComponent(
        hint = "Şifrenizi tekrar girin",
        value = confirmPassword,
        onValueChange = {
            confirmPassword = it
            validateInputs()
        }
    )
}

@Composable
private fun MobileRegisterScreenActionComponent(enabled: Boolean = false, onRegisterClick: () -> Unit) {
    SkyFitButtonComponent(
        modifier = Modifier.fillMaxWidth(), text = "Kayıt Ol",
        onClick = onRegisterClick,
        isEnabled = enabled
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
