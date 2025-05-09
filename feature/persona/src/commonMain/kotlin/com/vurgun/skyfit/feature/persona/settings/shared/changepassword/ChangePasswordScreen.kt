package com.vurgun.skyfit.feature.persona.settings.shared.changepassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.text.PasswordInputText
import com.vurgun.skyfit.core.ui.components.text.SingleLineInputText
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.settings_change_my_password_label
import skyfit.core.ui.generated.resources.settings_change_password_action
import skyfit.core.ui.generated.resources.settings_current_password_hint
import skyfit.core.ui.generated.resources.settings_current_password_label
import skyfit.core.ui.generated.resources.settings_new_password_again_hint
import skyfit.core.ui.generated.resources.settings_new_password_again_label
import skyfit.core.ui.generated.resources.settings_new_password_hint
import skyfit.core.ui.generated.resources.settings_new_password_label

class ChangePasswordScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<ChangePasswordViewModel>()

        SettingsChangePasswordScreen(
            goToBack = { navigator.pop() },
            viewModel = viewModel
        )
    }

}

@Composable
private fun SettingsChangePasswordScreen(
    goToBack: () -> Unit,
    viewModel: ChangePasswordViewModel
) {

    val currentPassword by viewModel.currentPassword.collectAsState()
    val newPassword by viewModel.newPassword.collectAsState()
    val confirmedPassword by viewModel.confirmedPassword.collectAsState()
    val isSaveEnabled by viewModel.isSaveEnabled.collectAsState()

    val passwordFocusRequester = FocusRequester()
    val againPasswordFocusRequester = FocusRequester()

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = stringResource(Res.string.settings_change_my_password_label),
                onClickBack = goToBack
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
                onValueChange = viewModel::updateCurrentPassword
            )

            PasswordInputText(
                title = stringResource(Res.string.settings_new_password_label),
                hint = stringResource(Res.string.settings_new_password_hint),
                value = newPassword,
                focusRequester = passwordFocusRequester,
                nextFocusRequester = againPasswordFocusRequester,
                onValueChange = viewModel::updateNewPassword
            )

            PasswordInputText(
                title = stringResource(Res.string.settings_new_password_again_label),
                hint = stringResource(Res.string.settings_new_password_again_hint),
                value = confirmedPassword,
                focusRequester = againPasswordFocusRequester,
                onValueChange = viewModel::updateConfirmedPassword
            )

            Spacer(Modifier.weight(1f))

            PrimaryLargeButton(
                text = stringResource(Res.string.settings_change_password_action),
                modifier = Modifier.fillMaxWidth(),
                isEnabled = isSaveEnabled,
                onClick = viewModel::saveChanged
            )
        }
    }
}
