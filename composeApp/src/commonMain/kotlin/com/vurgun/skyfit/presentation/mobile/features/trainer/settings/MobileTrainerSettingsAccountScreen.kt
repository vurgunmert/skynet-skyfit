package com.vurgun.skyfit.presentation.mobile.features.trainer.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileTrainerSettingsAccountScreen(navigator: Navigator) {

    val showDeleteConfirm: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileTrainerSettingsAccountScreenToolbarComponent()
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileTrainerSettingsAccountScreenDeleteConfirmComponent()
            } else {
                MobileTrainerSettingsAccountScreenSaveActionComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileTrainerSettingsAccountScreenInputComponent()
            MobileTrainerSettingsAccountScreenChangePasswordActionComponent()
            MobileTrainerSettingsAccountScreenDeleteAccountActionComponent()
        }
    }
}


@Composable
private fun MobileTrainerSettingsAccountScreenToolbarComponent() {
    TodoBox("MobileTrainerSettingsAccountScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileTrainerSettingsAccountScreenInputComponent() {
    TodoBox("MobileTrainerSettingsAccountScreenInputComponent", Modifier.size(382.dp, 1036.dp))
}

@Composable
private fun MobileTrainerSettingsAccountScreenSaveActionComponent() {
    TodoBox("MobileTrainerSettingsAccountScreenSaveActionComponent", Modifier.size(382.dp, 48.dp))
}

@Composable
private fun MobileTrainerSettingsAccountScreenChangePasswordActionComponent() {
    TodoBox("MobileTrainerSettingsAccountScreenChangePasswordActionComponent", Modifier.size(382.dp, 56.dp))
}

@Composable
private fun MobileTrainerSettingsAccountScreenDeleteAccountActionComponent() {
    TodoBox("MobileTrainerSettingsAccountScreenDeleteAccountActionComponent", Modifier.size(382.dp, 56.dp))
}

@Composable
private fun MobileTrainerSettingsAccountScreenDeleteConfirmComponent() {
    TodoBox("MobileTrainerSettingsAccountScreenDeleteConfirmComponent", Modifier.size(382.dp, 336.dp))
}