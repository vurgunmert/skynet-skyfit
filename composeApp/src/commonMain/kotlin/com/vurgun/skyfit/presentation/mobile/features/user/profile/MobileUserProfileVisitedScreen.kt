package com.vurgun.skyfit.presentation.mobile.features.user.profile

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.presentation.shared.components.ButtonSize
import com.vurgun.skyfit.presentation.shared.components.ButtonState
import com.vurgun.skyfit.presentation.shared.components.ButtonVariant
import com.vurgun.skyfit.presentation.shared.components.SkyFitButtonComponent
import com.vurgun.skyfit.presentation.shared.components.SkyFitIconButton
import com.vurgun.skyfit.presentation.shared.features.user.SkyFitUserProfileViewModel
import com.vurgun.skyfit.presentation.shared.features.user.fakePosts
import com.vurgun.skyfit.presentation.shared.navigation.SkyFitNavigationRoute
import com.vurgun.skyfit.presentation.shared.navigation.jumpAndStay
import com.vurgun.skyfit.presentation.shared.resources.SkyFitColor
import com.vurgun.skyfit.presentation.shared.resources.SkyFitTypography
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.logo_skyfit

@Composable
fun MobileUserProfileVisitedScreen(navigator: Navigator) {

    val scrollState = rememberScrollState()
    val postListState = rememberLazyListState()
    var showPosts by remember { mutableStateOf(false) }
    var isFollowing by remember { mutableStateOf(true) }
    val showInfoMini by remember {
        derivedStateOf { scrollState.value > 10 }
    }

    val viewModel = SkyFitUserProfileViewModel()
    val profileData by viewModel.profileData.collectAsState()
    val posts = fakePosts

    Scaffold(
        backgroundColor = SkyFitColor.background.default,
        topBar = {
            BoxWithConstraints {
                val width = maxWidth
                val imageHeight = width * 9 / 16
                val contentTopPadding = imageHeight * 2 / 10

                MobileUserProfileBackgroundImageComponent(imageHeight)

                Column(
                    Modifier
                        .padding(top = contentTopPadding)
                        .fillMaxWidth()
                ) {
                    profileData?.let {
                        if (showInfoMini) {
                            MobileUserProfileInfoCardMiniComponent(it)
                        } else {
                            MobileUserProfileInfoCardComponent(it)
                        }
                    }

                    Spacer(Modifier.height(16.dp))
                    MobileVisitedProfileActionsComponent(
                        onClickAbout = { showPosts = false },
                        onClickPosts = { showPosts = true }
                    )

                    if (isFollowing) {
                        SkyFitButtonComponent(
                            modifier = Modifier.fillMaxWidth(), text = "Takip Et",
                            onClick = { },
                            variant = ButtonVariant.Primary,
                            size = ButtonSize.Large,
                            state = ButtonState.Rest
                        )
                    } else {
                        SkyFitButtonComponent(
                            modifier = Modifier.fillMaxWidth(), text = "Takipten Cik",
                            onClick = { },
                            variant = ButtonVariant.Primary,
                            size = ButtonSize.Large,
                            state = ButtonState.Rest
                        )
                    }
                }

                MobileUserProfileVisitedScreenToolbarComponent(onClickBack = { navigator.popBackStack() })
            }
        }
    ) {
        if (showPosts) {
            MobileUserProfilePostsComponent(posts = posts, listState = postListState)
        } else {
            MobileUserProfileAboutGroupComponent(scrollState, navigator, viewModel)
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
fun MobileVisitedProfileActionsComponent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    onClickAbout: () -> Unit = {},
    onClickPosts: () -> Unit = {}
) {
    var aboutSelected by remember { mutableStateOf(true) } // Default to "Hakkımda"

    Row(
        modifier
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp))
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(if (aboutSelected) 1f else 3f)
                .background(
                    if (aboutSelected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary,
                    RoundedCornerShape(12.dp)
                )
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
                .background(
                    if (!aboutSelected) SkyFitColor.specialty.buttonBgRest else SkyFitColor.background.surfaceSecondary,
                    RoundedCornerShape(12.dp)
                )
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
private fun MobileUserProfileVisitedScreenAboutGroupComponent(
    scrollState: ScrollState,
    navigator: Navigator,
    viewModel: SkyFitUserProfileViewModel
) {
    var dietGoals: List<Any> = listOf(1, 2, 3)
    var showMeasurements: Boolean = true
    var exerciseHistory: List<Any> = listOf(1, 2, 3)
    var photos: List<Any> = emptyList()
    var statistics: List<Any> = emptyList()
    var habits: List<Any> = emptyList()
    var posts: List<Any> = emptyList()
    val appointments = viewModel.appointments.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (appointments.value.isEmpty()) {
            MobileUserProfileAppointmentsComponent(appointments.value)
        }
        if (dietGoals.isNotEmpty()) {
            MobileUserProfileDietGoalsComponent(dietGoals)
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

        if (exerciseHistory.isNotEmpty()) {
            MobileUserProfileExerciseHistoryComponent(exerciseHistory)
        }

        if (photos.isNotEmpty()) {
            MobileUserProfilePhotoDiaryComponent()
        }

        if (habits.isNotEmpty()) {
            MobileUserProfileHabitsComponent(habits)
        }
    }
}