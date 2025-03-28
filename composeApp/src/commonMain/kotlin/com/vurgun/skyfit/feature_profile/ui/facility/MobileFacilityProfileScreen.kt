package com.vurgun.skyfit.feature_profile.ui.facility

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.unit.dp
import com.vurgun.skyfit.core.ui.components.ButtonSize
import com.vurgun.skyfit.core.ui.components.ButtonVariant
import com.vurgun.skyfit.core.ui.components.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.button.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.core.ui.components.button.SkyFitSecondaryIconButton
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.loader.CircularLoader
import com.vurgun.skyfit.core.ui.components.special.RatingStarComponent
import com.vurgun.skyfit.core.ui.resources.SkyFitColor
import com.vurgun.skyfit.core.ui.resources.SkyFitStyleGuide
import com.vurgun.skyfit.core.ui.resources.SkyFitTypography
import com.vurgun.skyfit.feature_explore.ui.TrainerProfileCardItemViewData
import com.vurgun.skyfit.feature_lessons.ui.components.LessonSessionColumn
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionColumnViewData
import com.vurgun.skyfit.feature_profile.domain.model.ProfileViewMode
import com.vurgun.skyfit.feature_profile.ui.components.VerticalProfileStatisticItem
import com.vurgun.skyfit.feature_profile.ui.components.MobileProfileActionsRow
import com.vurgun.skyfit.feature_profile.ui.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature_profile.ui.components.PhotoGalleryEmptyStackCard
import com.vurgun.skyfit.feature_profile.ui.components.PhotoGalleryStackCard
import com.vurgun.skyfit.feature_profile.ui.components.VerticalTrainerProfileCardsRow
import com.vurgun.skyfit.feature_profile.ui.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature_social.ui.components.SocialPostCard
import com.vurgun.skyfit.feature_social.ui.components.SocialQuickPostInputCard
import com.vurgun.skyfit.feature_navigation.NavigationRoute
import com.vurgun.skyfit.feature_navigation.jumpAndStay
import com.vurgun.skyfit.feature_profile.ui.facility.viewmodel.FacilityProfileInfoViewData
import com.vurgun.skyfit.feature_profile.ui.facility.viewmodel.FacilityProfileViewModel
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import skyfit.composeapp.generated.resources.Res
import skyfit.composeapp.generated.resources.ic_calendar_dots
import skyfit.composeapp.generated.resources.ic_location_pin
import skyfit.composeapp.generated.resources.ic_send

@Composable
fun MobileFacilityProfileScreen(
    navigator: Navigator,
    viewMode: ProfileViewMode = ProfileViewMode.VISITOR,
    viewModel: FacilityProfileViewModel = koinInject()
) {
    val scrollState = rememberScrollState()
    val profileState by viewModel.profileState.collectAsState()
    val postsVisible by viewModel.postsVisible.collectAsState()
    val posts by viewModel.posts.collectAsState()

    val infoViewData = profileState.infoViewData
    val galleryStackViewData = profileState.galleryStackViewData
    val lessonSessionColumnViewData = profileState.lessonSessionColumnViewData
    val trainers = profileState.trainers

    LaunchedEffect(viewModel) {
        viewModel.loadData()
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

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(SkyFitColor.background.default)
    ) {
        val width = maxWidth
        val imageHeight = width * 9 / 16

        if (infoViewData == null) {
            CircularLoader(Modifier.align(Alignment.Center))
        } else {
            MobileProfileBackgroundImage(
                imageUrl = infoViewData.backgroundUrl,
                Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .height(imageHeight)
                    .alpha(backgroundAlpha)
            )

            val contentTopPadding = imageHeight * 6 / 10

            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .widthIn(max = SkyFitStyleGuide.Mobile.maxWidth)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.height(contentTopPadding))

                MobileFacilityProfileScreenInfoCardComponent(
                    infoViewData = infoViewData,
                    viewMode = viewMode,
                    onClickFollow = viewModel::followFacility,
                    onClickUnFollow = viewModel::unfollowFacility,
                    onClickBookingCalendar = { navigator.jumpAndStay(NavigationRoute.FacilityCalendarVisited) },
                    onClickMessage = { navigator.jumpAndStay(NavigationRoute.UserToFacilityChat) }
                )

                if (postsVisible) {
                    SocialQuickPostInputCard(modifier = Modifier.padding(horizontal = 16.dp), onClickSend = {})

                    posts.forEach { post ->
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
                    if (viewMode == ProfileViewMode.OWNER) {
                        MobileProfileActionsRow(
                            postsSelected = postsVisible,
                            onClickAbout = { viewModel.togglePostTab(false) },
                            onClickPosts = { viewModel.togglePostTab(true) },
                            onClickSettings = { navigator.jumpAndStay(NavigationRoute.FacilitySettings) },
                            onClickNewPost = { navigator.jumpAndStay(NavigationRoute.UserSocialMediaPostAdd) }
                        )
                    }

                    MobileFacilityProfilePhotoGallerySection(galleryStackViewData, viewMode, navigator)

                    MobileFacilityProfileTrainerSection(trainers, viewMode, navigator)

                    MobileFacilityProfileLessonSection(lessonSessionColumnViewData, viewMode, navigator)
                }

                Spacer(Modifier.height(124.dp))
            }
        }

        if (viewMode == ProfileViewMode.VISITOR) {
            MobileFacilityProfileVisitorToolbarComponent(onClickBack = { navigator.popBackStack() })
        }
    }
}

