package com.vurgun.skyfit.presentation.mobile.features.facility.profile

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
fun MobileFacilityProfileVisitedScreen(navigator: Navigator) {

    val scrollState = rememberScrollState()
    val photos: List<Any> = listOf(1,2,3)
    val trainers: List<Any> = listOf(1,2,3)
    val privateClasses: List<Any> = listOf(1,2,3)

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Box {
                MobileFacilityProfileVisitedScreenBackgroundImageComponent()
                MobileFacilityProfileVisitedScreenInfoCardComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (photos.isNotEmpty()) {
                MobileFacilityProfileVisitedScreenPhotosComponent()
            }

            if (trainers.isNotEmpty()) {
                MobileFacilityProfileVisitedScreenTrainersComponent()
            }

            if (privateClasses.isNotEmpty()) {
                MobileFacilityProfileVisitedScreenPrivateClassesComponent()
            }
        }
    }
}

@Composable
private fun MobileFacilityProfileVisitedScreenToolbarComponent() {
    TodoBox("MobileFacilityProfileVisitedScreenToolbarComponent", Modifier.size(430.dp, 64.dp))
}

@Composable
private fun MobileFacilityProfileVisitedScreenBackgroundImageComponent() {
    TodoBox("MobileFacilityProfileVisitedScreenBackgroundImageComponent", Modifier.size(430.dp, 240.dp))
}

@Composable
private fun MobileFacilityProfileVisitedScreenInfoCardComponent() {
    TodoBox("MobileFacilityProfileVisitedScreenInfoCardComponent", Modifier.size(398.dp, 412.dp))
}

@Composable
private fun MobileFacilityProfileVisitedScreenPhotosComponent() {
    TodoBox("MobileFacilityProfileVisitedScreenPhotosComponent", Modifier.size(398.dp, 414.dp))
}

@Composable
private fun MobileFacilityProfileVisitedScreenTrainersComponent() {
    TodoBox("MobileFacilityProfileVisitedScreenTrainersComponent", Modifier.size(398.dp, 350.dp))
}

@Composable
private fun MobileFacilityProfileVisitedScreenPrivateClassesComponent() {
    TodoBox("MobileFacilityProfileVisitedScreenPrivateClassesComponent", Modifier.size(398.dp, 416.dp))
}
