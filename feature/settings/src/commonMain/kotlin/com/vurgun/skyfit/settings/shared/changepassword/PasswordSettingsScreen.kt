package com.vurgun.skyfit.settings.shared.changepassword

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.ui.components.button.PrimaryLargeButton
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.text.PasswordInputText
import com.vurgun.skyfit.core.ui.components.text.SingleLineInputText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.settings.component.SettingsCompactComponent
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.*

class PasswordSettingsScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<PasswordSettingsViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        CollectEffect(viewModel.uiEffect) { effect ->
            when (effect) {
                PasswordSettingsUiEffect.NavigateBack -> navigator.pop()
            }
        }

        when (uiState) {
            is PasswordSettingsUiState.Error -> {
                ErrorScreen(message = (uiState as PasswordSettingsUiState.Error).message, onConfirm = {
                    navigator.pop()
                })
            }

            PasswordSettingsUiState.Idle -> SettingsChangePasswordScreen(viewModel)
            PasswordSettingsUiState.Loading -> FullScreenLoaderContent()
        }
    }
}

@Composable
private fun SettingsChangePasswordScreen(
    viewModel: PasswordSettingsViewModel
) {
    val currentPassword by viewModel.currentPassword.collectAsState()
    val newPassword by viewModel.newPassword.collectAsState()
    val confirmedPassword by viewModel.confirmedPassword.collectAsState()
    val isSaveEnabled by viewModel.isSaveEnabled.collectAsState()

    val passwordFocusRequester = FocusRequester()
    val againPasswordFocusRequester = FocusRequester()

    SettingsCompactComponent.Layout(
        topbar = {
            CompactTopBar(
                title = stringResource(Res.string.settings_change_my_password_label),
                onClickBack = { viewModel.onAction(PasswordSettingsUiAction.OnClickBack) }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            SingleLineInputText(
                title = stringResource(Res.string.settings_current_password_label),
                hint = stringResource(Res.string.settings_current_password_hint),
                value = currentPassword,
                nextFocusRequester = passwordFocusRequester,
                onValueChange = { viewModel.onAction(PasswordSettingsUiAction.OnCurrentPasswordChanged(it)) }
            )

            PasswordInputText(
                title = stringResource(Res.string.settings_new_password_label),
                hint = stringResource(Res.string.settings_new_password_hint),
                value = newPassword,
                focusRequester = passwordFocusRequester,
                nextFocusRequester = againPasswordFocusRequester,
                onValueChange = { viewModel.onAction(PasswordSettingsUiAction.OnNewPasswordChanged(it)) }
            )

            PasswordInputText(
                title = stringResource(Res.string.settings_new_password_again_label),
                hint = stringResource(Res.string.settings_new_password_again_hint),
                value = confirmedPassword,
                focusRequester = againPasswordFocusRequester,
                onValueChange = { viewModel.onAction(PasswordSettingsUiAction.OnAgainPasswordChanged(it)) }
            )

            Spacer(Modifier.weight(1f))

            PrimaryLargeButton(
                text = stringResource(Res.string.settings_change_password_action),
                modifier = Modifier.fillMaxWidth(),
                isEnabled = isSaveEnabled,
                onClick = { viewModel.onAction(PasswordSettingsUiAction.OnClickSubmit) }
            )
        }
    }
}
