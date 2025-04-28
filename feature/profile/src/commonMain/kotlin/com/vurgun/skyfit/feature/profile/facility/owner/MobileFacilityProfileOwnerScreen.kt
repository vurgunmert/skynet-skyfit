package com.vurgun.skyfit.feature.profile.facility.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.vurgun.skyfit.core.data.domain.model.FacilityProfile
import com.vurgun.skyfit.feature.calendar.component.weekly.CalendarWeekDaySelector
import com.vurgun.skyfit.feature.calendar.component.weekly.CalendarWeekDaySelectorState
import com.vurgun.skyfit.feature.calendar.component.weekly.CalendarWeekDaySelectorViewModel
import com.vurgun.skyfit.feature.profile.components.MobileProfileActionsRow
import com.vurgun.skyfit.feature.profile.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature.profile.components.PhotoGalleryEmptyStackCard
import com.vurgun.skyfit.feature.profile.components.PhotoGalleryStackCard
import com.vurgun.skyfit.feature.profile.components.VerticalProfileStatisticItem
import com.vurgun.skyfit.feature.profile.components.VerticalTrainerProfileCardsRow
import com.vurgun.skyfit.feature.profile.components.viewdata.PhotoGalleryStackViewData
import com.vurgun.skyfit.feature.profile.components.viewdata.TrainerProfileCardItemViewData
import com.vurgun.skyfit.feature.social.components.SocialPostCard
import com.vurgun.skyfit.feature.social.components.SocialQuickPostInputCard
import com.vurgun.skyfit.core.ui.components.button.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.core.ui.components.button.SkyFitSecondaryIconButton
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.event.LessonSessionColumn
import com.vurgun.skyfit.core.ui.components.event.NoLessonOnSelectedDaysEventItem
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoader
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonState
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.RatingStarComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.add_trainer_action
import skyfit.core.ui.generated.resources.appointment_book_action
import skyfit.core.ui.generated.resources.follow_action
import skyfit.core.ui.generated.resources.ic_calendar_dots
import skyfit.core.ui.generated.resources.ic_location_pin
import skyfit.core.ui.generated.resources.ic_send
import skyfit.core.ui.generated.resources.lessons_label
import skyfit.core.ui.generated.resources.member_label
import skyfit.core.ui.generated.resources.our_trainers_label
import skyfit.core.ui.generated.resources.show_all_action
import skyfit.core.ui.generated.resources.trainer_label
import skyfit.core.ui.generated.resources.unfollow_action

@Composable
fun MobileFacilityProfileOwnerScreen(
    goToBack: () -> Unit,
    goToLessonListing: () -> Unit,
    goToSettings: () -> Unit,
    goToCreatePost: () -> Unit,
    goToTrainers: () -> Unit,
    goToPhotoGallery: () -> Unit,
    viewModel: FacilityProfileOwnerViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                FacilityProfileOwnerEffect.NavigateBack -> goToBack()
                FacilityProfileOwnerEffect.NavigateToCreatePost -> goToCreatePost()
                FacilityProfileOwnerEffect.NavigateToGallery -> goToPhotoGallery()
                FacilityProfileOwnerEffect.NavigateToLessonListing -> goToLessonListing()
                FacilityProfileOwnerEffect.NavigateToSettings -> goToSettings()
                FacilityProfileOwnerEffect.NavigateToTrainers -> goToTrainers()
            }
        }
    }

    when (uiState) {
        is FacilityProfileOwnerUiState.Loading -> FullScreenLoader()
        is FacilityProfileOwnerUiState.Error -> {
            val message = (uiState as FacilityProfileOwnerUiState.Error).message
            ErrorScreen(message = message, onBack = goToBack)
        }

        is FacilityProfileOwnerUiState.Content -> {
            val content = (uiState as FacilityProfileOwnerUiState.Content)
            MobileFacilityProfileOwnerContent(
                content = content,
                onAction = viewModel::onAction
            )
        }
    }
}

