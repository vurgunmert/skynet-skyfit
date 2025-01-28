package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserProfileScreen(navigator: Navigator) {

    var appointments: List<Any> = emptyList()
    var dietGoals: List<Any> = emptyList()
    var showMeasurements: Boolean = true
    var exerciseHistory: List<Any> = emptyList()
    var photos: List<Any> = emptyList()
    var statistics: List<Any> = emptyList()
    var habits: List<Any> = emptyList()
    var posts: List<Any> = emptyList()
    val scrollState = rememberScrollState()
    val showPosts: Boolean = false
    val showInfoMini by remember {
        derivedStateOf { scrollState.value > 10 }
    }

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            Column {
                if (showInfoMini) {
                    MobileUserProfileInfoCardMiniComponent()
                } else {
                    Box {
                        MobileUserProfileBackgroundImageComponent()
                        MobileUserProfileInfoCardComponent()
                    }
                }

                MobileUserProfileActionsComponent()
                if (showPosts) {
                    MobileUserProfilePostsInputComponent()
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (appointments.isNotEmpty()) {
                MobileUserProfileAppointmentsComponent()
            }
            if (dietGoals.isNotEmpty()) {
                MobileUserProfileDietGoalsEmptyComponent()
            } else {
                MobileUserProfileDietGoalsComponent()
            }

            if (showMeasurements) {
                Spacer(Modifier.height(16.dp))
                MobileUserProfileMeasurementsComponent(onClick = {
                    navigator.jumpAndStay(SkyFitNavigationRoute.UserMeasurements)
                })
            }

            if (statistics.isNotEmpty()) {
                MobileUserProfileStatisticsBarsComponent()
            }

            if (exerciseHistory.isEmpty()) {
                MobileUserProfileScreenExploreExercisesComponent {
                    navigator.jumpAndStay(SkyFitNavigationRoute.DashboardExploreExercises)
                }
            } else {
                MobileUserProfileExerciseHistoryComponent()
            }

            if (photos.isEmpty()) {
                MobileUserProfilePhotoDiaryEmptyComponent()
            } else {
                MobileUserProfilePhotoDiaryComponent()
            }

            if (habits.isNotEmpty()) {
                MobileUserProfileHabitsComponent()
            }

            if (showPosts) {
                MobileUserProfilePostsComponent()
            }
        }
    }
}

@Composable
private fun MobileUserProfileBackgroundImageComponent() {
    TodoBox("MobileUserProfileBackgroundImageComponent", Modifier.size(430.dp, 180.dp))
}

@Composable
private fun MobileUserProfileInfoCardComponent() {
    TodoBox("MobileUserProfileInfoComponent", Modifier.size(398.dp, 208.dp))
}


@Composable
private fun MobileUserProfileInfoCardMiniComponent() {
    TodoBox("MobileUserProfileInfoComponent", Modifier.size(398.dp, 150.dp))
}

@Composable
private fun MobileUserProfileActionsComponent() {
    TodoBox("MobileUserProfileActionsComponent", Modifier.size(398.dp, 56.dp))
}

@Composable
private fun MobileUserProfileAppointmentsComponent() {
    TodoBox("MobileUserProfileAppointmentsComponent", Modifier.size(398.dp, 352.dp))
}

@Composable
private fun MobileUserProfileDietGoalsComponent() {
    TodoBox("MobileUserProfileDietGoalsComponent", Modifier.size(382.dp, 392.dp))
}

@Composable
private fun MobileUserProfileDietGoalsEmptyComponent() {
    TodoBox("MobileUserProfileDietGoalsEmptyComponent", Modifier.size(382.dp, 390.dp))
}

@Composable
fun MobileUserProfileMeasurementsComponent(onClick: () -> Unit) {
    Row(
        Modifier.fillMaxWidth()
            .background(SkyFitColor.background.fillTransparent, shape = RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "Ölçümlerim",
            style = SkyFitTypography.bodyMediumSemibold,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(Res.drawable.logo_skyfit),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = SkyFitColor.icon.default
        )
    }
}

@Composable
private fun MobileUserProfileStatisticsBarsComponent() {
    TodoBox("MobileUserProfileStatisticsBarsComponent", Modifier.size(382.dp, 250.dp))
}

@Composable
private fun MobileUserProfileExerciseHistoryComponent() {
    TodoBox("MobileUserProfileExerciseHistoryComponent", Modifier.size(382.dp, 162.dp))
}

@Composable
private fun MobileUserProfileScreenExploreExercisesComponent(onClick: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 34.dp),
        contentAlignment = Alignment.Center
    ) {

        SkyFitButtonComponent(
            Modifier.wrapContentWidth(), text = "Antrenman Keşfet",
            onClick = onClick,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Medium,
            initialState = ButtonState.Rest
        )
    }
}

@Composable
private fun MobileUserProfilePhotoDiaryComponent() {
    TodoBox("MobileUserProfilePhotoDiaryComponent", Modifier.size(382.dp, 410.dp))
}

@Composable
private fun MobileUserProfilePhotoDiaryEmptyComponent() {
    TodoBox("MobileUserProfilePhotoDiaryEmptyComponent", Modifier.size(374.dp, 374.dp))
}

@Composable
private fun MobileUserProfileHabitsComponent() {
    TodoBox("MobileUserProfileHabitsComponent", Modifier.size(382.dp, 162.dp))
}

@Composable
private fun MobileUserProfilePostsInputComponent() {
    TodoBox("MobileUserProfilePostsInputComponent", Modifier.size(382.dp, 64.dp))
}

@Composable
private fun MobileUserProfilePostsComponent() {
    TodoBox("MobileUserProfilePostsComponent", Modifier.size(382.dp, 857.dp))
}
