package com.vurgun.skyfit.feature.profile.user

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.feature.profile.components.MobileProfileActionsRow
import com.vurgun.skyfit.feature.profile.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature.profile.components.MobileProfileHeader
import com.vurgun.skyfit.feature.profile.components.MobileProfileHeaderMini
import com.vurgun.skyfit.feature.profile.components.viewdata.ProfileViewMode
import com.vurgun.skyfit.feature.profile.trainer.MobileTrainerProfileInfoCardComponent
import com.vurgun.skyfit.ui.core.components.event.LessonSessionColumn
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MobileUserProfileScreen(
    goToBack: () -> Unit,
    goToSettings: () -> Unit,
    goToAppointments: () -> Unit,
    goToMeasurements: () -> Unit,
    goToExercises: () -> Unit,
    goToPhotoDiary: () -> Unit,
    goToCreatePost: () -> Unit,
    viewMode: ProfileViewMode = ProfileViewMode.OWNER,
    viewModel: UserProfileOwnerViewModel = koinViewModel()
) {
    // Observing state from ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val postsVisible by viewModel.showPosts.collectAsState()
    val showInfoMini by viewModel.showInfoMini.collectAsState()

    val scrollState = rememberScrollState()
    val postListState = rememberLazyListState()

    LaunchedEffect(scrollState.value, postListState.firstVisibleItemIndex) {
        viewModel.updateScroll(scrollState.value, postListState.firstVisibleItemIndex)
    }

    var backgroundAlpha by remember { mutableStateOf(1f) }
    val transitionThreshold = 300f

    LaunchedEffect(scrollState.value) {
        val scrollY = scrollState.value.toFloat()
        backgroundAlpha = when {
            scrollY >= transitionThreshold -> 0f
            else -> (1f - (scrollY / transitionThreshold))
        }
    }

    SkyFitMobileScaffold { defaultPadding ->

        BoxWithConstraints(
            modifier = Modifier
                .padding(defaultPadding)
                .fillMaxSize()
                .background(SkyFitColor.background.default)
        ) {
            val width = maxWidth
            val imageHeight = width * 9 / 16
            val contentTopPadding = imageHeight * 5 / 10

            MobileProfileBackgroundImage(
                imageUrl = uiState.profileData.backgroundImageUrl,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .height(imageHeight)
                    .alpha(backgroundAlpha)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.height(contentTopPadding))

                MobileTrainerProfileInfoCardComponent(uiState.profileData)
//                MobileProfileHeaderMini(viewData = uiState.profileData)

                if (viewMode == ProfileViewMode.OWNER) {
                    MobileProfileActionsRow(
                        postsSelected = postsVisible,
                        onClickAbout = { viewModel.toggleShowPosts(false) },
                        onClickPosts = { viewModel.toggleShowPosts(true) },
                        onClickSettings = goToSettings,
                        onClickNewPost = goToCreatePost
                    )
                }

                if (postsVisible) {

                } else {
                    uiState.appointments?.let {
                        LessonSessionColumn(
                            viewData = it,
                            modifier = Modifier,
                            onClickItem = { goToAppointments() }
                        )
                    }
                }

                Spacer(Modifier.height(124.dp))
            }
        }
    }
}

