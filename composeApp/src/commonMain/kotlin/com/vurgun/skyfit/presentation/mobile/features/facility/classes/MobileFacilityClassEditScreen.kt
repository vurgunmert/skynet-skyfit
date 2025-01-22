package com.vurgun.skyfit.presentation.mobile.features.facility.classes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileFacilityClassEditScreen(navigator: Navigator) {
    
    val showCancelAction: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileFacilityClassEditScreenToolbarComponent()
        },
        bottomBar = {
            MobileFacilityClassEditScreenActionComponent()
        }
    ) {
        Box {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MobileFacilityClassEditScreenInputComponent()
            }

            if (showCancelAction) {
                MobileFacilityClassEditScreenCancelActionComponent()
            }
        }
    }
}

@Composable
private fun MobileFacilityClassEditScreenToolbarComponent() {
    TodoBox("MobileFacilityClassEditScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileFacilityClassEditScreenInputComponent() {
    TodoBox("MobileFacilityClassEditScreenInputComponent", Modifier.size(430.dp, 1071.dp))
}

@Composable
private fun MobileFacilityClassEditScreenActionComponent() {
    TodoBox("MobileFacilityClassEditScreenActionComponent", Modifier.size(430.dp, 48.dp))
}

@Composable
private fun MobileFacilityClassEditScreenCancelActionComponent() {
    TodoBox("MobileFacilityClassEditScreenActionComponent", Modifier.size(382.dp, 196.dp))
}