@Composable
private fun MobileFacilityProfileOwnerContent(
    content: FacilityProfileOwnerUiState.Content,
    onAction: (FacilityProfileOwnerAction) -> Unit
) {
    var backgroundAlpha by remember { mutableStateOf(1f) }
    val transitionThreshold = 300f

    val scrollState = rememberScrollState()
    LaunchedEffect(scrollState.value) {
        val scrollY = scrollState.value.toFloat()
        backgroundAlpha = when {
            scrollY >= transitionThreshold -> 0f
            else -> (1f - (scrollY / transitionThreshold))
        }
    }

    SkyFitMobileScaffold {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(SkyFitColor.background.default)
        ) {
            val width = maxWidth
            val imageHeight = width * 9 / 16

            MobileProfileBackgroundImage(
                imageUrl = content.profile.backgroundImageUrl,
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
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.height(contentTopPadding))

                FacilityProfileComponent.MobileFacilityProfileOwner_HeaderCard(
                    profile = content.profile
                )

                MobileProfileActionsRow(
                    postsSelected = content.postsVisible,
                    onClickAbout = { onAction(FacilityProfileOwnerAction.TogglePostVisibility(false)) },
                    onClickPosts = { onAction(FacilityProfileOwnerAction.TogglePostVisibility(true)) },
                    onClickSettings = { onAction(FacilityProfileOwnerAction.NavigateToSettings) },
                    onClickNewPost = { onAction(FacilityProfileOwnerAction.NavigateToCreatePost) },
                )

                if (content.postsVisible) {
                    SocialQuickPostInputCard(modifier = Modifier.padding(horizontal = 16.dp), onClickSend = {})

                    content.posts.forEach { post ->
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
                    FacilityProfileComponent.MobileFacilityProfileOwner_Lessons(
                        lessons = content.lessons,
                        goToLessons = { onAction(FacilityProfileOwnerAction.NavigateToLessonListing) },
                        modifier = Modifier
                    )

//                        MobileFacilityProfilePhotoGallerySection(
//                            galleryStackViewData, viewMode,
//                            goToPhotoGallery = goToPhotoGallery,
//                        )

                    FacilityProfileComponent.MobileFacilityProfileOwner_Trainers(
                        trainers = content.trainers,
                        goToVisitTrainerProfile = { onAction(FacilityProfileOwnerAction.NavigateToTrainers) },
                    )
                }

                Spacer(Modifier.height(124.dp))
            }
        }
    }
}

internal object FacilityProfileComponent {

    //region Trainers
    @Composable
    private fun MobileFacilityProfileTrainersRow(
        trainers: List<TrainerProfileCardItemViewData>,
        onClick: () -> Unit
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.our_trainers_label),
                style = SkyFitTypography.bodyLargeSemibold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
            )
            VerticalTrainerProfileCardsRow(trainers, contentPaddingStart = 12.dp)
        }
    }

    @Composable
    fun MobileFacilityProfileOwner_Trainers(
        trainers: List<TrainerProfileCardItemViewData>,
        goToVisitTrainerProfile: () -> Unit,
    ) {
        if (trainers.isEmpty()) {
            MobileFacilityProfileOwner_NoTrainerCard(onClickAdd = goToVisitTrainerProfile)
        } else {
            MobileFacilityProfileTrainersRow(
                trainers = trainers,
                onClick = goToVisitTrainerProfile
            )
        }
    }

    @Composable
    fun MobileFacilityProfileVisitor_Trainers(
        trainers: List<TrainerProfileCardItemViewData>,
        goToVisitTrainerProfile: () -> Unit,
    ) {
        if (trainers.isEmpty()) return
        MobileFacilityProfileTrainersRow(
            trainers = trainers,
            onClick = goToVisitTrainerProfile
        )
    }

    @Composable
    fun MobileFacilityProfileOwner_NoTrainerCard(onClickAdd: () -> Unit) {
        Column(Modifier.fillMaxWidth()) {
            Text(stringResource(Res.string.our_trainers_label), style = SkyFitTypography.bodyLargeSemibold)
            Spacer(Modifier.height(16.dp))
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                    .padding(vertical = 34.dp),
                contentAlignment = Alignment.Center
            ) {
                SkyFitButtonComponent(
                    modifier = Modifier.wrapContentWidth(),
                    text = stringResource(Res.string.add_trainer_action),
                    onClick = onClickAdd,
                    variant = ButtonVariant.Primary,
                    size = ButtonSize.Medium,
                    state = ButtonState.Rest
                )
            }
        }
    }
