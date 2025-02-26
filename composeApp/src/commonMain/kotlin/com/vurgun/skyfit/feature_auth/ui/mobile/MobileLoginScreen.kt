package com.vurgun.skyfit.feature_auth.ui.mobile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.vurgun.skyfit.core.ui.resources.MobileStyleGuide
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_auth.ui.viewmodel.MobileLoginNavigation
import com.vurgun.skyfit.feature_auth.ui.viewmodel.MobileLoginViewModel
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import com.vurgun.skyfit.navigation.jumpAndTakeover
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.auth_enter_password
import skyfit.composeapp.generated.resources.auth_forgot_password
import skyfit.composeapp.generated.resources.auth_login
import skyfit.composeapp.generated.resources.auth_welcome_message

@Composable
fun MobileLoginScreen(navigator: Navigator) {
    val viewModel: MobileLoginViewModel = koinInject()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoginEnabled by viewModel.isLoginEnabled.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isPasswordEnabled by viewModel.isPasswordEnabled.collectAsState()
    var isShowingPrivacyDialog by remember { mutableStateOf(false) }
    var isShowingTermsDialog by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.navigationEvents.collectLatest { event ->
            when (event) {
                is MobileLoginNavigation.GoToDashboard -> {
                    navigator.jumpAndTakeover(NavigationRoute.Login, NavigationRoute.Dashboard)
                }

                is MobileLoginNavigation.GoToOTPVerification -> {
                    navigator.jumpAndStay(NavigationRoute.OTPVerification.route + "?phone=$phoneNumber")
                }

                is MobileLoginNavigation.ShowError -> {
                    errorMessage = event.message
                }
            }
        }
    }

    SkyFitScaffold {
        Column(
            modifier = Modifier
                .padding(MobileStyleGuide.padding24)
                .widthIn(max = MobileStyleGuide.screenWithMax)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            MobileLoginWelcomeGroup()

            MobileLoginWithPhoneContentGroup(
                phoneNumber = phoneNumber,
                errorMessage = errorMessage,
                onPhoneNumberChanged = viewModel::onPhoneNumberChanged,
                isPasswordEnabled = isPasswordEnabled,
                password = password,
                onPasswordChanged = viewModel::onPasswordChanged,
                onPasswordSubmit = {
                    errorMessage = null
                    viewModel.onLoginClicked()
                },
                onTogglePassword = viewModel::togglePassword,
                onClickPrivacyPolicy = { isShowingPrivacyDialog = true },
                onClickTermsAndConditions = { isShowingTermsDialog = true },
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

        if (isShowingPrivacyDialog) {
            LegalContentDialog(
                content = SKYFIT_PRIVACY_POLICY_HTML_FORMAT,
                onDismissRequest = { isShowingPrivacyDialog = false }
            )
        }

        if (isShowingTermsDialog) {
            LegalContentDialog(
                content = TERMS_AND_CONDITIONS_HTML_FORMAT,
                onDismissRequest = { isShowingTermsDialog = false }
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
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = "Telefon Numarası",
            modifier = Modifier.padding(start = 8.dp),
            style = SkyFitTypography.bodySmallSemibold
        )
        Spacer(Modifier.height(4.dp))
        PhoneNumberTextInput(value = phoneNumber, onValueChange = onPhoneNumberChanged)

        errorMessage?.let {
            Text(
                text = it,
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.criticalOnBgFill
            )
        }

        Row(Modifier.fillMaxWidth()) {
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

        if (!isPasswordEnabled) {
            Spacer(Modifier.height(8.dp))

            SecondaryMediumUnderlinedText(
                text = "Şifre ile Giriş Yap",
                modifier = Modifier.clickable(onClick = onTogglePassword)
            )
        } else {
            Spacer(Modifier.height(8.dp))

            SkyFitPasswordInputComponent(
                hint = stringResource(Res.string.auth_enter_password),
                value = password,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Go),
                onKeyboardGoAction = onPasswordSubmit,
                onValueChange = onPasswordChanged
            )

            Spacer(Modifier.height(8.dp))

            SecondaryMediumUnderlinedText(
                text = stringResource(Res.string.auth_forgot_password),
                modifier = Modifier.clickable(onClick = onClickForgotPassword)
            )
        }
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