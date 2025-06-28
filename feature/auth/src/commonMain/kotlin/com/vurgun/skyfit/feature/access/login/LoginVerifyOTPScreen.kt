package com.vurgun.skyfit.feature.access.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.special.FiweLogoDark
import com.vurgun.skyfit.core.ui.components.special.FiweLogoGroup
import com.vurgun.skyfit.core.ui.components.special.SideBySideLayout
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumText
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumUnderlinedText
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.styling.LocalDimensions
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEvent
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import com.vurgun.skyfit.core.ui.utils.formatPhoneNumber
import com.vurgun.skyfit.feature.access.component.OTPInputTextField
import com.vurgun.skyfit.feature.access.register.CreatePasswordScreen
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

class LoginVerifyOTPScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val appNavigator = LocalNavigator.currentOrThrow
        val viewModel: LoginOTPVerificationViewModel = koinScreenModel()

        if (windowSize == WindowSize.EXPANDED) {
            ExpandedLoginVerifyOTPScreen(
                goToCreatePassword = {
                    appNavigator.replace(CreatePasswordScreen())
                },
                goToDashboard = {
                    appNavigator.replaceAll(SharedScreen.Main)
                },
                goToOnboarding = {
                    appNavigator.replace(SharedScreen.Onboarding)
                },
                viewModel = viewModel
            )
        } else {
            MobileLoginVerifyOTPScreen(
                goToCreatePassword = {
                    appNavigator.replace(CreatePasswordScreen())
                },
                goToDashboard = {
                    appNavigator.replaceAll(SharedScreen.Main)
                },
                goToOnboarding = {
                    appNavigator.replace(SharedScreen.Onboarding)
                },
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun ExpandedLoginVerifyOTPScreen(
    goToCreatePassword: () -> Unit,
    goToDashboard: () -> Unit,
    goToOnboarding: () -> Unit,
    viewModel: LoginOTPVerificationViewModel,
) {
    val enteredOtp by viewModel.enteredOtp.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState("")
    val isResendEnabled by viewModel.isResendEnabled.collectAsState()
    val countdownTime by viewModel.countdownTime.collectAsState()
    val otpLength = viewModel.otpLength

    var errorMessage by remember { mutableStateOf<String?>(null) }

    CollectEvent(viewModel.eventFlow) { event ->
        when (event) {
            is LoginOTPVerificationEvent.GoToRegister -> goToCreatePassword()
            is LoginOTPVerificationEvent.GoToDashboard -> goToDashboard()
            is LoginOTPVerificationEvent.GoToOnboarding -> goToOnboarding()
            is LoginOTPVerificationEvent.ShowError -> errorMessage = event.message
        }
    }

    SideBySideLayout(
        leftModifier = Modifier.background(SkyFitColor.background.fillTransparentSecondary),
        leftContent = {
            FiweLogoGroup(
                subtitle = stringResource(Res.string.auth_code_sent_message, formatPhoneNumber(phoneNumber))
            )
        },
        rightContent = {
            Column(
                modifier = Modifier
                    .widthIn(max = LocalDimensions.current.mobileMaxWidth, min = LocalDimensions.current.mobileMinWidth)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterVertically)
            ) {
                OTPInputTextField(onCodeReady = viewModel::onOtpChanged)

                MobileOTPVerificationActionGroup(
                    onClickConfirm = viewModel::submitCode,
                    onClickResend = viewModel::resendOTP,
                    isConfirmEnabled = enteredOtp.length == otpLength && !isLoading,
                    isResendEnabled = isResendEnabled,
                    isLoading = isLoading,
                    countdownTime = countdownTime,
                    errorMessage = errorMessage
                )
            }
        })
}


@Composable
private fun MobileLoginVerifyOTPScreen(
    goToCreatePassword: () -> Unit,
    goToDashboard: () -> Unit,
    goToOnboarding: () -> Unit,
    viewModel: LoginOTPVerificationViewModel
) {

    val enteredOtp by viewModel.enteredOtp.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState("")
    val isResendEnabled by viewModel.isResendEnabled.collectAsState()
    val countdownTime by viewModel.countdownTime.collectAsState()
    val otpLength = viewModel.otpLength

    var errorMessage by remember { mutableStateOf<String?>(null) }

    CollectEvent(viewModel.eventFlow) { event ->
        when (event) {
            is LoginOTPVerificationEvent.GoToRegister -> {
                goToCreatePassword()
            }

            is LoginOTPVerificationEvent.GoToDashboard -> {
                goToDashboard()
            }

            is LoginOTPVerificationEvent.GoToOnboarding -> {
                goToOnboarding()
            }

            is LoginOTPVerificationEvent.ShowError -> {
                errorMessage = event.message
            }
        }
    }

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalPadding.current.large)
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {

            MobileOTPVerificationTextGroup(phoneNumber)

            OTPInputTextField(onCodeReady = viewModel::onOtpChanged)

            MobileOTPVerificationActionGroup(
                onClickConfirm = viewModel::submitCode,
                onClickResend = viewModel::resendOTP,
                isConfirmEnabled = enteredOtp.length == otpLength && !isLoading,
                isResendEnabled = isResendEnabled,
                isLoading = isLoading,
                countdownTime = countdownTime,
                errorMessage = errorMessage
            )
        }
    }
}

@Composable
private fun MobileOTPVerificationTextGroup(phoneNumber: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FiweLogoDark()

        Spacer(Modifier.height(36.dp))
        SecondaryMediumText(text = stringResource(Res.string.auth_code_sent_message, formatPhoneNumber(phoneNumber)))
    }
}

@Composable
fun MobileOTPVerificationActionGroup(
    onClickConfirm: () -> Unit,
    onClickResend: () -> Unit,
    isConfirmEnabled: Boolean,
    isResendEnabled: Boolean,
    isLoading: Boolean,
    countdownTime: Int,
    errorMessage: String?
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PrimaryLargeButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.auth_verify_action),
            onClick = onClickConfirm,
            isEnabled = isConfirmEnabled,
            isLoading = isLoading
        )

        errorMessage?.let { message ->
            Spacer(Modifier.height(16.dp))
            SkyText(
                text = message,
                styleType = TextStyleType.BodySmall,
                color = SkyFitColor.text.criticalOnBgFill
            )
        }

        Spacer(Modifier.height(16.dp))

        if (!isResendEnabled) {
            SecondaryMediumText(text = stringResource(Res.string.auth_resend_timer_message, countdownTime))
        } else {
            Row {
                SecondaryMediumText(text = stringResource(Res.string.auth_code_not_received_message))
                SecondaryMediumUnderlinedText(
                    text = stringResource(Res.string.auth_resend_code_action),
                    modifier = Modifier.clickable(onClick = onClickResend)
                )
            }
        }
    }
}