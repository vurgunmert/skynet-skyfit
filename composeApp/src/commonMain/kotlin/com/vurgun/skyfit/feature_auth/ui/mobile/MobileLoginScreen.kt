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
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.resources.MobileStyleGuide
import com.vurgun.skyfit.core.ui.components.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.text.input.PhoneNumberTextInput
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
import com.vurgun.skyfit.navigation.jumpAndTakeover
import com.vurgun.skyfit.feature_auth.ui.viewmodel.MobileLoginNavigation
import com.vurgun.skyfit.feature_auth.ui.viewmodel.MobileLoginViewModel
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.auth_login
import skyfit.composeapp.generated.resources.auth_welcome_message

@Composable
fun MobileLoginScreen(navigator: Navigator) {
    val viewModel: MobileLoginViewModel = koinInject()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val isLoginEnabled by viewModel.isLoginEnabled.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

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
                onPhoneNumberChanged = viewModel::onPhoneNumberChanged
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
    onPhoneNumberChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PhoneNumberTextInput(value = phoneNumber, onValueChange = onPhoneNumberChanged)

        errorMessage?.let {
            Text(
                text = it,
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.criticalOnBgFill
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
        onClick = onClickLogin,
        isLoading = isLoading,
        isEnabled = isLoginEnabled && !isLoading
    )
}


/*
MobileLoginWithCredentialsContentGroup(
    onLoginGoogle = {
        navigator.jumpAndTakeover(SkyFitNavigationRoute.Login, SkyFitNavigationRoute.Dashboard)
    },
    onLoginFacebook = {
        navigator.jumpAndTakeover(SkyFitNavigationRoute.Login, SkyFitNavigationRoute.Dashboard)
    },
    onLoginApple = {
        navigator.jumpAndTakeover(SkyFitNavigationRoute.Login, SkyFitNavigationRoute.Dashboard)
    },
    onLoginCredentials = {
        navigator.jumpAndTakeover(SkyFitNavigationRoute.Login, SkyFitNavigationRoute.Dashboard)
    },
    onForgotPassword = {
        navigator.jumpAndStay(SkyFitNavigationRoute.ForgotPassword)
    }
)

@Composable
private fun MobileLoginWithCredentialsContentGroup(
    onLoginGoogle: () -> Unit,
    onLoginFacebook: () -> Unit,
    onLoginApple: () -> Unit,
    onLoginCredentials: () -> Unit,
    onForgotPassword: () -> Unit
) {
    //TODO: ViewModel
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SecondaryLargeButton(
            text = stringResource(Res.string.auth_login_via_google),
            onClick = onLoginGoogle,
            modifier = Modifier.fillMaxWidth(),
            leftIconPainter = painterResource(Res.drawable.ic_google)
        )

        Spacer(Modifier.height(24.dp))

        SecondaryLargeButton(
            text = stringResource(Res.string.auth_login_via_facebook),
            onClick = onLoginFacebook,
            modifier = Modifier.fillMaxWidth(),
            leftIconPainter = painterResource(Res.drawable.ic_facebook_fill)
        )

        Spacer(Modifier.height(24.dp))

        SecondaryLargeButton(
            text = stringResource(Res.string.auth_login_via_apple),
            onClick = onLoginApple,
            modifier = Modifier.fillMaxWidth(),
            leftIconPainter = painterResource(Res.drawable.ic_apple)
        )

        Spacer(Modifier.height(16.dp))

        SecondaryMediumText(text = stringResource(Res.string.auth_continue_via_email))

        Spacer(Modifier.height(16.dp))

        SkyFitTextInputComponent(
            hint = stringResource(Res.string.auth_enter_email),
            value = email,
            onValueChange = { email = it },
            leftIconPainter = painterResource(Res.drawable.ic_envelope_closed)
        )

        Spacer(Modifier.height(16.dp))

        SkyFitPasswordInputComponent(
            hint = stringResource(Res.string.auth_enter_password),
            value = password,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Go),
            onKeyboardGoAction = onLoginCredentials,
            onValueChange = { password = it }
        )

        Spacer(Modifier.height(16.dp))

        SecondaryMediumUnderlinedText(
            text = stringResource(Res.string.auth_forgot_password),
            modifier = Modifier.clickable(onClick = onForgotPassword)
        )
    }
}
*/