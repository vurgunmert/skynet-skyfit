package com.vurgun.skyfit.feature.auth.forgotpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.vurgun.skyfit.feature.auth.login.MobileOTPVerificationActionGroup
import com.vurgun.skyfit.ui.core.components.special.SkyFitLogoComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.text.SecondaryMediumText
import com.vurgun.skyfit.ui.core.styling.LocalPadding
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import com.vurgun.skyfit.ui.core.utils.KeyboardState
import com.vurgun.skyfit.ui.core.utils.formatPhoneNumber
import com.vurgun.skyfit.ui.core.utils.keyboardAsState
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.auth_code_sent_message
import skyfit.ui.core.generated.resources.auth_forgot_password_action

@Composable
fun MobileForgotPasswordVerifyOTPScreen(
    goToReset: () -> Unit
) {
    val viewModel: ForgotPasswordVerifyOTPViewModel = koinInject()

    val enteredOtp by viewModel.enteredOtp.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isResendEnabled by viewModel.isResendEnabled.collectAsState()
    val countdownTime by viewModel.countdownTime.collectAsState()
    val phoneNumber by viewModel.phoneNumber.collectAsState("")

    var errorMessage by remember { mutableStateOf<String?>(null) }

    val keyboardState by keyboardAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(keyboardState) {
        if (keyboardState is KeyboardState.Opened) {
            scrollState.animateScrollTo(keyboardState.heightPx)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                ForgotPasswordVerifyOTPViewEvent.GoToResetPassword -> { goToReset() }

                is ForgotPasswordVerifyOTPViewEvent.ShowError -> {
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

            MobileForgotPasswordCodeTextGroup(phoneNumber)

            OTPInputTextField(onCodeReady = viewModel::onOtpChanged)

            MobileOTPVerificationActionGroup(
                onClickConfirm = viewModel::onConfirmClicked,
                onClickResend = viewModel::resendOTP,
                isConfirmEnabled = enteredOtp.length == 6 && !isLoading,
                isResendEnabled = isResendEnabled,
                isLoading = isLoading,
                countdownTime = countdownTime,
                errorMessage = errorMessage
            )
        }
    }
}


@Composable
private fun MobileForgotPasswordCodeTextGroup(phoneNumber: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SkyFitLogoComponent()

        Spacer(Modifier.height(36.dp))

        Text(
            text = stringResource(Res.string.auth_forgot_password_action),
            style = SkyFitTypography.heading3
        )
        Spacer(Modifier.height(16.dp))

        SecondaryMediumText(text = stringResource(Res.string.auth_code_sent_message, formatPhoneNumber(phoneNumber)))
    }
}