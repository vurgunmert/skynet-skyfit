package com.vurgun.skyfit.presentation.mobile.features.auth

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.mobile.resources.MobileStyleGuide
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.button.PrimaryLargeButton
import com.vurgun.skyfit.presentation.shared.components.text.input.PasswordTextInput
import com.vurgun.skyfit.presentation.shared.components.text.input.PersonNameTextInput
import com.vurgun.skyfit.presentation.shared.components.text.input.PhoneNumberTextInput
import com.vurgun.skyfit.presentation.shared.navigation.NavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.query
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.auth_register

@Composable
fun MobileRegisterScreen(navigator: Navigator) {
    val viewModel: MobileRegisterViewModel = koinInject()
    val keyboardController = LocalSoftwareKeyboardController.current

    val currentEntry by navigator.currentEntry.collectAsState(null)
    val phoneNumber = remember(currentEntry) {
        currentEntry?.query("phone", default = "") ?: ""
    }

    val fullName by viewModel.fullName.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isRegisterEnabled by viewModel.isRegisterEnabled.collectAsState()

    var showTermsDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }

    LaunchedEffect(phoneNumber) {
        viewModel.setPhoneNumber(phoneNumber)
    }

    val nameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    LaunchedEffect(viewModel) {
        viewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is MobileRegisterNavigation.GoToOnboarding -> {
                    navigator.jumpAndTakeover(NavigationRoute.Register, NavigationRoute.Onboarding)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        nameFocusRequester.requestFocus()
    }

    LaunchedEffect(isRegisterEnabled) {
        if (isRegisterEnabled) {
            keyboardController?.hide()
        }
    }

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
            MobileLoginWelcomeGroup()

            Spacer(Modifier.height(48.dp))
            MobileRegisterInputGroupComponent(
                phoneNumber = phoneNumber,
                fullName = fullName,
                password = password,
                confirmPassword = confirmPassword,
                passwordError = viewModel.passwordError.collectAsState().value,
                confirmPasswordError = viewModel.confirmPasswordError.collectAsState().value,
                onFullNameChanged = viewModel::setFullName,
                onPasswordChanged = viewModel::setPassword,
                onConfirmPasswordChanged = viewModel::setConfirmPassword,
                nameFocusRequester = nameFocusRequester,
                passwordFocusRequester = passwordFocusRequester,
                confirmPasswordFocusRequester = confirmPasswordFocusRequester
            )
            Spacer(Modifier.weight(1f))
            MobileRegisterScreenActionComponent(
                isRegisterEnabled = isRegisterEnabled,
                isLoading = false,
                onClickRegister = viewModel::onRegisterClicked
            )
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
private fun MobileRegisterInputGroupComponent(
    phoneNumber: String,
    fullName: String,
    password: String,
    confirmPassword: String,
    passwordError: String?,
    confirmPasswordError: String?,
    onFullNameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    nameFocusRequester: FocusRequester,
    passwordFocusRequester: FocusRequester,
    confirmPasswordFocusRequester: FocusRequester
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        PhoneNumberTextInput(
            value = phoneNumber,
            onValueChange = { },
            isEnabled = false
        )

        PersonNameTextInput(
            value = fullName,
            onValueChange = onFullNameChanged,
            focusRequester = nameFocusRequester,
            onKeyboardNextAction = {
                passwordFocusRequester.requestFocus()
            }
        )

        PasswordTextInput(
            value = password,
            onValueChange = onPasswordChanged,
            hint = "Şifrenizi girin",
            error = passwordError,
            focusRequester = passwordFocusRequester,
            onKeyboardNextAction = {
                confirmPasswordFocusRequester.requestFocus()
            }
        )

        PasswordTextInput(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChanged,
            hint = "Şifrenizi tekrar girin",
            error = confirmPasswordError,
            focusRequester = confirmPasswordFocusRequester,
            onKeyboardDoneAction = {
                keyboardController?.hide() // Close keyboard when done
            }
        )
    }
}


@Composable
private fun MobileRegisterScreenActionComponent(
    isRegisterEnabled: Boolean,
    isLoading: Boolean,
    onClickRegister: () -> Unit
) {
    PrimaryLargeButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.auth_register),
        onClick = onClickRegister,
        isLoading = isLoading,
        isEnabled = isRegisterEnabled && !isLoading
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
