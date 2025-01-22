package com.vurgun.skyfit.presentation.mobile.features.user.exercises

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
fun MobileUserExerciseInActionCompletedScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        bottomBar = {
            MobileUserExerciseInActionCompletedScreenCompleteActionComponent()
        }
    ) {

        Box(Modifier.fillMaxSize()) {
            MobileUserExerciseInActionCompletedScreenImageComponent()
            MobileUserExerciseInActionCompletedScreenToolbarComponent()

            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(251.dp))
                MobileUserExerciseInActionCompletedScreenInfoComponent()
            }
        }
    }
}

@Composable
private fun MobileUserExerciseInActionCompletedScreenToolbarComponent() {
    TodoBox("MobileUserExerciseInActionCompletedScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileUserExerciseInActionCompletedScreenImageComponent() {
    TodoBox("MobileUserExerciseInActionCompletedScreenImageComponent", Modifier.size(430.dp, 295.dp))
}

@Composable
private fun MobileUserExerciseInActionCompletedScreenInfoComponent() {
    TodoBox("MobileUserExerciseInActionCompletedScreenInfoComponent", Modifier.size(385.dp, 301.dp))
}

@Composable
private fun MobileUserExerciseInActionCompletedScreenCompleteActionComponent() {
    TodoBox("MobileUserExerciseInActionCompletedScreenCompleteActionComponent", Modifier.size(430.dp, 48.dp))
}
