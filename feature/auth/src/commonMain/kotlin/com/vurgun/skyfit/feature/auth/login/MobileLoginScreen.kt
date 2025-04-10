package com.vurgun.skyfit.feature.auth.login

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.auth.component.MobileLoginWelcomeGroup
import com.vurgun.skyfit.ui.core.components.button.PrimaryLargeButton
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitPasswordInputComponent
import com.vurgun.skyfit.ui.core.components.text.PhoneNumberTextInput
import com.vurgun.skyfit.ui.core.components.text.SecondaryMediumText
import com.vurgun.skyfit.ui.core.components.text.SecondaryMediumUnderlinedText
import com.vurgun.skyfit.ui.core.styling.LocalPadding
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import com.vurgun.skyfit.ui.core.utils.KeyboardState
import com.vurgun.skyfit.ui.core.utils.keyboardAsState
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.auth_forgot_password_action
import skyfit.ui.core.generated.resources.auth_login_action
import skyfit.ui.core.generated.resources.auth_login_password_action
import skyfit.ui.core.generated.resources.auth_password_input_hint
import skyfit.ui.core.generated.resources.user_phone_number_label

@Composable
fun MobileLoginScreen(
    goToOtp: () -> Unit,
    goToOnboarding: () -> Unit,
    goToDashboard: () -> Unit,
    goToForgotPassword: () -> Unit,
    goToPrivacyPolicy: () -> Unit,
    goToTermsAndConditions: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
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
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is LoginViewEvent.GoToDashboard -> {
                    goToDashboard()
                }

                is LoginViewEvent.GoToOnboarding -> {
                    goToOnboarding()
                }

                is LoginViewEvent.GoToOTPVerification -> {
                    goToOtp()
                }

                is LoginViewEvent.ShowError -> {
                    errorMessage = event.message
                }
            }
        }
    }

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
            MobileLoginWelcomeGroup()

            MobileLoginWithPhoneContentGroup(
                phoneNumber = phoneNumber,
                errorMessage = errorMessage,
                onPhoneNumberChanged = viewModel::onPhoneNumberChanged,
                isPasswordEnabled = isPasswordVisible,
                password = password,
                onPasswordChanged = viewModel::onPasswordChanged,
                onPasswordSubmit = {
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

@Composable
private fun MobileLoginWithPhoneContentGroup(
    phoneNumber: String,
    errorMessage: String?,
    onPhoneNumberChanged: (String) -> Unit,
    isPasswordEnabled: Boolean,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onPasswordSubmit: () -> Unit,
    onTogglePassword: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickTermsAndConditions: () -> Unit,
    onClickForgotPassword: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = stringResource(Res.string.user_phone_number_label),
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp),
            style = SkyFitTypography.bodySmallSemibold
        )
        Spacer(Modifier.height(4.dp))
        PhoneNumberTextInput(value = phoneNumber, onValueChange = onPhoneNumberChanged)


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
            SkyFitPasswordInputComponent(
                hint = stringResource(Res.string.auth_password_input_hint),
                value = password,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Go),
                onKeyboardGoAction = onPasswordSubmit,
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