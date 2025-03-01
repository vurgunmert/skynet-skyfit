package com.vurgun.skyfit.feature_profile.ui.user

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
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonState
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.button.SkyFitIconButton
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_profile.ui.fakePosts
import com.vurgun.skyfit.navigation.NavigationRoute
import com.vurgun.skyfit.navigation.jumpAndStay
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
            MobileUserProfileAboutGroupComponent(scrollState, navigator, emptyList())
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
    var aboutSelected by remember { mutableStateOf(true) }

    Row(
        modifier
            .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(20.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
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
    val statistics by viewModel.statistics.collectAsState()
    val appointments by viewModel.appointments.collectAsState()
    val exercisesRowViewData by viewModel.exercisesRowViewData.collectAsState()
    val habitsRowViewData by viewModel.habitsRowViewData.collectAsState()
    val photoDiary by viewModel.photoDiary.collectAsState()


    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (appointments.isEmpty()) {
            MobileUserProfileAppointmentsComponent(appointments)
        }
        if (dietGoals.isNotEmpty()) {
            MobileUserProfileDietGoalsComponent(dietGoals)
        }

        if (showMeasurements) {
            Spacer(Modifier.height(16.dp))
            MobileUserProfileMeasurementsComponent(onClick = {
                navigator.jumpAndStay(NavigationRoute.UserMeasurements)
            })
        }

        statistics?.let { MobileUserProfileStatisticsBarsComponent(it) }

//        if (exercisesRowViewData != null) {
//            LifestyleActionRow(viewData = exercisesRowViewData)
//        }

        photoDiary?.let { MobileUserProfilePhotoDiaryComponent(it) }

//        if (habitsRowViewData != null) {
//            LifestyleActionRow(viewData = habitsRowViewData)
//        }
    }
}