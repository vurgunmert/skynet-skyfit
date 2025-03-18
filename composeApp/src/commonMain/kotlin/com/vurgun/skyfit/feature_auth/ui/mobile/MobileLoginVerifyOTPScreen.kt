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
import com.vurgun.skyfit.core.ui.components.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumText
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumUnderlinedText
import com.vurgun.skyfit.core.ui.components.text.input.CodeTextInput
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.core.utils.formatPhoneNumber
import com.vurgun.skyfit.feature_auth.ui.viewmodel.LoginOTPVerificationViewEvent
import com.vurgun.skyfit.feature_auth.ui.viewmodel.LoginOTPVerificationViewModel
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.auth_code_sent_to_phone
import skyfit.composeapp.generated.resources.auth_verify
import skyfit.composeapp.generated.resources.ota_code_not_received
import skyfit.composeapp.generated.resources.ota_code_resend_code
import skyfit.composeapp.generated.resources.resend_code_timer

@Composable
fun MobileLoginVerifyOTPScreen(navigator: Navigator) {
    val viewModel: LoginOTPVerificationViewModel = koinInject()

    val enteredOtp by viewModel.enteredOtp.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isResendEnabled by viewModel.isResendEnabled.collectAsState()
    val countdownTime by viewModel.countdownTime.collectAsState()
    val otpLength = viewModel.otpLength

    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is LoginOTPVerificationViewEvent.GoToRegister -> {
                    navigator.jumpAndTakeover(NavigationRoute.CreatePassword)
                }

                is LoginOTPVerificationViewEvent.GoToDashboard -> {
                    navigator.jumpAndTakeover(NavigationRoute.Dashboard)
                }

                is LoginOTPVerificationViewEvent.GoToOnboarding -> {
                    navigator.jumpAndTakeover(NavigationRoute.Onboarding)
                }

                is LoginOTPVerificationViewEvent.ShowError -> {
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
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {

            MobileOTPVerificationTextGroup(viewModel.phoneNumber)

            MobileOTPVerificationContentGroup(otpLength, onOtpCompleted = viewModel::onOtpChanged)

            MobileOTPVerificationActionGroup(
                onClickConfirm = viewModel::onConfirmClicked,
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
        SecondaryMediumText(text = stringResource(Res.string.auth_code_sent_to_phone, formatPhoneNumber(phoneNumber)))
    }
}

@Composable
fun MobileOTPVerificationContentGroup(
    codeLength: Int = 4,
    onOtpCompleted: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CodeTextInput(
            codeLength = codeLength,
            onOtpCompleted = onOtpCompleted
        )
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
            text = stringResource(Res.string.auth_verify),
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
            SecondaryMediumText(text = stringResource(Res.string.resend_code_timer, countdownTime))
        } else {
            Row {
                SecondaryMediumText(text = stringResource(Res.string.ota_code_not_received))
                SecondaryMediumUnderlinedText(
                    text = stringResource(Res.string.ota_code_resend_code),
                    modifier = Modifier.clickable(onClick = onClickResend)
                )
            }
        }
    }
}