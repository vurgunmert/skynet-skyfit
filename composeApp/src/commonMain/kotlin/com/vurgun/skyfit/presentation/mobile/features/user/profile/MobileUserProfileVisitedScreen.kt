package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItem
import com.vurgun.skyfit.presentation.shared.features.social.SkyFitPostCardItemComponent
import com.vurgun.skyfit.presentation.shared.features.common.TodoBox
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

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
                    MobileVisitedProfileActionsComponent()
                }

                MobileUserProfileVisitedScreenToolbarComponent(onClickBack = { navigator.popBackStack() })
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
private fun MobileUserProfileVisitedScreenToolbarComponent(onClickBack: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 24.dp)) {
        SkyFitIconButton(
            painter = painterResource(Res.drawable.logo_skyfit),
            modifier = Modifier.size(48.dp).clickable(onClick = onClickBack)
        )
    }
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
fun MobileVisitedProfileActionsComponent(modifier: Modifier = Modifier.fillMaxWidth(),
                                         onClickAbout: () -> Unit = {},
                                         onClickPosts: () -> Unit = {}) {
    var aboutSelected by remember { mutableStateOf(true) } // Default to "Hakkımda"

    Row(
        modifier
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(if (aboutSelected) 1f else 3f)
                .background(if (aboutSelected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
                .clickable(onClick = { aboutSelected = true; onClickAbout.invoke() })
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Hakkımda",
                style = SkyFitTypography.bodyLargeMedium,
                color = if (aboutSelected) SkyFitColor.text.inverse else SkyFitColor.text.default
            )
        }

        Box(
            modifier = Modifier
                .weight(if (!aboutSelected) 3f else 1f)
                .background(if (!aboutSelected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary, RoundedCornerShape(12.dp))
                .clickable(onClick = { aboutSelected = false; onClickPosts.invoke() })
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Paylaşımlar",
                style = SkyFitTypography.bodyLargeMedium,
                color = if (!aboutSelected) SkyFitColor.text.inverse else SkyFitColor.text.default
            )
        }
    }
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
    val posts = List(6) { index ->
        SkyFitPostCardItem(
            postId = "post_${index + 1}",
            username = listOf("JohnDoe", "FitnessQueen", "MikeTrainer", "EmmaRunner", "DavidGym", "SophiaYoga").random(),
            socialLink = listOf("https://instagram.com/user", "https://twitter.com/user", "https://linkedin.com/user", null).random(),
            timeAgo = listOf("5 min ago", "2 hours ago", "1 day ago", "3 days ago", "1 week ago").random(),
            profileImageUrl = listOf(
                "https://example.com/profile1.png",
                "https://example.com/profile2.png",
                "https://example.com/profile3.png",
                "https://example.com/profile4.png",
                null
            ).random(),
            content = listOf(
                "Just finished an amazing workout! 💪",
                "Morning yoga session done! 🧘‍♀️",
                "Any tips for increasing stamina? 🏃‍♂️",
                "Trying out a new HIIT routine. 🔥",
                "Recovery day with some light stretching.",
                "Nutrition is key! What’s your go-to meal?"
            ).random(),
            imageUrl = listOf(
                "https://example.com/post1.jpg",
                "https://example.com/post2.jpg",
                "https://example.com/post3.jpg",
                null
            ).random(),
            favoriteCount = (0..500).random(),
            commentCount = (0..200).random(),
            shareCount = (0..100).random(),
        )
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(posts) {
            SkyFitPostCardItemComponent(it,
                onClick = {},
                onClickComment = {},
                onClickLike = {},
                onClickShare = {})
        }
    }
}
