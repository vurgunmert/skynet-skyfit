package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
fun MobileUserPhotoDiaryScreen(navigator: Navigator) {

    var dietsExpanded: Boolean = false
    var exercisesExpanded: Boolean = false
    var showAddPhoto: Boolean = true

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileUserPhotoDiaryScreenToolbarComponent()
        }
    ) {
        Box(Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                MobileUserPhotoDiaryScreenDietsHeaderComponent()

                if (dietsExpanded) {
                    MobileUserPhotoDiaryScreenDietsComponent()
                } else {
                    MobileUserPhotoDiaryScreenDietsCompactComponent()
                }

                Spacer(Modifier.height(24.dp))

                MobileUserPhotoDiaryScreenExercisesHeaderComponent()

                if (exercisesExpanded) {
                    MobileUserPhotoDiaryScreenExercisesComponent()
                } else {
                    MobileUserPhotoDiaryScreenExercisesCompactComponent()
                }
            }

            if (showAddPhoto) {
                MobileUserPhotoDiaryScreenAddPhotoComponent()
            }

        }
    }
}

@Composable
private fun MobileUserPhotoDiaryScreenToolbarComponent() {
    TodoBox("MobileUserPhotoDiaryScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileUserPhotoDiaryScreenDietsHeaderComponent() {
    TodoBox("MobileUserPhotoDiaryScreenDietsHeaderComponent", Modifier.size(430.dp, 74.dp))
}

@Composable
private fun MobileUserPhotoDiaryScreenDietsCompactComponent() {
    TodoBox("MobileUserPhotoDiaryScreenDietsHeaderComponent", Modifier.size(398.dp, 414.dp))
}

@Composable
private fun MobileUserPhotoDiaryScreenDietsComponent() {
    TodoBox("MobileUserPhotoDiaryScreenDietsHeaderComponent", Modifier.size(430.dp, 1258.dp))
}

@Composable
private fun MobileUserPhotoDiaryScreenExercisesHeaderComponent() {
    TodoBox("MobileUserPhotoDiaryScreenExercisesHeaderComponent", Modifier.size(430.dp, 74.dp))
}

@Composable
private fun MobileUserPhotoDiaryScreenExercisesCompactComponent() {
    TodoBox("MobileUserPhotoDiaryScreenExercisesCompactComponent", Modifier.size(398.dp, 414.dp))
}

@Composable
private fun MobileUserPhotoDiaryScreenExercisesComponent() {
    TodoBox("MobileUserPhotoDiaryScreenExercisesComponent", Modifier.size(430.dp, 1258.dp))
}


@Composable
private fun MobileUserPhotoDiaryScreenAddPhotoComponent() {
    TodoBox("MobileUserPhotoDiaryScreenAddPhotoComponent", Modifier.size(398.dp, 518.dp))
}
