package com.vurgun.skyfit.presentation.mobile.features.facility.profile

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
fun MobileFacilityProfileScreen(navigator: Navigator) {

    val scrollState = rememberScrollState()
    val photos: List<Any> = emptyList()
    val trainers: List<Any> = emptyList()
    val privateClasses: List<Any> = emptyList()

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Box {
                MobileFacilityProfileScreenBackgroundImageComponent()
                MobileFacilityProfileScreenInfoCardComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (photos.isEmpty()) {
                MobileFacilityProfileScreenPhotosEmptyComponent()
            } else {
                MobileFacilityProfileScreenPhotosComponent()
            }

            if (trainers.isEmpty()) {
                MobileFacilityProfileScreenTrainersEmptyComponent()
            } else {
                MobileFacilityProfileScreenTrainersComponent()
            }

            if (privateClasses.isEmpty()) {
                MobileFacilityProfileScreenPrivateClassesEmptyComponent()
            } else {
                MobileFacilityProfileScreenPrivateClassesComponent()
            }
        }
    }
}

@Composable
private fun MobileFacilityProfileScreenBackgroundImageComponent() {
    TodoBox("MobileFacilityProfileScreenBackgroundImageComponent", Modifier.size(430.dp, 240.dp))
}

@Composable
private fun MobileFacilityProfileScreenInfoCardComponent() {
    TodoBox("MobileFacilityProfileScreenInfoCardComponent", Modifier.size(398.dp, 284.dp))
}

@Composable
private fun MobileFacilityProfileScreenPhotosComponent() {
    TodoBox("MobileFacilityProfileScreenPhotosComponent", Modifier.size(398.dp, 414.dp))
}

@Composable
private fun MobileFacilityProfileScreenPhotosEmptyComponent() {
    TodoBox("MobileFacilityProfileScreenPhotosEmptyComponent", Modifier.size(398.dp, 390.dp))
}

@Composable
private fun MobileFacilityProfileScreenTrainersComponent() {
    TodoBox("MobileFacilityProfileScreenTrainersComponent", Modifier.size(398.dp, 350.dp))
}

@Composable
private fun MobileFacilityProfileScreenTrainersEmptyComponent() {
    TodoBox("MobileFacilityProfileScreenTrainersEmptyComponent", Modifier.size(398.dp, 177.dp))
}

@Composable
private fun MobileFacilityProfileScreenPrivateClassesComponent() {
    TodoBox("MobileFacilityProfileScreenPrivateClassesComponent", Modifier.size(398.dp, 416.dp))
}

@Composable
private fun MobileFacilityProfileScreenPrivateClassesEmptyComponent() {
    TodoBox("MobileFacilityProfileScreenPrivateClassesEmptyComponent", Modifier.size(398.dp, 177.dp))
}
