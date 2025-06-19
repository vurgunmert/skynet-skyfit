package com.vurgun.skyfit.profile.facility.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.profile.PhotoGalleryStackViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.SharedScreen.*
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.core.ui.components.button.SkyFitSecondaryIconButton
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.profile.*
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberCalendarWeekDaySelectorController
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberWeekDaySelectorState
import com.vurgun.skyfit.core.ui.components.special.*
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.profile.component.ProfileCompactComponent
import com.vurgun.skyfit.profile.model.ProfileDestination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
internal fun FacilityProfileCompact(
    viewModel: FacilityProfileViewModel
) {
    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val localNavigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            FacilityProfileUiEffect.NavigateBack -> {
                localNavigator.pop()
            }

            FacilityProfileUiEffect.NavigateToCreatePost -> {
                appNavigator.push(SharedScreen.CreatePost)
            }

            FacilityProfileUiEffect.NavigateToGallery -> {}
            FacilityProfileUiEffect.NavigateToLessonListing -> {
                appNavigator.push(SharedScreen.FacilityManageLessons)
            }

            FacilityProfileUiEffect.NavigateToSettings -> {
                appNavigator.push(SharedScreen.Settings)
            }

            FacilityProfileUiEffect.NavigateToTrainers -> {
                appNavigator.push(SharedScreen.ExploreTrainers)
            }

            is FacilityProfileUiEffect.NavigateToVisitTrainer -> {
                appNavigator.push(TrainerProfileVisitor(effect.trainerId))
            }

            is FacilityProfileUiEffect.NavigateToFacilityChat -> {
                appNavigator.push(SharedScreen.ChatWithFacility(effect.facilityId))
            }

            is FacilityProfileUiEffect.NavigateToFacilitySchedule -> {
                appNavigator.push(SharedScreen.FacilitySchedule(effect.facilityId))
            }
        }
    }

    when (uiState) {
        is FacilityProfileUiState.Loading -> {
            FullScreenLoaderContent()
        }

        is FacilityProfileUiState.Error -> {
            val message = (uiState as FacilityProfileUiState.Error).message
            ErrorScreen(
                message = message,
                onConfirm = { viewModel.onAction(FacilityProfileUiAction.NavigateBack) })
        }

        is FacilityProfileUiState.Content -> {
            val content = (uiState as FacilityProfileUiState.Content)
            FacilityProfileCompactComponent.Content(content, viewModel::onAction)
        }
    }
}


internal object FacilityProfileCompactComponent {

