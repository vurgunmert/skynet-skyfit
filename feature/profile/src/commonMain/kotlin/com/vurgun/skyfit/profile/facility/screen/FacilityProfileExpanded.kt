package com.vurgun.skyfit.profile.facility.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.SharedScreen.ChatWithFacility
import com.vurgun.skyfit.core.navigation.SharedScreen.CreatePost
import com.vurgun.skyfit.core.navigation.SharedScreen.FacilityManageLessons
import com.vurgun.skyfit.core.navigation.SharedScreen.FacilitySchedule
import com.vurgun.skyfit.core.navigation.SharedScreen.FacilityTrainerSettings
import com.vurgun.skyfit.core.navigation.SharedScreen.Settings
import com.vurgun.skyfit.core.navigation.SharedScreen.TrainerProfile
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.profile.SocialPostCard
import com.vurgun.skyfit.core.ui.components.profile.SocialQuickPostInputCard
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberCalendarWeekDaySelectorController
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberWeekDaySelectorState
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.special.RatingButton
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.profile.component.ProfileCompactComponent
import com.vurgun.skyfit.profile.component.ProfileExpandedComponent
import com.vurgun.skyfit.profile.facility.screen.FacilityProfileCompactComponent.FacilityProfileTrainerCards
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_location_pin
import skyfit.core.ui.generated.resources.member_label
import skyfit.core.ui.generated.resources.trainer_label

@Composable
fun FacilityProfileExpanded(
    viewModel: FacilityProfileViewModel,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val localNavigator = LocalNavigator.currentOrThrow
    val compactOverlayController = LocalCompactOverlayController.current
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            FacilityProfileUiEffect.NavigateBack -> {
                localNavigator.pop()
            }

            FacilityProfileUiEffect.NavigateToCreatePost -> {
                compactOverlayController?.invoke(CreatePost)
            }

            FacilityProfileUiEffect.NavigateToGallery -> {}
            FacilityProfileUiEffect.NavigateToLessonListing -> {
                compactOverlayController?.invoke(FacilityManageLessons)
            }

            FacilityProfileUiEffect.NavigateToSettings -> {
                localNavigator.replace(Settings)
            }

            FacilityProfileUiEffect.NavigateToExplore -> {
                localNavigator.replace(SharedScreen.Explore)
            }

            is FacilityProfileUiEffect.NavigateToVisitTrainer -> {
                localNavigator.replace(TrainerProfile(effect.trainerId))
            }

            is FacilityProfileUiEffect.NavigateToFacilityChat -> {
                compactOverlayController?.invoke(ChatWithFacility(effect.facilityId))
            }

            is FacilityProfileUiEffect.NavigateToFacilitySchedule -> {
                compactOverlayController?.invoke(FacilitySchedule(effect.facilityId))
            }
            FacilityProfileUiEffect.ShareProfile -> {

            }

            FacilityProfileUiEffect.NavigateToTrainerSettings -> {
                compactOverlayController?.invoke(FacilityTrainerSettings)
            }
        }
    }

    when (uiState) {
        FacilityProfileUiState.Loading ->
            FullScreenLoaderContent()

        is FacilityProfileUiState.Error -> {
            val message = (uiState as FacilityProfileUiState.Error).message
            ErrorScreen(message = message) { }
        }

        is FacilityProfileUiState.Content -> {
            val content = uiState as FacilityProfileUiState.Content

            ProfileExpandedComponent.Layout(
                header = {
                    FacilityProfileExpandedComponent.Header(content, viewModel::onAction)
                },
                content = {
                    Row(
                        Modifier.fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SkyFitColor.background.surfaceTertiary)
                            .padding(16.dp)
                    ) {
                        FacilityProfileExpandedComponent.AboutContent(
                            content,
                            viewModel::onAction,
                            modifier = Modifier.width(444.dp).fillMaxHeight()
                        )
                        Spacer(Modifier.width(16.dp))
                        FacilityProfileExpandedComponent.PostsContent(
                            content,
                            viewModel::onAction,
                            modifier = Modifier.weight(1f).fillMaxHeight()
                        )
                    }
                },
                modifier = modifier
            )
        }
    }
}

private object FacilityProfileExpandedComponent {

