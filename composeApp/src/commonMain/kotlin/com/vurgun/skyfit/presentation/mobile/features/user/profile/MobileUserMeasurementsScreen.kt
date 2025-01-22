package com.vurgun.skyfit.presentation.mobile.features.user.profile

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
fun MobileUserMeasurementsScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                MobileUserMeasurementsScreenToolbarComponent()
                MobileUserMeasurementsScreenSearchComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileUserMeasurementsComponent()
        }
    }
}

@Composable
private fun MobileUserMeasurementsScreenToolbarComponent() {
    TodoBox("MobileUserMeasurementsScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileUserMeasurementsScreenSearchComponent() {
    TodoBox("MobileUserMeasurementsScreenSearchComponent", Modifier.size(382.dp, 44.dp))
}

@Composable
private fun MobileUserMeasurementsComponent() {
    TodoBox("MobileUserMeasurementsComponent", Modifier.size(430.dp, 558.dp))
}