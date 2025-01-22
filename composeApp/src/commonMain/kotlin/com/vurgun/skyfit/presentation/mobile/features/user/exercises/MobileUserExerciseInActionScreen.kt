package com.vurgun.skyfit.presentation.mobile.features.user.exercises

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndTakeover
import moe.tlaster.precompose.navigation.Navigator

private enum class MobileUserExerciseInActionScreenStep {
    SESSION,
    BREAK,
    PROGRAM,
    TROPHY,
    COMPLETE
}

@Composable
fun MobileUserExerciseInActionScreen(navigator: Navigator) {

    val showToolbar: Boolean = false
    val step = MobileUserExerciseInActionScreenStep.SESSION

    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            when (step) {
                MobileUserExerciseInActionScreenStep.SESSION -> {
                    if (showToolbar) {
                        MobileUserExerciseInActionScreenToolbarComponent()
                    }
                    Spacer(Modifier.weight(1f))
                    MobileUserExerciseInActionScreenActionsComponent()
                }

                MobileUserExerciseInActionScreenStep.BREAK -> {
                    MobileUserExerciseInActionScreenToolbarComponent()
                    Spacer(Modifier.height(18.dp))
                    MobileUserExerciseInActionScreenBreakComponent()
                }

                MobileUserExerciseInActionScreenStep.PROGRAM -> {

                    MobileUserExerciseInActionScreenToolbarComponent()
                    Spacer(Modifier.height(18.dp))
                    MobileUserExerciseInActionScreenProgramComponent()
                }

                MobileUserExerciseInActionScreenStep.TROPHY -> {
                    MobileUserExerciseInActionScreenToolbarComponent()
                    Spacer(Modifier.height(18.dp))
                    MobileUserExerciseInActionScreenProgramComponent()
                }

                MobileUserExerciseInActionScreenStep.COMPLETE -> {
                    navigator.jumpAndTakeover(
                        SkyFitNavigationRoute.UserExerciseInAction,
                        SkyFitNavigationRoute.UserExerciseInActionComplete
                    )
                }
            }

        }
    }
}

@Composable
private fun MobileUserExerciseInActionScreenToolbarComponent() {
    TodoBox("MobileUserExerciseInActionScreenToolbarComponent", Modifier.size(430.dp, 56.dp))
}

@Composable
private fun MobileUserExerciseInActionScreenBreakComponent() {
    TodoBox("MobileUserExerciseInActionScreenBreakComponent", Modifier.size(382.dp, 780.dp))
}

@Composable
private fun MobileUserExerciseInActionScreenProgramComponent() {
    TodoBox("MobileUserExerciseInActionScreenProgramComponent", Modifier.size(382.dp, 780.dp))
}

@Composable
private fun MobileUserExerciseInActionScreenTrophyComponent() {
    TodoBox("MobileUserExerciseInActionScreenTrophyComponent", Modifier.size(382.dp, 780.dp))
}

@Composable
private fun MobileUserExerciseInActionScreenActionsComponent() {
    TodoBox("MobileUserExerciseInActionScreenActionsComponent", Modifier.size(382.dp, 95.dp))
}