@Composable
private fun MobileFacilityProfileVisitorToolbarComponent(onClickBack: () -> Unit) {
    Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
        SkyFitPrimaryCircularBackButton(onClick = onClickBack)
    }
}

@Composable
fun MobileFacilityProfileScreenInfoCardComponent(
    infoViewData: FacilityProfileInfoViewData,
    viewMode: ProfileViewMode,
    onClickFollow: () -> Unit = {},
    onClickUnFollow: () -> Unit = {},
    onClickBookingCalendar: () -> Unit = {},
    onClickMessage: () -> Unit = {}
) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .background(color = SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
            .padding(24.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = infoViewData.name,
                    style = SkyFitTypography.bodyLargeSemibold,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = infoViewData.socialLink,
                    style = SkyFitTypography.bodySmallMedium,
                    color = SkyFitColor.text.secondary
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VerticalProfileStatisticItem(title = "${infoViewData.memberCount}", subtitle = "Uye")
                VerticalDivider(Modifier.height(48.dp))
                VerticalProfileStatisticItem(title = "${infoViewData.trainerCount}", subtitle = "Egitmen")
                VerticalDivider(Modifier.height(48.dp))
                RatingStarComponent(infoViewData.rating ?: 0.0)
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = infoViewData.bio,
                style = SkyFitTypography.bodySmall,
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(Res.drawable.ic_location_pin),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.default
                )
                Text(
                    text = infoViewData.address,
                    style = SkyFitTypography.bodySmallSemibold,
                    modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                )
            }

            if (viewMode == ProfileViewMode.VISITOR) {
                Spacer(modifier = Modifier.height(16.dp))

                SkyFitButtonComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = if (infoViewData.isFollowed) "Takipten Çık" else "Takip Et",
                    onClick = if (infoViewData.isFollowed) onClickUnFollow else onClickFollow,
                    variant = ButtonVariant.Primary,
                    size = ButtonSize.Large
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    SkyFitButtonComponent(
                        modifier = Modifier.weight(1f),
                        text = "Randevu Al",
                        onClick = onClickBookingCalendar,
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Large,
                        leftIconPainter = painterResource(Res.drawable.ic_calendar_dots)
                    )
                    if (infoViewData.isFollowed) {
                        Spacer(modifier = Modifier.width(10.dp))
                        SkyFitSecondaryIconButton(
                            painter = painterResource(Res.drawable.ic_send),
                            modifier = Modifier.size(44.dp)
                        )
                    }
                }
            }
        }
    }
}


//region PhotoGallery
@Composable
private fun MobileFacilityProfilePhotoGallerySection(
    galleryStackViewData: PhotoGalleryStackViewData?,
    viewMode: ProfileViewMode,
    navigator: Navigator
) {
    if (galleryStackViewData == null) {
        PhotoGalleryEmptyStackCard(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClickAdd = { navigator.jumpAndStay(NavigationRoute.FacilityPhotoGallery) }
        )
    } else {
        PhotoGalleryStackCard(
            viewData = galleryStackViewData,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
//endregion PhotoGallery

//region Trainers
@Composable
private fun MobileFacilityProfileTrainersRow(
    trainers: List<TrainerProfileCardItemViewData>,
    onClick: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Antrenörlerimiz",
            style = SkyFitTypography.bodyLargeSemibold,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
        )
        VerticalTrainerProfileCardsRow(trainers, contentPaddingStart = 12.dp)
    }
}

@Composable
private fun MobileFacilityProfileTrainerSection(
    trainers: List<TrainerProfileCardItemViewData>,
    viewMode: ProfileViewMode,
    navigator: Navigator
) {
    if (trainers.isEmpty()) {
        MobileFacilityTrainersCard(
            onClickAdd = { navigator.jumpAndStay(NavigationRoute.TrainerProfileVisited) }
        )
    } else {
        MobileFacilityProfileTrainersRow(
            trainers = trainers,
            onClick = { navigator.jumpAndStay(NavigationRoute.TrainerProfileVisited) }
        )
    }
}
//endregion Trainers

//region Lessons
@Composable
private fun MobileFacilityProfileLessonSection(
    lessonSessionColumnViewData: LessonSessionColumnViewData?,
    viewMode: ProfileViewMode,
    navigator: Navigator
) {
    if (lessonSessionColumnViewData == null) {
        MobileFacilityLessonsEmptyCard(
            onClickAdd = { navigator.jumpAndStay(NavigationRoute.FacilityManageLessons) }
        )
    } else {
        val actionVisitCalendar: () -> Unit = { navigator.jumpAndStay(NavigationRoute.FacilityCalendarVisited) }
        val actionManageLessons: () -> Unit = { navigator.jumpAndStay(NavigationRoute.FacilityManageLessons) }
        LessonSessionColumn(
            viewData = lessonSessionColumnViewData,
            onClickShowAll = if (viewMode == ProfileViewMode.VISITOR) null else actionManageLessons,
            onClickItem = {
                when (viewMode) {
                    ProfileViewMode.OWNER -> actionManageLessons.invoke()
                    ProfileViewMode.VISITOR -> actionVisitCalendar.invoke()
                }
            }
        )
    }
}
//endregion Lessons