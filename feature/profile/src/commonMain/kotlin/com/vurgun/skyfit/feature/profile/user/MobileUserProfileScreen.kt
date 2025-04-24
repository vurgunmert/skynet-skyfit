package com.vurgun.skyfit.feature.profile.user

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.data.user.domain.FacilityProfile
import com.vurgun.skyfit.feature.profile.components.MobileProfileActionsRow
import com.vurgun.skyfit.feature.profile.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature.profile.components.viewdata.ProfileViewMode
import com.vurgun.skyfit.feature.profile.trainer.MobileUserProfileInfoCardComponent
import com.vurgun.skyfit.ui.core.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.ui.core.components.image.NetworkImage
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.appointments_title
import skyfit.ui.core.generated.resources.show_all_action

@Composable
fun MobileUserProfileOwnerScreen(
    goToBack: () -> Unit,
    goToSettings: () -> Unit,
    goToAppointments: () -> Unit,
    goToMeasurements: () -> Unit,
    goToExercises: () -> Unit,
    goToPhotoDiary: () -> Unit,
    goToCreatePost: () -> Unit,
    onVisitFacility: (gymId: Int) -> Unit,
    viewMode: ProfileViewMode = ProfileViewMode.OWNER,
    viewModel: UserProfileOwnerViewModel = koinViewModel()
) {
    // Observing state from ViewModel
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val memberFacilityProfile by viewModel.memberFacilityProfile.collectAsStateWithLifecycle()
    val appointments by viewModel.appointments.collectAsStateWithLifecycle()

//    val uiState by viewModel.uiState.collectAsState()
    val posts by viewModel.posts.collectAsState()
    val postsVisible by viewModel.showPosts.collectAsState()

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

    LaunchedEffect(Unit) {
        viewModel.loadData()
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
                imageUrl = userProfile?.backgroundImageUrl,
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

                MobileUserProfileInfoCardComponent(userProfile)

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

                    memberFacilityProfile?.let {
                        UserProfileComponent.MemberFacilityCard(it, onClick = { onVisitFacility(it.gymId) })
                    }

                    UserProfileComponent.UserProfileAppointmentColumn(
                        appointments = appointments,
                        onClickItem = { goToAppointments() },
                        onClickShowAll = goToAppointments,
                        modifier = Modifier
                    )
                }

                Spacer(Modifier.height(124.dp))
            }
        }
    }
}


private object UserProfileComponent {
    @Composable
    fun UserProfileAppointmentColumn(
        appointments: List<LessonSessionItemViewData>,
        onClickItem: ((LessonSessionItemViewData) -> Unit)? = null,
        onClickShowAll: (() -> Unit)? = null,
        modifier: Modifier = Modifier,
    ) {
        if (appointments.isEmpty()) return

        Column(
            modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.fillTransparent)
                .clickable(onClick = { onClickShowAll?.invoke() })
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = SkyFitAsset.getPainter(SkyFitAsset.SkyFitIcon.EXERCISES.id),
                    modifier = Modifier.size(24.dp),
                    contentDescription = null,
                    tint = SkyFitColor.icon.default
                )

                Text(
                    text = stringResource(Res.string.appointments_title),
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.weight(1f)
                )

                if (onClickShowAll != null) {
                    Text(
                        text = stringResource(Res.string.show_all_action),
                        style = SkyFitTypography.bodyXSmall,
                        color = SkyFitColor.border.secondaryButton,
                        modifier = Modifier.clickable(onClick = onClickShowAll)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                appointments.forEach { item ->
                    AvailableActivityCalendarEventItem(
                        title = item.title,
                        iconId = item.iconId,
                        date = item.date.toString(),
                        timePeriod = item.hours.toString(),
                        location = item.location.toString(),
                        trainer = item.trainer.toString(),
                        capacity = item.capacityRatio.toString(),
                        note = item.note
                    )
                }
            }
        }
    }

    @Composable
    fun MemberFacilityCard(
        facilityProfile: FacilityProfile,
        onClick: () -> Unit
    ) {
        Row(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .clickable(onClick = onClick)
                .fillMaxWidth()
                .border(1.dp, SkyFitColor.border.secondaryButtonHover, RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            NetworkImage(
                imageUrl = facilityProfile.backgroundImageUrl,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.width(16.dp))

            Column(Modifier.weight(1f)) {
                Text(
                    text = facilityProfile.facilityName,
                    style = SkyFitTypography.bodyMediumRegular,
                    color = SkyFitColor.text.secondary
                )
            }
        }
    }
}