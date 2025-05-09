package com.vurgun.skyfit.feature.access.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.registry.rememberScreen
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.special.SideBySideLayout
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.PasswordTextInput
import com.vurgun.skyfit.core.ui.components.text.PhoneNumberTextInput
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumText
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumUnderlinedText
import com.vurgun.skyfit.core.ui.styling.LocalDimensions
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.KeyboardState
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.core.ui.utils.keyboardAsState
import com.vurgun.skyfit.feature.access.AuthFlow
import com.vurgun.skyfit.feature.access.authScreenFlowModule
import com.vurgun.skyfit.feature.access.component.LoginWelcomeGroup
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.auth_forgot_password_action
import skyfit.core.ui.generated.resources.auth_login_action
import skyfit.core.ui.generated.resources.auth_login_password_action
import skyfit.core.ui.generated.resources.auth_password_input_hint
import skyfit.core.ui.generated.resources.user_phone_number_label

class AuthFlowScreen : Screen {

    @Composable
    override fun Content() {

        ScreenRegistry {
            authScreenFlowModule()
        }

        val login = rememberScreen(AuthFlow.Login)
        Navigator(login) { navigator ->
            SlideTransition(navigator)
        }
    }
}


class LoginScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val navigator = LocalNavigator.currentOrThrow
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()

        val viewModel = koinScreenModel<LoginViewModel>()

        LoginContent(
            isExpanded = windowSize == WindowSize.EXPANDED,
            goToOtp = {
                navigator.push(AuthFlow.LoginVerifyOTP)
            },
            goToOnboarding = {
                navigator.replace(SharedScreen.Onboarding)
            },
            goToDashboard = {
                appNavigator.replaceAll(SharedScreen.Dashboard)
            },
            goToForgotPassword = {
                navigator.push(AuthFlow.ForgotPassword)
            },
            goToPrivacyPolicy = {
                navigator.push(AuthFlow.PrivacyPolicy)
            },
            goToTermsAndConditions = {
                navigator.push(AuthFlow.TermsAndConditions)
            },
            viewModel = viewModel
        )
    }
}

@Composable
private fun MobileLoginWithPhoneContentGroup(
    phoneNumber: String,
    errorMessage: String?,
    onPhoneNumberChanged: (String) -> Unit,
    isPasswordEnabled: Boolean,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onSubmitInput: () -> Unit,
    onTogglePassword: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickTermsAndConditions: () -> Unit,
    onClickForgotPassword: () -> Unit
) {
    val passwordFocusRequester = remember { FocusRequester() }

    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = stringResource(Res.string.user_phone_number_label),
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
            style = SkyFitTypography.bodySmallSemibold
        )
        Spacer(Modifier.height(4.dp))
        PhoneNumberTextInput(
            value = phoneNumber,
            onValueChange = onPhoneNumberChanged,
            onKeyboardGoAction = {
                if (isPasswordEnabled) {
                    passwordFocusRequester.requestFocus()
                } else {
                    onSubmitInput()
                }
            }
        )

        errorMessage?.let {
            Spacer(Modifier.height(4.dp))
            Text(
                text = it,
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.criticalOnBgFill,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(Modifier.height(4.dp))
        LegalActionGroup(onClickPrivacyPolicy, onClickTermsAndConditions)

        Spacer(Modifier.height(16.dp))
        if (!isPasswordEnabled) {
            Box(Modifier.fillMaxWidth()) {
                SecondaryMediumUnderlinedText(
                    text = stringResource(Res.string.auth_login_password_action),
                    modifier = Modifier.align(Alignment.Center).clickable(onClick = onTogglePassword)
                )
            }
        } else {
            LaunchedEffect(isPasswordEnabled) {
                if (isPasswordEnabled && phoneNumber.isNotEmpty()) {
                    passwordFocusRequester.requestFocus()
                }
            }

            PasswordTextInput(
                hint = stringResource(Res.string.auth_password_input_hint),
                value = password,
                focusRequester = passwordFocusRequester,
                onKeyboardDoneAction = onSubmitInput,
                onValueChange = onPasswordChanged
            )
            Spacer(Modifier.height(16.dp))
            Box(Modifier.fillMaxWidth()) {
                SecondaryMediumUnderlinedText(
                    text = stringResource(Res.string.auth_forgot_password_action),
                    modifier = Modifier.align(Alignment.Center).clickable(onClick = onClickForgotPassword)
                )
            }
        }
    }
}

