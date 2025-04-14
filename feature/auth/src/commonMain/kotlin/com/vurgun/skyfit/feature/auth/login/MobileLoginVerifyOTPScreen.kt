package com.vurgun.skyfit.feature.auth.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
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
import com.vurgun.skyfit.feature.auth.component.OTPInputTextField
import com.vurgun.skyfit.ui.core.components.button.PrimaryLargeButton
import com.vurgun.skyfit.ui.core.components.special.SkyFitLogoComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.text.SecondaryMediumText
import com.vurgun.skyfit.ui.core.components.text.SecondaryMediumUnderlinedText
import com.vurgun.skyfit.ui.core.styling.LocalPadding
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import com.vurgun.skyfit.ui.core.utils.formatPhoneNumber
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.auth_code_not_received_message
import skyfit.ui.core.generated.resources.auth_code_sent_message
import skyfit.ui.core.generated.resources.auth_resend_code_action
import skyfit.ui.core.generated.resources.auth_resend_timer_message
import skyfit.ui.core.generated.resources.auth_verify_action

@Composable
fun MobileLoginVerifyOTPScreen(
    goToCreatePassword: () -> Unit,
    goToDashboard: () -> Unit,
    goToOnboarding: () -> Unit,
) {
    val viewModel: LoginOTPVerificationViewModel = koinInject()

    val enteredOtp by viewModel.enteredOtp.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState("")
    val isResendEnabled by viewModel.isResendEnabled.collectAsState()
    val countdownTime by viewModel.countdownTime.collectAsState()
    val otpLength = viewModel.otpLength

    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is LoginOTPVerificationViewEvent.GoToRegister -> {
                    goToCreatePassword()
                }

                is LoginOTPVerificationViewEvent.GoToDashboard -> {
                    goToDashboard()
                }

                is LoginOTPVerificationViewEvent.GoToOnboarding -> {
                    goToOnboarding()
                }

                is LoginOTPVerificationViewEvent.ShowError -> {
                    errorMessage = event.message
                }
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
        SkyFitLogoComponent()

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

        errorMessage?.let {
            Spacer(Modifier.height(16.dp))
            Text(
                text = it,
                style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.criticalOnBgFill)
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