package com.vurgun.skyfit.feature_auth.ui.mobile

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
import com.vurgun.skyfit.core.ui.components.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.SecondaryMediumText
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.core.utils.KeyboardState
import com.vurgun.skyfit.core.utils.formatPhoneNumber
import com.vurgun.skyfit.core.utils.keyboardAsState
import com.vurgun.skyfit.feature_auth.ui.component.OTPInputTextField
import com.vurgun.skyfit.feature_auth.ui.viewmodel.ForgotPasswordVerifyOTPViewEvent
import com.vurgun.skyfit.feature_auth.ui.viewmodel.ForgotPasswordVerifyOTPViewModel
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.auth_code_sent_message
import skyfit.composeapp.generated.resources.auth_forgot_password_action

@Composable
fun MobileForgotPasswordVerifyOTPScreen(navigator: Navigator) {
    val viewModel: ForgotPasswordVerifyOTPViewModel = koinInject()

    val enteredOtp by viewModel.enteredOtp.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isResendEnabled by viewModel.isResendEnabled.collectAsState()
    val countdownTime by viewModel.countdownTime.collectAsState()

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
                ForgotPasswordVerifyOTPViewEvent.GoToResetPassword -> {
                    navigator.jumpAndTakeover(MobileNavRoute.ForgotPasswordReset)
                }

                is ForgotPasswordVerifyOTPViewEvent.ShowError -> {
                    errorMessage = event.message
                }
            }
        }
    }

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier
                .padding(SkyFitStyleGuide.Padding.xLarge)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {

            MobileForgotPasswordCodeTextGroup(viewModel.phoneNumber)

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