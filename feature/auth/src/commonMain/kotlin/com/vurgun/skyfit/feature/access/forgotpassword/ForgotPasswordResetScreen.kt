package com.vurgun.skyfit.feature.access.forgotpassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonState
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.special.FiweLogoGroup
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.PasswordTextInput
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.KeyboardState
import com.vurgun.skyfit.core.ui.utils.keyboardAsState
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

class ForgotPasswordResetScreen : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<PasswordResetViewModel>()

        MobileForgotPasswordResetScreen(
            goToLogin = {
                appNavigator.popUntil(SharedScreen.Authorization)
            },
            goToDashboard = {
                appNavigator.replaceAll(SharedScreen.Main)
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
                .verticalScroll(scrollState)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            FiweLogoGroup(
                title = stringResource(Res.string.auth_password_reset_title),
                subtitle = stringResource(Res.string.auth_password_reset_message),
            )

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

            Spacer(Modifier.weight(1f))
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
        SkyButton(
            label = stringResource(Res.string.continue_action),
            onClick = onClickContinue,
            modifier = Modifier.fillMaxWidth(),
            variant = SkyButtonVariant.Primary,
            size = SkyButtonSize.Large,
            state = when {
                isLoading -> SkyButtonState.Loading
                !isEnabled -> SkyButtonState.Disabled
                    else -> SkyButtonState.Rest
            },
            enabled = isEnabled && !isLoading
        )

        Spacer(Modifier.height(14.dp))

        SkyButton(
            label =  stringResource(Res.string.cancel_action),
            onClick = onClickCancel,
            modifier = Modifier.fillMaxWidth(),
            variant = SkyButtonVariant.Secondary,
            size = SkyButtonSize.Large
        )
    }
}