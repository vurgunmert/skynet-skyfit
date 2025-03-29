package com.vurgun.skyfit.feature_profile.ui.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.SkyFitScaffold
import com.vurgun.skyfit.feature_exercises.ui.components.FindExercisesCard
import com.vurgun.skyfit.feature_lessons.ui.components.LessonSessionColumn
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_profile.ui.components.LifestyleActionRow
import com.vurgun.skyfit.feature_profile.ui.components.MobileMeasurementsActionCard
import com.vurgun.skyfit.feature_profile.ui.components.MobileProfileActionsRow
import com.vurgun.skyfit.feature_profile.ui.components.MobileProfileHeader
import com.vurgun.skyfit.feature_profile.ui.components.PhotoGalleryEmptyStackCard
import com.vurgun.skyfit.feature_profile.ui.components.PhotoGalleryStackCard
import com.vurgun.skyfit.feature_profile.ui.user.viewmodel.SkyFitUserProfileViewModel
import com.vurgun.skyfit.feature_social.ui.components.SocialPostCard
import com.vurgun.skyfit.feature_social.ui.components.SocialQuickPostInputCard
import moe.tlaster.precompose.navigation.Navigator

@Composable
fun MobileUserProfileScreen(navigator: Navigator) {
    val viewModel = remember { SkyFitUserProfileViewModel() }

    // Observing state from ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val showPosts by viewModel.showPosts.collectAsState()
    val showInfoMini by viewModel.showInfoMini.collectAsState()

    var showMeasurements: Boolean = true

    val scrollState = rememberScrollState()
    val postListState = rememberLazyListState()

    LaunchedEffect(scrollState.value, postListState.firstVisibleItemIndex) {
        viewModel.updateScroll(scrollState.value, postListState.firstVisibleItemIndex)
    }

    SkyFitScaffold { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item(key = "Header") {
                MobileProfileHeader(viewData = uiState.profileData.copy(showInfoMini = showInfoMini))
            }

            item(key = "ActionGroup") {
                MobileProfileActionsRow(
                    postsSelected = showPosts,
                    onClickAbout = { viewModel.toggleShowPosts(false) },
                    onClickPosts = { viewModel.toggleShowPosts(true) },
                    onClickSettings = { navigator.jumpAndStay(NavigationRoute.UserSettings) },
                    onClickNewPost = { navigator.jumpAndStay(NavigationRoute.UserSocialMediaPostAdd) }
                )
            }

            if (showPosts) {
                item(key = "QuickPost") {
                    SocialQuickPostInputCard(modifier = Modifier.padding(horizontal = 16.dp), onClickSend = {})
                }

                items(posts) { post ->
                    SocialPostCard(
                        data = post,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        onClick = {},
                        onClickComment = {},
                        onClickLike = {},
                        onClickShare = {}
                    )
                }
            } else {
                uiState.appointments?.let {
                    item(key = "Appointments") {
                        LessonSessionColumn(
                            viewData = it,
                            modifier = Modifier.padding(horizontal = 16.dp),
                            onClickItem = { navigator.jumpAndStay(NavigationRoute.UserAppointments) }
                        )
                    }
                }

                if (showMeasurements) {
                    item(key = "Measurements") {
                        MobileMeasurementsActionCard(modifier = Modifier.padding(horizontal = 16.dp)) {
                            navigator.jumpAndStay(NavigationRoute.UserMeasurements)
                        }
                    }
                }

                item(key = "ExerciseHistory") {
                    uiState.exercises?.let {
                        LifestyleActionRow(modifier = Modifier.padding(horizontal = 16.dp), viewData = it)
                    } ?: FindExercisesCard(modifier = Modifier.padding(horizontal = 16.dp)) {
                        navigator.jumpAndStay(NavigationRoute.ExploreExercises)
                    }
                }

                item(key = "PhotoDiary") {
                    uiState.photoDiary?.let {
                        PhotoGalleryStackCard(modifier = Modifier.padding(horizontal = 16.dp), viewData = it)
                    } ?: PhotoGalleryEmptyStackCard(modifier = Modifier.padding(horizontal = 16.dp)) {
                        navigator.jumpAndStay(NavigationRoute.UserPhotoDiary)
                    }
                }

                uiState.habits?.let {
                    item(key = "Habits") { LifestyleActionRow(modifier = Modifier.padding(horizontal = 16.dp), viewData = it) }
                }
            }

            item { Spacer(Modifier.height(124.dp)) }
        }
    }
}

