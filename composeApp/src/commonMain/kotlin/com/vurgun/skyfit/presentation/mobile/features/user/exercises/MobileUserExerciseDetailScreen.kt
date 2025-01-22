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
fun MobileUserExerciseDetailScreen(navigator: Navigator) {

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            MobileExerciseActionDetailScreenStartActionComponent()
        },
        bottomBar = {
            MobileExerciseActionDetailScreenStartActionComponent()
        }
    ) {

        Box(Modifier.fillMaxSize()) {
            MobileExerciseActionDetailScreenImageComponent()

            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(251.dp))
                MobileExerciseActionDetailScreenInfoComponent()
            }
        }
    }
}

@Composable
private fun MobileExerciseActionDetailScreenToolbarComponent() {
    TodoBox("MobileExerciseActionDetailScreenToolbarComponent", Modifier.size(430.dp, 40.dp))
}

@Composable
private fun MobileExerciseActionDetailScreenImageComponent() {
    TodoBox("MobileExerciseActionDetailScreenImageComponent", Modifier.size(430.dp, 295.dp))
}

@Composable
private fun MobileExerciseActionDetailScreenInfoComponent() {
    TodoBox("MobileExerciseActionDetailScreenInfoComponent", Modifier.size(430.dp, 672.dp))
}

@Composable
private fun MobileExerciseActionDetailScreenStartActionComponent() {
    TodoBox("MobileExerciseActionDetailScreenStartActionComponent", Modifier.size(430.dp, 48.dp))
}