    @Composable
    fun Content(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileUiAction) -> Unit
    ) {

        ProfileCompactComponent.Layout(
            header = { Header(content, onAction) },
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ProfileCompactComponent.NavigationMenuWithAction(
                        onDestinationChanged = { onAction(FacilityProfileUiAction.OnDestinationChanged(it)) },
                        destination = content.destination,
                        action = {
                            if (content.destination == ProfileDestination.Posts) {
                                if (!content.isVisiting) {
                                    ProfileCompactComponent.NavigationMenuAction(
                                        res = Res.drawable.ic_plus,
                                        onClick = { onAction(FacilityProfileUiAction.OnClickNewPost) }
                                    )
                                }
                            } else {
                                if (!content.isVisiting) {
                                    ProfileCompactComponent.NavigationMenuAction(
                                        res = Res.drawable.ic_settings,
                                        onClick = { onAction(FacilityProfileUiAction.OnClickSettings) }
                                    )
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (content.destination == ProfileDestination.Posts) {
                        PostsContent(content, onAction)
                    } else {
                        AboutContent(content, onAction)
                    }

                    Spacer(Modifier.height(124.dp))
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }

    //region Trainers
    @Composable
    private fun MobileFacilityProfileTrainersRow(
        trainers: List<FacilityTrainerProfile>,
        onClick: () -> Unit
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(Res.string.our_trainers_label),
                style = SkyFitTypography.bodyLargeSemibold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp)
            )
            VerticalTrainerProfileCardsRow(
                trainers = trainers,
                onClick = onClick,
                contentPaddingStart = 12.dp
            )
        }
    }

    @Composable
    fun TrainerCards(
        trainers: List<FacilityTrainerProfile>,
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
    fun Header(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileUiAction) -> Unit
    ) {
        val profile = content.profile

        ProfileCompactComponent.Header(
            backgroundImageUrl = profile.backgroundImageUrl,
            backgroundImageModifier = Modifier.fillMaxWidth().height(240.dp),
            profileImageUrl = null,
            cardContents = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SkyText(
                        text = profile.facilityName,
                        styleType = TextStyleType.BodyLargeSemibold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    SkyText(
                        text = profile.username,
                        styleType = TextStyleType.BodySmallMedium,
                        color = SkyFitColor.text.secondary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    VerticalProfileStatisticItem(
                        title = "${profile.memberCount}",
                        subtitle = stringResource(Res.string.member_label)
                    )
                    VerticalDivider(Modifier.height(48.dp))
                    VerticalProfileStatisticItem(
                        title = "${profile.trainerCount}",
                        subtitle = stringResource(Res.string.trainer_label)
                    )
                    VerticalDivider(Modifier.height(48.dp))
                    RatingStarComponent(profile.point ?: 0f)
                }

                Spacer(Modifier.height(16.dp))

                SkyText(
                    text = profile.bio,
                    styleType = TextStyleType.BodySmall,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    SkyIcon(
                        res = Res.drawable.ic_location_pin,
                        size = SkyIconSize.Small
                    )
                    SkyText(
                        text = profile.gymAddress,
                        styleType = TextStyleType.BodySmallSemibold,
                        modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                    )
                }

                if (content.isVisiting) {
                    Spacer(modifier = Modifier.height(16.dp))

                    FeatureVisible(true) {
                        SkyFitButtonComponent(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(if (content.isVisitorFollowing) Res.string.unfollow_action else Res.string.follow_action),
                            onClick = { onAction(FacilityProfileUiAction.OnToggleFollow) },
                            variant = ButtonVariant.Primary,
                            size = ButtonSize.Large
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SkyFitButtonComponent(
                            modifier = Modifier.weight(1f),
                            text = stringResource(Res.string.appointment_book_action),
                            onClick = { onAction(FacilityProfileUiAction.OnClickShowSchedule) },
                            variant = ButtonVariant.Secondary,
                            size = ButtonSize.Large,
                            leftIconPainter = painterResource(Res.drawable.ic_calendar_dots)
                        )
                        if (content.isVisitorFollowing) {
                            Spacer(modifier = Modifier.width(10.dp))
                            SkyFitSecondaryIconButton(
                                painter = painterResource(Res.drawable.ic_send),
                                modifier = Modifier.size(44.dp)
                            )
                        }
                    }
                }
            }
        )
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
                    VerticalProfileStatisticItem(
                        title = "${profile.memberCount}",
                        subtitle = stringResource(Res.string.member_label)
                    )
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
    fun AboutContent(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileUiAction) -> Unit
    ) {
        val weekDaySelectorController = rememberCalendarWeekDaySelectorController()
        val calendarUiState = rememberWeekDaySelectorState(weekDaySelectorController)

        if (content.isVisiting) {
            LaunchedEffect(calendarUiState.selectedDate) {
                onAction(FacilityProfileUiAction.ChangeDate(calendarUiState.selectedDate))
            }
        }

        if (content.isVisiting) {
            ProfileCompactComponent.WeeklyLessonScheduleGroup(
                calendarUiState = calendarUiState,
                calendarViewModel = weekDaySelectorController,
                lessons = content.lessons,
                goToVisitCalendar = { onAction(FacilityProfileUiAction.OnClickShowSchedule) },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            if (content.lessons.isEmpty()) {
                ProfileCompactComponent.NoScheduledLessonsCard()
            } else {
                ProfileCompactComponent.LessonSchedule(
                    lessons = content.lessons,
                    goToLessons = { onAction(FacilityProfileUiAction.OnClickAllLessons) }
                )
            }
        }
    }

    @Composable
    fun PostsContent(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileUiAction) -> Unit
    ) {
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
    }
}