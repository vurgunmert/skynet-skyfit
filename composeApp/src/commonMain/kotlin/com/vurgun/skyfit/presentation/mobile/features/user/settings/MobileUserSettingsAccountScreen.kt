package com.vurgun.skyfit.presentation.mobile.features.user.settings

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
import com.vurgun.skyfit.presentation.shared.components.SkyFitScreenHeader
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserSettingsAccountScreen(navigator: Navigator) {

    val showDeleteConfirm: Boolean = false

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            SkyFitScreenHeader("Hesap Ayarlari", onBackClick = { navigator.popBackStack() })
        },
        bottomBar = {
            if (showDeleteConfirm) {
                MobileUserSettingsScreenDeleteConfirmComponent()
            } else {
                MobileUserSettingsScreenSaveActionComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserSettingsScreenInputComponent()
        }
    }
}

@Composable
private fun MobileUserSettingsScreenInputComponent() {
    TodoBox("MobileUserSettingsScreenInputComponent", Modifier.size(382.dp, 1184.dp))
}

@Composable
private fun MobileUserSettingsScreenSaveActionComponent() {
    TodoBox("MobileUserSettingsScreenDeleteAccountActionComponent", Modifier.size(382.dp, 48.dp))
}

@Composable
private fun MobileUserSettingsScreenChangePasswordActionComponent() {
    TodoBox("MobileUserSettingsScreenChangePasswordActionComponent", Modifier.size(382.dp, 56.dp))
}

@Composable
private fun MobileUserSettingsScreenDeleteAccountActionComponent() {
    TodoBox("MobileUserSettingsScreenDeleteAccountActionComponent", Modifier.size(382.dp, 56.dp))
}

@Composable
private fun MobileUserSettingsScreenDeleteConfirmComponent() {
    TodoBox("MobileUserSettingsScreenDeleteConfirmComponent", Modifier.size(382.dp, 336.dp))
}