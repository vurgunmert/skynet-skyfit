package com.vurgun.skyfit.presentation.mobile.features.facility.classes

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
fun MobileFacilityClassDetailScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileFacilityClassDetailScreenToolbarComponent()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            MobileFacilityClassDetailComponent()
        }
    }
}

@Composable
private fun MobileFacilityClassDetailScreenToolbarComponent() {
    TodoBox("MobileFacilityClassDetailScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileFacilityClassDetailComponent() {
    TodoBox("MobileFacilityClassDetailScreenInputComponent", Modifier.size(430.dp, 480.dp))
}