@Composable
private fun LegalActionGroup(onClickPrivacyPolicy: () -> Unit, onClickTermsAndConditions: () -> Unit) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
        SecondaryMediumUnderlinedText(
            text = "Gizlilik Politikasına",
            modifier = Modifier.clickable(onClick = onClickPrivacyPolicy)
        )
        SecondaryMediumText(" ve ")
        SecondaryMediumUnderlinedText(
            text = "Hizmet Şartlarına",
            modifier = Modifier.clickable(onClick = onClickTermsAndConditions)
        )
        SecondaryMediumText(" göz at.")
    }
}

@Composable
private fun MobileLoginActionGroup(
    isLoginEnabled: Boolean,
    isLoading: Boolean,
    onClickLogin: () -> Unit
) {
    PrimaryLargeButton(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.auth_login_action),
        onClick = { if (isLoginEnabled) onClickLogin.invoke() },
        isLoading = isLoading,
        isEnabled = isLoginEnabled && !isLoading
    )
}

@Composable
private fun LoginContent(
    isExpanded: Boolean,
    goToOtp: () -> Unit,
    goToOnboarding: () -> Unit,
    goToDashboard: () -> Unit,
    goToForgotPassword: () -> Unit,
    goToPrivacyPolicy: () -> Unit,
    goToTermsAndConditions: () -> Unit,
    viewModel: LoginViewModel
) {
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoginEnabled by viewModel.isLoginEnabled.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isPasswordVisible by viewModel.isPasswordVisible.collectAsState()

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val keyboardState by keyboardAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(keyboardState) {
        if (keyboardState is KeyboardState.Opened) {
            scrollState.animateScrollTo(keyboardState.heightPx)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { event ->
            when (event) {
                is LoginEffect.GoToDashboard -> goToDashboard()
                is LoginEffect.GoToOnboarding -> goToOnboarding()
                is LoginEffect.GoToOTPVerification -> goToOtp()
                is LoginEffect.ShowError -> errorMessage = event.message
            }
        }
    }

    if (isExpanded) {
        SideBySideLayout(
            leftModifier = Modifier.background(SkyFitColor.background.fillTransparentSecondary),
            leftContent = {
                LoginWelcomeGroup()
            },
            rightContent = {
                Column(
                    modifier = Modifier
                        .widthIn(max = LocalDimensions.current.mobileMaxWidth, min = LocalDimensions.current.mobileMinWidth)
                        .verticalScroll(scrollState)
                        .imePadding(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterVertically)
                ) {
                    MobileLoginWithPhoneContentGroup(
                        phoneNumber,
                        errorMessage,
                        viewModel::onPhoneNumberChanged,
                        isPasswordVisible,
                        password,
                        viewModel::onPasswordChanged,
                        onSubmitInput = {
                            errorMessage = null
                            viewModel.submitLogin()
                        },
                        onTogglePassword = viewModel::togglePasswordVisibility,
                        onClickPrivacyPolicy = goToPrivacyPolicy,
                        onClickTermsAndConditions = goToTermsAndConditions,
                        onClickForgotPassword = goToForgotPassword
                    )
                    MobileLoginActionGroup(
                        isLoginEnabled = isLoginEnabled,
                        isLoading = isLoading,
                        onClickLogin = {
                            errorMessage = null
                            viewModel.submitLogin()
                        }
                    )
                }
            }
        )

    } else {
        SkyFitMobileScaffold {
            Column(
                modifier = Modifier
                    .padding(LocalPadding.current.large)
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(48.dp)
            ) {
                LoginWelcomeGroup()

                MobileLoginWithPhoneContentGroup(
                    phoneNumber,
                    errorMessage,
                    viewModel::onPhoneNumberChanged,
                    isPasswordVisible,
                    password,
                    viewModel::onPasswordChanged,
                    onSubmitInput = {
                        errorMessage = null
                        viewModel.submitLogin()
                    },
                    onTogglePassword = viewModel::togglePasswordVisibility,
                    onClickPrivacyPolicy = goToPrivacyPolicy,
                    onClickTermsAndConditions = goToTermsAndConditions,
                    onClickForgotPassword = goToForgotPassword
                )
                MobileLoginActionGroup(
                    isLoginEnabled = isLoginEnabled,
                    isLoading = isLoading,
                    onClickLogin = {
                        errorMessage = null
                        viewModel.submitLogin()
                    }
                )
            }
        }
    }
}