    @Composable
    fun Header(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val profile = content.profile

        ProfileExpandedComponent.Header(
            backgroundImageUrl = profile.backgroundImageUrl,
            profileImageUrl = null,
            leftContent = {
                ProfileCompactComponent.HeaderBodyGroup(
                    leftItem = {
                        ProfileCompactComponent.HeaderEditorialDataItem(
                            title = profile.memberCount.toString(),
                            subtitle = stringResource(Res.string.member_label),
                            modifier = Modifier.weight(1f)
                        )
                    },
                    centerItem = {
                        ProfileCompactComponent.HeaderEditorialDataItem(
                            title = profile.trainerCount.toString(),
                            subtitle = stringResource(Res.string.trainer_label),
                            modifier = Modifier.weight(1f)
                        )
                    },
                    rightItem = {
                        Box(
                            Modifier.weight(1f).fillMaxHeight(),
                            contentAlignment = Alignment.Center
                        ) {
                            RatingButton(
                                rating = profile.point,
                                modifier = Modifier
                            )
                        }
                    }
                )
            },
            centerContent = {
                Column(
                    Modifier
                        .weight(1f).padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    ProfileCompactComponent.HeaderNameGroup(
                        firstName = profile.facilityName,
                        userName = profile.username
                    )

                    Spacer(Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.Top)
                    {
                        SkyIcon(
                            res = Res.drawable.ic_location_pin,
                            size = SkyIconSize.Small
                        )
                        SkyText(
                            text = profile.gymAddress,
                            styleType = TextStyleType.BodySmallSemibold,
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                    }
                }
            },
            rightContent = {
                FeatureVisible(false) { //Share
                    ProfileExpandedComponent.HeaderNavigationGroup(
                        destination = content.destination,
                        onClickShare = { onAction(FacilityProfileUiAction.OnClickShare) }
                    )
                }
            }
        )
    }

    @Composable
    fun AboutContent(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {

        Column(modifier.fillMaxSize()) {

            FacilityProfileLessonsGroup(content, onAction, Modifier.fillMaxWidth())

            Spacer(Modifier.height(16.dp))

            FacilityProfileTrainersGroup(content, onAction, Modifier.fillMaxWidth())

            Spacer(Modifier.height(132.dp))
        }
    }

    @Composable
    private fun FacilityProfileLessonsGroup(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileUiAction) -> Unit,
        modifier: Modifier = Modifier) {

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
                    .clip(RoundedCornerShape(20.dp))
                    .background(SkyFitColor.background.default)
            )
        } else {
            if (content.lessons.isEmpty()) {
                ProfileCompactComponent.NoScheduledLessonsCard(
                    onClickAdd = { onAction(FacilityProfileUiAction.OnClickAllLessons) },
                    cardModifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(SkyFitColor.background.default)
                )
            } else {
                ProfileCompactComponent.LessonSchedule(
                    lessons = content.lessons,
                    onClickShowAll = { onAction(FacilityProfileUiAction.OnClickAllLessons) },
                    onClickLesson = { onAction(FacilityProfileUiAction.OnClickAllLessons) },
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(SkyFitColor.background.default)
                )
            }
        }
    }

    @Composable
    private fun FacilityProfileTrainersGroup(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {

        Column(
            modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .background(SkyFitColor.background.default)
                .padding(vertical = 16.dp)
        ) {
            SkyText(
                text = content.profile.bio,
                styleType = TextStyleType.BodySmallSemibold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            FacilityProfileTrainerCards(
                trainers = content.trainers,
                onAction = onAction
            )
        }
    }

    @Composable
    fun PostsContent(
        content: FacilityProfileUiState.Content,
        onAction: (FacilityProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!content.isVisiting) {
                SocialQuickPostInputCard(
                    creatorImageUrl = content.profile.backgroundImageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SkyFitColor.background.default, RoundedCornerShape(16.dp))
                ) { onAction(FacilityProfileUiAction.OnSendQuickPost(it)) }
            }

            content.posts.forEach { post ->
                SocialPostCard(
                    post = post,
                    onClick = { onAction(FacilityProfileUiAction.OnClickPost) },
                    onClickComment = { onAction(FacilityProfileUiAction.OnClickCommentPost) },
                    onClickLike = { onAction(FacilityProfileUiAction.OnClickLikePost) },
                    onClickShare = { onAction(FacilityProfileUiAction.OnClickSharePost) }
                )
            }
        }
    }
}