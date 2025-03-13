package com.vurgun.skyfit.feature_auth.ui.mobile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import com.vurgun.skyfit.core.ui.components.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.SkyFitPasswordInputComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumText
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumUnderlinedText
import com.vurgun.skyfit.core.ui.components.text.input.PhoneNumberTextInput
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.core.utils.KeyboardState
import com.vurgun.skyfit.core.utils.keyboardAsState
import com.vurgun.skyfit.feature_auth.ui.viewmodel.LoginViewEvent
import com.vurgun.skyfit.feature_auth.ui.viewmodel.MobileLoginViewModel
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.auth_enter_password
import skyfit.composeapp.generated.resources.auth_forgot_password
import skyfit.composeapp.generated.resources.auth_login
import skyfit.composeapp.generated.resources.auth_login_via_password
import skyfit.composeapp.generated.resources.auth_welcome_message
import skyfit.composeapp.generated.resources.phone_number

@Composable
fun MobileLoginScreen(navigator: Navigator) {
    val viewModel: MobileLoginViewModel = koinInject()
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
                    navigator.jumpAndTakeover(NavigationRoute.Login, NavigationRoute.Dashboard)
                }

                is LoginViewEvent.GoToOTPVerification -> {
                    navigator.jumpAndStay(NavigationRoute.LoginOTPVerification.route + "?phone=$phoneNumber")
                }

                is LoginViewEvent.ShowError -> {
                    errorMessage = event.message
                }
            }
        }
    }

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .padding(SkyFitStyleGuide.Padding.xLarge)
                .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                .fillMaxHeight()
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
                    viewModel.onLoginClicked()
                },
                onTogglePassword = viewModel::togglePasswordVisibility,
                onClickPrivacyPolicy = { navigator.jumpAndStay(NavigationRoute.PrivacyPolicy) },
                onClickTermsAndConditions = { navigator.jumpAndStay(NavigationRoute.TermsAndConditions) },
                onClickForgotPassword = { }
            )
            MobileLoginActionGroup(
                isLoginEnabled = isLoginEnabled,
                isLoading = isLoading,
                onClickLogin = {
                    errorMessage = null
                    viewModel.onLoginClicked()
                }
            )
        }
    }
}


@Composable
fun MobileLoginWelcomeGroup() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SkyFitLogoComponent()
        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(Res.string.auth_welcome_message),
            style = SkyFitTypography.heading3
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
    onPasswordSubmit: () -> Unit,
    onTogglePassword: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickTermsAndConditions: () -> Unit,
    onClickForgotPassword: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = stringResource(Res.string.phone_number),
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
                color = SkyFitColor.text.criticalOnBgFill
            )
        }

        Spacer(Modifier.height(4.dp))
        LegalActionGroup(onClickPrivacyPolicy, onClickTermsAndConditions)

        Spacer(Modifier.height(16.dp))
        if (!isPasswordEnabled) {
            Box(Modifier.fillMaxWidth()) {
                SecondaryMediumUnderlinedText(
                    text = stringResource(Res.string.auth_login_via_password),
                    modifier = Modifier.align(Alignment.Center).clickable(onClick = onTogglePassword)
                )
            }
        } else {
            SkyFitPasswordInputComponent(
                hint = stringResource(Res.string.auth_enter_password),
                value = password,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Go),
                onKeyboardGoAction = onPasswordSubmit,
                onValueChange = onPasswordChanged
            )
            Spacer(Modifier.height(16.dp))
            Box(Modifier.fillMaxWidth()) {
                SecondaryMediumUnderlinedText(
                    text = stringResource(Res.string.auth_forgot_password),
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
        text = stringResource(Res.string.auth_login),
        onClick = { if (isLoginEnabled) onClickLogin.invoke() },
        isLoading = isLoading,
        isEnabled = isLoginEnabled && !isLoading
    )
}