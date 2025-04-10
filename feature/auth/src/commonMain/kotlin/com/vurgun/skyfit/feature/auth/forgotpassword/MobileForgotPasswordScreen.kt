package com.vurgun.skyfit.feature.auth.forgotpassword

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.ui.core.components.button.PrimaryLargeButton
import com.vurgun.skyfit.ui.core.components.button.SecondaryLargeButton
import com.vurgun.skyfit.ui.core.components.special.SkyFitLogoComponent
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.text.PhoneNumberTextInput
import com.vurgun.skyfit.ui.core.styling.LocalPadding
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.auth_forgot_password_action
import skyfit.ui.core.generated.resources.auth_forgot_password_prompt
import skyfit.ui.core.generated.resources.cancel_action
import skyfit.ui.core.generated.resources.continue_action

@Composable
fun MobileForgotPasswordScreen(
    goToBack: () -> Unit,
    goToVerify: () -> Unit,
) {
    val viewModel: ForgotPasswordViewModel = koinInject()
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val isSubmitEnabled by viewModel.isSubmitEnabled.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var errorMessage by remember { mutableStateOf<String?>(null) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                ForgotPasswordViewEvent.GoToOTPVerification -> {
                    goToVerify()
                }

                is ForgotPasswordViewEvent.ShowError -> {
                    errorMessage = event.message
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier
                .padding(LocalPadding.current.medium)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            SkyFitLogoComponent()
            Spacer(Modifier.height(36.dp))
            MobileForgotPasswordScreenTitleComponent()
            Spacer(Modifier.height(48.dp))

            PhoneNumberTextInput(
                value = phoneNumber,
                onValueChange = viewModel::onPhoneNumberChanged,
                focusRequester = focusRequester
            )

            Spacer(Modifier.weight(1f))

            PrimaryLargeButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.continue_action),
                onClick = viewModel::submitForgotPassword,
                isLoading = isLoading,
                isEnabled = isSubmitEnabled && !isLoading
            )

            Spacer(Modifier.height(14.dp))

            SecondaryLargeButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.cancel_action),
                onClick = goToBack
            )
        }
    }
}


@Composable
private fun MobileForgotPasswordScreenTitleComponent() {
    Text(
        text = stringResource(Res.string.auth_forgot_password_action),
        style = SkyFitTypography.heading3
    )
    Spacer(Modifier.height(16.dp))
    Text(
        text = stringResource(Res.string.auth_forgot_password_prompt),
        style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
    )
}
