package com.vurgun.skyfit.feature.access.forgotpassword

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.special.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumText
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.KeyboardState
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.formatPhoneNumber
import com.vurgun.skyfit.core.ui.utils.keyboardAsState
import com.vurgun.skyfit.feature.access.component.OTPInputTextField
import com.vurgun.skyfit.feature.access.login.MobileOTPVerificationActionGroup
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.auth_code_sent_message
import skyfit.core.ui.generated.resources.auth_forgot_password_action

class ForgotPasswordVerifyOTPScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val appNavigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ForgotPasswordVerifyOTPViewModel>()

        MobileForgotPasswordVerifyOTPScreen(
            goToReset = { appNavigator.replace(ForgotPasswordResetScreen()) },
            viewModel = viewModel
        )
    }
}

@Composable
private fun MobileForgotPasswordVerifyOTPScreen(
    goToReset: () -> Unit,
    viewModel: ForgotPasswordVerifyOTPViewModel
) {

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
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                ForgotPasswordVerifyOTPEffect.GoToResetPassword -> {
                    goToReset()
                }

                is ForgotPasswordVerifyOTPEffect.ShowError -> {
                    errorMessage = effect.message
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