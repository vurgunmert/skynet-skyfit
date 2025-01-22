package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserProfileVisitedScreen(navigator: Navigator) {

    var appointments: List<Any> = emptyList()
    var dietGoals: List<Any> = emptyList()
    var measurements: List<Any> = emptyList()
    var exerciseHistory: List<Any> = emptyList()
    var photos: List<Any> = emptyList()
    var statistics: List<Any> = emptyList()
    var habits: List<Any> = emptyList()
    var posts: List<Any> = emptyList()
    val scrollState = rememberScrollState()
    val showPosts: Boolean = true
    val showInfoMini by remember {
        derivedStateOf { scrollState.value > 10 }
    }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Box {
                Column {
                    if (showInfoMini) {
                        MobileUserProfileVisitedScreenInfoCardMiniComponent()
                    } else {
                        Box {
                            MobileUserProfileVisitedScreenBackgroundImageComponent()
                            MobileUserProfileVisitedScreenInfoCardComponent()
                        }
                    }
                    MobileUserProfileVisitedScreenActionsComponent()
                }

                MobileUserProfileVisitedScreenToolbarComponent()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (appointments.isNotEmpty()) {
                MobileUserProfileVisitedScreenAppointmentsComponent()
            }

            if (dietGoals.isNotEmpty()) {
                MobileUserProfileVisitedScreenDietGoalsComponent()
            }

            if (measurements.isNotEmpty()) {
                MobileUserProfileVisitedScreenMeasurementsComponent()
            }

            if (statistics.isNotEmpty()) {
                MobileUserProfileVisitedScreenStatisticsBarsComponent()
            }

            if (exerciseHistory.isNotEmpty()) {
                MobileUserProfileVisitedScreenExerciseHistoryComponent()
            }

            if (photos.isNotEmpty()) {
                MobileUserProfileVisitedScreenPhotoDiaryComponent()
            }

            if (habits.isNotEmpty()) {
                MobileUserProfileVisitedScreenHabitsComponent()
            }

            if (showPosts) {
                MobileUserProfileVisitedScreenPostsComponent()
            }
        }
    }
}

@Composable
private fun MobileUserProfileVisitedScreenToolbarComponent() {
    TodoBox("MobileUserProfileVisitedScreenToolbarComponent", Modifier.size(430.dp, 64.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenBackgroundImageComponent() {
    TodoBox("MobileUserProfileVisitedScreenBackgroundImageComponent", Modifier.size(430.dp, 180.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenInfoCardComponent() {
    TodoBox("MobileUserProfileVisitedScreenInfoCardComponent", Modifier.size(398.dp, 208.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenInfoCardMiniComponent() {
    TodoBox("MobileUserProfileVisitedScreenCardMiniComponent", Modifier.size(398.dp, 150.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenActionsComponent() {
    TodoBox("MobileUserProfileVisitedScreenActionsComponent", Modifier.size(398.dp, 120.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenAppointmentsComponent() {
    TodoBox("MobileUserProfileVisitedScreenAppointmentsComponent", Modifier.size(398.dp, 352.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenDietGoalsComponent() {
    TodoBox("MobileUserProfileVisitedScreenDietGoalsComponent", Modifier.size(382.dp, 392.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenMeasurementsComponent() {
    TodoBox("MobileUserProfileVisitedScreenMeasurementsComponent", Modifier.size(382.dp, 56.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenStatisticsBarsComponent() {
    TodoBox("MobileUserProfileVisitedScreenStatisticsBarsComponent", Modifier.size(382.dp, 250.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenExerciseHistoryComponent() {
    TodoBox("MobileUserProfileVisitedScreenExerciseHistoryComponent", Modifier.size(382.dp, 162.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenPhotoDiaryComponent() {
    TodoBox("MobileUserProfileVisitedScreenPhotoDiaryComponent", Modifier.size(382.dp, 410.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenHabitsComponent() {
    TodoBox("MobileUserProfileVisitedScreenHabitsComponent", Modifier.size(382.dp, 162.dp))
}

@Composable
private fun MobileUserProfileVisitedScreenPostsComponent() {
    TodoBox("MobileUserProfileVisitedScreenPostsComponent", Modifier.size(382.dp, 857.dp))
}
