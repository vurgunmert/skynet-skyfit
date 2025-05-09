package com.vurgun.skyfit.feature.access.forgotpassword

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.popUntil
import com.vurgun.skyfit.core.navigation.replaceAll
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.button.SecondaryLargeButton
import com.vurgun.skyfit.core.ui.components.special.SkyFitLogoComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.PasswordTextInput
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.KeyboardState
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.keyboardAsState
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.auth_password_input_hint
import skyfit.core.ui.generated.resources.auth_password_repeat_input_hint
import skyfit.core.ui.generated.resources.auth_password_reset_message
import skyfit.core.ui.generated.resources.auth_password_reset_title
import skyfit.core.ui.generated.resources.cancel_action
import skyfit.core.ui.generated.resources.continue_action

class ForgotPasswordResetScreen : Screen {

    @Composable
    override fun Content() {
        val windowSize = LocalWindowSize.current
        val appNavigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<PasswordResetViewModel>()

        MobileForgotPasswordResetScreen(
            goToLogin = {
                appNavigator.popUntil(SharedScreen.Authorization)
            },
            goToDashboard = {
                appNavigator.replaceAll(SharedScreen.Dashboard)
            },
            viewModel = viewModel
        )
    }
}

@Composable
fun MobileForgotPasswordResetScreen(
    goToLogin: () -> Unit,
    goToDashboard: () -> Unit,
    viewModel: PasswordResetViewModel
) {

    val password by viewModel.password.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isSubmitEnabled by viewModel.isSubmitEnabled.collectAsState()
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(viewModel) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is PasswordResetViewEvent.Error -> {
                    errorMessage = effect.message
                }

                PasswordResetViewEvent.GoToDashboard -> {
                    goToDashboard()
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
            MobileForgotPasswordResetScreenActionsComponent(
                isLoading = isLoading,
                isEnabled = isSubmitEnabled,
                onClickContinue = viewModel::submitPasswordReset,
                onClickCancel = goToLogin
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