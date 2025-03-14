package com.vurgun.skyfit.feature_auth.ui.mobile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.text.input.PasswordTextInput
import com.vurgun.skyfit.core.ui.components.text.input.PersonNameTextInput
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.core.utils.KeyboardState
import com.vurgun.skyfit.core.utils.keyboardAsState
import com.vurgun.skyfit.feature_auth.ui.viewmodel.PasswordCreateViewModel
import com.vurgun.skyfit.feature_auth.ui.viewmodel.CreatePasswordViewEvent
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.auth_enter_again_password
import skyfit.composeapp.generated.resources.auth_enter_password
import skyfit.composeapp.generated.resources.auth_register
import skyfit.composeapp.generated.resources.user_name

@Composable
fun MobileCreatePasswordScreen(navigator: Navigator) {
    val viewModel: PasswordCreateViewModel = koinViewModel()

    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isRegisterEnabled by viewModel.isRegisterEnabled.collectAsState()

    val nameFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    val keyboardState by keyboardAsState()
    val scrollState = rememberScrollState()

    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(keyboardState) {
        if (keyboardState is KeyboardState.Opened) {
            scrollState.animateScrollTo(keyboardState.heightPx)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is CreatePasswordViewEvent.GoToOnboarding -> {
                    navigator.jumpAndTakeover(NavigationRoute.CreatePassword, NavigationRoute.Onboarding)
                }

                is CreatePasswordViewEvent.Error -> {
                    errorMessage = event.message
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        nameFocusRequester.requestFocus()
    }

    SkyFitScaffold {
        Column(
            Modifier
                .padding(SkyFitStyleGuide.Padding.xLarge)
                .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                .verticalScroll(scrollState)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileLoginWelcomeGroup()

            Spacer(Modifier.height(48.dp))
            MobileRegisterInputGroupComponent(
                username = username,
                password = password,
                confirmPassword = confirmPassword,
                passwordError = errorMessage,
                onFullNameChanged = viewModel::setFullName,
                onPasswordChanged = viewModel::setPassword,
                onConfirmPasswordChanged = viewModel::setConfirmPassword,
                nameFocusRequester = nameFocusRequester,
                passwordFocusRequester = passwordFocusRequester,
                confirmPasswordFocusRequester = confirmPasswordFocusRequester
            )
            Spacer(Modifier.height(16.dp))
            Spacer(Modifier.weight(1f))
            MobileRegisterScreenActionComponent(
                isRegisterEnabled = isRegisterEnabled,
                isLoading = false,
                onClickRegister = viewModel::onRegisterClicked
            )
            Spacer(Modifier.height(16.dp))
            MobileRegisterScreenLegalActionsComponent(
                onPrivacyPolicyClick = { navigator.jumpAndStay(NavigationRoute.PrivacyPolicy) },
                onTermsOfServiceClick = { navigator.jumpAndStay(NavigationRoute.TermsAndConditions) }
            )
        }
    }
}

@Composable
private fun MobileRegisterInputGroupComponent(
    username: String,
    password: String,
    confirmPassword: String,
    passwordError: String?,
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

        PersonNameTextInput(
            hint = stringResource(Res.string.user_name),
            value = username,
            onValueChange = onFullNameChanged,
            focusRequester = nameFocusRequester,
            onKeyboardNextAction = {
                passwordFocusRequester.requestFocus()
            }
        )

        PasswordTextInput(
            value = password,
            onValueChange = onPasswordChanged,
            hint = stringResource(Res.string.auth_enter_password),
            error = passwordError,
            focusRequester = passwordFocusRequester,
            onKeyboardNextAction = {
                confirmPasswordFocusRequester.requestFocus()
            }
        )

        PasswordTextInput(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChanged,
            hint = stringResource(Res.string.auth_enter_again_password),
            focusRequester = confirmPasswordFocusRequester,
            onKeyboardDoneAction = {
                keyboardController?.hide()
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
