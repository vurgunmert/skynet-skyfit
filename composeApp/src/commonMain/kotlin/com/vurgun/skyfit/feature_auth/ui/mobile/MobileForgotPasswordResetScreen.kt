package com.vurgun.skyfit.feature_auth.ui.mobile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryLargeButton
import com.vurgun.skyfit.core.ui.components.text.input.PasswordTextInput
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.core.utils.KeyboardState
import com.vurgun.skyfit.core.utils.keyboardAsState
import com.vurgun.skyfit.feature_auth.ui.viewmodel.PasswordResetViewEvent
import com.vurgun.skyfit.feature_auth.ui.viewmodel.PasswordResetViewModel
import com.vurgun.skyfit.feature_navigation.MobileNavRoute
import com.vurgun.skyfit.feature_navigation.jumpAndTakeover
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.auth_password_input_hint
import skyfit.composeapp.generated.resources.auth_password_repeat_input_hint
import skyfit.composeapp.generated.resources.auth_password_reset_message
import skyfit.composeapp.generated.resources.auth_password_reset_title
import skyfit.composeapp.generated.resources.cancel_action
import skyfit.composeapp.generated.resources.continue_action

@Composable
fun MobileForgotPasswordResetScreen(navigator: Navigator) {
    val viewModel: PasswordResetViewModel = koinViewModel()

    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isSubmitEnabled by viewModel.isSubmitEnabled.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collectLatest { event ->
            when (event) {
                is PasswordResetViewEvent.Error -> {
                    errorMessage = event.message
                }
                PasswordResetViewEvent.GoToDashboard -> {
                    navigator.jumpAndTakeover(MobileNavRoute.Dashboard)
                }
            }
        }
    }

    val keyboardState by keyboardAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(keyboardState) {
        if (keyboardState is KeyboardState.Opened) {
            scrollState.animateScrollTo(keyboardState.heightPx)
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val passwordFocusRequester = remember { FocusRequester() }
    val confirmPasswordFocusRequester = remember { FocusRequester() }

    SkyFitMobileScaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(36.dp))
            SkyFitLogoComponent()
            Spacer(Modifier.height(48.dp))
            MobileForgotPasswordResetScreenTitleComponent()
            Spacer(Modifier.height(48.dp))

            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                PasswordTextInput(
                    value = password,
                    onValueChange = viewModel::setPassword,
                    hint = stringResource(Res.string.auth_password_input_hint),
                    focusRequester = passwordFocusRequester,
                    onKeyboardNextAction = {
                        confirmPasswordFocusRequester.requestFocus()
                    }
                )
                errorMessage?.let {
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = it,
                        style = SkyFitTypography.bodySmall.copy(color = SkyFitColor.text.criticalOnBgFill)
                    )
                }
                Spacer(Modifier.height(16.dp))
                PasswordTextInput(
                    value = confirmPassword,
                    onValueChange = viewModel::setConfirmPassword,
                    hint = stringResource(Res.string.auth_password_repeat_input_hint),
                    focusRequester = confirmPasswordFocusRequester,
                    onKeyboardDoneAction = {
                        keyboardController?.hide()
                    }
                )
            }

            Spacer(Modifier.height(24.dp))
            Spacer(Modifier.weight(1f))
            MobileForgotPasswordResetScreenActionsComponent(
                isLoading = isLoading,
                isEnabled = isSubmitEnabled,
                onClickContinue = viewModel::submitPasswordReset,
                onClickCancel = { navigator.jumpAndTakeover(MobileNavRoute.Login) }
            )
            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
private fun MobileForgotPasswordResetScreenTitleComponent() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(Res.string.auth_password_reset_title),
            style = SkyFitTypography.heading3
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.auth_password_reset_message),
            style = SkyFitTypography.bodyMediumRegular.copy(color = SkyFitColor.text.secondary)
        )
    }
}

@Composable
private fun MobileForgotPasswordResetScreenActionsComponent(
    isLoading: Boolean,
    isEnabled: Boolean,
    onClickContinue: () -> Unit,
    onClickCancel: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PrimaryLargeButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.continue_action),
            onClick = onClickContinue,
            isLoading = isLoading,
            isEnabled = isEnabled && !isLoading
        )

        Spacer(Modifier.height(14.dp))

        SecondaryLargeButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(Res.string.cancel_action),
            onClick = onClickCancel
        )
    }
}