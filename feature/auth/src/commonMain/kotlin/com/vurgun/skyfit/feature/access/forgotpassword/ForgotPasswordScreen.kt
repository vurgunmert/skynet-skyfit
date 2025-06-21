package com.vurgun.skyfit.feature.access.forgotpassword

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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonState
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.special.FiweLogoDark
import com.vurgun.skyfit.core.ui.components.special.FiweLogoGroup
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.PhoneNumberTextInput
import com.vurgun.skyfit.core.ui.styling.LocalPadding
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.auth_forgot_password_action
import skyfit.core.ui.generated.resources.auth_forgot_password_prompt
import skyfit.core.ui.generated.resources.cancel_action
import skyfit.core.ui.generated.resources.continue_action

class ForgotPasswordScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val appNavigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ForgotPasswordViewModel>()

        MobileForgotPasswordScreen(
            goToBack = { appNavigator.pop() },
            goToVerify = {
                appNavigator.replace(ForgotPasswordVerifyOTPScreen())
            },
            viewModel = viewModel
        )
    }

}

@Composable
private fun MobileForgotPasswordScreen(
    goToBack: () -> Unit,
    goToVerify: () -> Unit,
    viewModel: ForgotPasswordViewModel
) {
    val phoneNumber by viewModel.phoneNumber.collectAsState()
    val isSubmitEnabled by viewModel.isSubmitEnabled.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var errorMessage by remember { mutableStateOf<String?>(null) }
    val focusRequester = remember { FocusRequester() }

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            ForgotPasswordEffect.GoToOTPVerification -> {
                goToVerify()
            }

            is ForgotPasswordEffect.ShowError -> {
                errorMessage = effect.message
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
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            FiweLogoGroup(
                title = stringResource(Res.string.auth_forgot_password_action),
                subtitle =  stringResource(Res.string.auth_forgot_password_prompt)
            )

            Spacer(Modifier.height(48.dp))

            PhoneNumberTextInput(
                value = phoneNumber,
                onValueChange = viewModel::onPhoneNumberChanged,
                focusRequester = focusRequester
            )

            Spacer(Modifier.weight(1f))

            SkyButton(
                label = stringResource(Res.string.continue_action),
                onClick = viewModel::submitForgotPassword,
                modifier = Modifier.fillMaxWidth(),
                variant = SkyButtonVariant.Primary,
                size = SkyButtonSize.Large,
                state = when {
                    isLoading -> SkyButtonState.Loading
                    !isSubmitEnabled -> SkyButtonState.Disabled
                    else -> SkyButtonState.Rest
                },
                enabled = isSubmitEnabled && !isLoading
            )

            Spacer(Modifier.height(14.dp))
            
            SkyButton(
                label =  stringResource(Res.string.cancel_action),
                onClick = goToBack,
                modifier = Modifier.fillMaxWidth(),
                variant = SkyButtonVariant.Secondary,
                size = SkyButtonSize.Large
            )
            Spacer(Modifier.height(48.dp))
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