//endregion Trainers

    //region Lessons
    @Composable
    fun MobileFacilityProfileOwner_Lessons(
        lessons: List<LessonSessionItemViewData>,
        goToLessons: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        if (lessons.isEmpty()) {
            MobileFacilityProfileOwner_LessonsEmptyCard(
                onClickAdd = goToLessons
            )
        } else {
            LessonSessionColumn(
                lessons = lessons,
                onClickShowAll = goToLessons,
                onClickItem = { goToLessons() },
                modifier = modifier
            )
        }
    }

    @Composable
    fun MobileFacilityProfileVisitor_Lessons(
        calendarUiState: CalendarWeekDaySelectorState,
        calendarViewModel: CalendarWeekDaySelectorViewModel,
        lessons: List<LessonSessionItemViewData>,
        goToVisitCalendar: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        Column(
            modifier.fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(SkyFitColor.background.fillTransparent)
                .clickable(onClick = goToVisitCalendar)
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
                    text = stringResource(Res.string.lessons_label),
                    style = SkyFitTypography.bodyLargeSemibold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = stringResource(Res.string.show_all_action),
                    style = SkyFitTypography.bodyXSmall,
                    color = SkyFitColor.border.secondaryButton,
                    modifier = Modifier.clickable(onClick = goToVisitCalendar)
                )
            }

            CalendarWeekDaySelector(
                daysOfWeek = calendarUiState.weekDays,
                onDaySelected = calendarViewModel::setSelectedDate,
                onPreviousWeek = calendarViewModel::loadPreviousWeek,
                onNextWeek = calendarViewModel::loadNextWeek
            )

            if (lessons.isEmpty()) {
                NoLessonOnSelectedDaysEventItem()
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    lessons.forEach { item ->
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
    }
    //endregion Lessons

    //region PhotoGallery
    @Composable
    fun MobileFacilityProfileOwner_PhotoGallery(
        galleryStackViewData: PhotoGalleryStackViewData?,
        goToPhotoGallery: () -> Unit
    ) {
        if (galleryStackViewData == null) {
            PhotoGalleryEmptyStackCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClickAdd = goToPhotoGallery
            )
        } else {
            PhotoGalleryStackCard(
                viewData = galleryStackViewData,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }

    @Composable
    fun MobileFacilityProfileVisitor_PhotoGallery(
        galleryStackViewData: PhotoGalleryStackViewData?,
        goToPhotoGallery: () -> Unit
    ) {
        if (galleryStackViewData == null) return

        PhotoGalleryStackCard(
            viewData = galleryStackViewData,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
    //endregion PhotoGallery

    @Composable
    fun MobileFacilityProfileVisitor_TopBar(onClickBack: () -> Unit) {
        Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
            SkyFitPrimaryCircularBackButton(onClick = onClickBack)
        }
    }

    @Composable
    fun MobileFacilityProfileOwner_HeaderCard(profile: FacilityProfile) {
        BoxWithConstraints(
            modifier = Modifier
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
                        text = profile.facilityName,
                        style = SkyFitTypography.bodyLargeSemibold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = profile.username,
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
                    VerticalProfileStatisticItem(title = "${profile.memberCount}", subtitle = stringResource(Res.string.member_label))
                    VerticalDivider(Modifier.height(48.dp))
                    VerticalProfileStatisticItem(
                        title = "${profile.trainerCount}",
                        subtitle = stringResource(Res.string.trainer_label)
                    )
                    VerticalDivider(Modifier.height(48.dp))
                    RatingStarComponent(profile.point ?: 0f)
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = profile.bio,
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
                        text = profile.gymAddress,
                        style = SkyFitTypography.bodySmallSemibold,
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun MobileFacilityProfileVisitor_HeaderCard(
        profile: FacilityProfile,
        isFollowedByVisitor: Boolean,
        onClickFollow: () -> Unit,
        onClickUnFollow: () -> Unit,
        onClickBookingCalendar: () -> Unit,
        onClickMessage: () -> Unit
    ) {
        BoxWithConstraints(
            modifier = Modifier
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
                        text = profile.facilityName,
                        style = SkyFitTypography.bodyLargeSemibold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = profile.username,
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
                    VerticalProfileStatisticItem(title = "${profile.memberCount}", subtitle = stringResource(Res.string.member_label))
                    VerticalDivider(Modifier.height(48.dp))
                    VerticalProfileStatisticItem(
                        title = "${profile.trainerCount}",
                        subtitle = stringResource(Res.string.trainer_label)
                    )
                    VerticalDivider(Modifier.height(48.dp))
                    RatingStarComponent(profile.point ?: 0f)
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = profile.bio,
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
                        text = profile.gymAddress,
                        style = SkyFitTypography.bodySmallSemibold,
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                SkyFitButtonComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(if (isFollowedByVisitor) Res.string.unfollow_action else Res.string.follow_action),
                    onClick = if (isFollowedByVisitor) onClickUnFollow else onClickFollow,
                    variant = ButtonVariant.Primary,
                    size = ButtonSize.Large
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    SkyFitButtonComponent(
                        modifier = Modifier.weight(1f),
                        text = stringResource(Res.string.appointment_book_action),
                        onClick = onClickBookingCalendar,
                        variant = ButtonVariant.Secondary,
                        size = ButtonSize.Large,
                        leftIconPainter = painterResource(Res.drawable.ic_calendar_dots)
                    )
                    if (isFollowedByVisitor) {
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

    @Composable
    private fun MobileFacilityProfileOwner_LessonsEmptyCard(onClickAdd: () -> Unit) {
        Column(Modifier.fillMaxWidth().padding(16.dp)) {
            Text("Ozel Dersler", style = SkyFitTypography.bodyLargeSemibold)
            Spacer(Modifier.height(16.dp))
            Box(
                Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                    .padding(vertical = 34.dp),
                contentAlignment = Alignment.Center
            ) {
                SkyFitButtonComponent(
                    modifier = Modifier.wrapContentWidth(),
                    text = "Ders Ekle",
                    onClick = onClickAdd,
                    variant = ButtonVariant.Primary,
                    size = ButtonSize.Medium,
                    state = ButtonState.Rest
                )
            }
        }
    }
}