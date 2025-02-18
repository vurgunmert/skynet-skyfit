package com.vurgun.skyfit.presentation.mobile.features.user.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitPasswordInputComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitScaffold
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserSettingsChangePasswordScreen(navigator: Navigator) {
    val viewModel = remember { ChangePasswordViewModel() }
    val currentPassword by viewModel.currentPassword.collectAsState()
    val newPassword by viewModel.newPassword.collectAsState()
    val confirmedPassword by viewModel.confirmedPassword.collectAsState()
    val isSaveEnabled by viewModel.isSaveEnabled.collectAsState()

    SkyFitScaffold(
        topBar = {
            SkyFitScreenHeader(
                title = "Şifremi Değiştir",
                onClickBack = { navigator.popBackStack() }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Güncel Şifre", Modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)

                SkyFitPasswordInputComponent(
                    hint = "Güncel şifrenizi girin",
                    value = currentPassword,
                    onValueChange = viewModel::updateCurrentPassword
                )
            }

            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Yeni Şifre", Modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)

                SkyFitPasswordInputComponent(
                    hint = "Yeni şifrenizi girin",
                    value = newPassword,
                    onValueChange = viewModel::updateNewPassword
                )
            }

            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Yeni Şifre (Tekrar)", Modifier.padding(start = 8.dp), style = SkyFitTypography.bodySmallSemibold)

                SkyFitPasswordInputComponent(
                    hint = "Yeni şifrenizi tekrarlayın",
                    value = confirmedPassword,
                    onValueChange = viewModel::updateConfirmedPassword
                )
            }

            Spacer(Modifier.weight(1f))

            SkyFitButtonComponent(
                modifier = Modifier.fillMaxWidth(),
                text = "Şifreyi Değiştir",
                onClick = viewModel::saveChanged,
                variant = ButtonVariant.Primary,
                size = ButtonSize.Large,
                state = ButtonState.Rest,
                isEnabled = isSaveEnabled
            )
        }
    }
}
