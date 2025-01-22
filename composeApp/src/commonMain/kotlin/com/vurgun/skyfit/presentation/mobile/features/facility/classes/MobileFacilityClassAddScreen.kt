package com.vurgun.skyfit.presentation.mobile.features.facility.classes

import androidx.compose.foundation.layout.Box
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
fun MobileFacilityClassAddScreen(navigator: Navigator) {

    val showCancelAction: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileFacilityClassAddScreenToolbarComponent()
        },
        bottomBar = {
            MobileFacilityClassAddScreenActionComponent()
        }
    ) {
        Box {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MobileFacilityClassAddScreenInputComponent()
            }

            if (showCancelAction) {
                MobileFacilityClassAddScreenCancelActionComponent()
            }
        }
    }
}

@Composable
private fun MobileFacilityClassAddScreenToolbarComponent() {
    TodoBox("MobileFacilityClassAddScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileFacilityClassAddScreenInputComponent() {
    TodoBox("MobileFacilityClassAddScreenInputComponent", Modifier.size(430.dp, 1071.dp))
}

@Composable
private fun MobileFacilityClassAddScreenActionComponent() {
    TodoBox("MobileFacilityClassAddScreenActionComponent", Modifier.size(430.dp, 48.dp))
}

@Composable
private fun MobileFacilityClassAddScreenCancelActionComponent() {
    TodoBox("MobileFacilityClassAddScreenActionComponent", Modifier.size(382.dp, 196.dp))
}

