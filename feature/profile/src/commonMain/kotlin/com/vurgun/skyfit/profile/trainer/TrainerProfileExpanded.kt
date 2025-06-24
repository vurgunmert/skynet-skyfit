package com.vurgun.skyfit.profile.trainer

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
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.replace
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.button.SkyButtonSize
import com.vurgun.skyfit.core.ui.components.button.SkyButtonVariant
import com.vurgun.skyfit.core.ui.components.button.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.profile.SocialPostCard
import com.vurgun.skyfit.core.ui.components.profile.SocialQuickPostInputCard
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberCalendarWeekDaySelectorController
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberWeekDaySelectorState
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.profile.component.ProfileCompactComponent
import com.vurgun.skyfit.profile.component.ProfileExpandedComponent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
fun TrainerProfileExpanded(
    viewModel: TrainerProfileViewModel,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val localNavigator = LocalNavigator.currentOrThrow
    val appNavigator = localNavigator.findRootNavigator()
    val overlayController = LocalCompactOverlayController.current
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            TrainerProfileUiEffect.NavigateBack -> {
                localNavigator.pop()
            }

            TrainerProfileUiEffect.NavigateToAppointments -> {
                overlayController?.invoke(SharedScreen.TrainerAppointmentListing)
            }

            is TrainerProfileUiEffect.NavigateToChatWithTrainer -> {
                localNavigator.pop()
            }

            TrainerProfileUiEffect.NavigateToCreatePost -> {
                overlayController?.invoke(SharedScreen.NewPost)
            }

            is TrainerProfileUiEffect.NavigateToLessonDetail -> {
                overlayController?.invoke(SharedScreen.TrainerAppointmentDetail(effect.lessonId))
            }

            TrainerProfileUiEffect.NavigateToSettings -> {
                localNavigator.replace(SharedScreen.Settings)
            }

            is TrainerProfileUiEffect.NavigateToTrainerSchedule -> {
                overlayController?.invoke(SharedScreen.TrainerSchedule(effect.trainerId))
            }
        }
    }

    when (uiState) {
        TrainerProfileUiState.Loading ->
            FullScreenLoaderContent()

        is TrainerProfileUiState.Error -> {
            val message = (uiState as TrainerProfileUiState.Error).message
            ErrorScreen(message = message) { }
        }

        is TrainerProfileUiState.Content -> {
            val content = uiState as TrainerProfileUiState.Content

            ProfileExpandedComponent.Layout(
                header = {
                    TrainerProfileExpandedComponent.Header(content, viewModel::onAction)
                },
                content = {
                    Row(Modifier.weight(1f)) {
                        TrainerProfileExpandedComponent.AboutContent(
                            content,
                            viewModel::onAction,
                            modifier = Modifier.width(444.dp).fillMaxHeight()
                        )
                        Spacer(Modifier.width(16.dp))
                        TrainerProfileExpandedComponent.PostsContent(
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

private object TrainerProfileExpandedComponent {

    @Composable
    fun Header(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        val trainer = content.profile

        Box(modifier.fillMaxWidth()) {
            ProfileExpandedComponent.Header(
                backgroundImageUrl = trainer.backgroundImageUrl,
                profileImageUrl = trainer.profileImageUrl,
                leftContent = {
                    ProfileCompactComponent.HeaderBodyGroup(
                        leftItem = {
                            ProfileCompactComponent.HeaderEditorialDataItem(
                                title = content.profile.followerCount.toString(),
                                subtitle = stringResource(Res.string.follower_label),
                                modifier = Modifier.weight(1f)
                            )
                        },
                        centerItem = {
                            ProfileCompactComponent.HeaderEditorialDataItem(
                                title = trainer.lessonCount.toString(),
                                subtitle = stringResource(Res.string.lesson_count_label),
                                modifier = Modifier.weight(1f)
                            )
                        },
                        rightItem = {
                            ProfileCompactComponent.HeaderEditorialDataItem(
                                title = trainer.postCount.toString(),
                                subtitle = stringResource(Res.string.posts_label),
                                modifier = Modifier.weight(1f)
                            )
                        }
                    )
                },
                centerContent = {
                    ProfileCompactComponent.HeaderNameGroup(
                        firstName = trainer.firstName,
                        userName = trainer.username
                    )
                },
                rightContent = {

                    Row {
                        FeatureVisible(content.isVisiting) {
                            SkyButton(
                                label = stringResource(Res.string.appointment_book_action),
                                leftIcon = painterResource(Res.drawable.ic_calendar_dots),
                                size = SkyButtonSize.Micro,
                                variant = SkyButtonVariant.Secondary,
                                onClick = { onAction(TrainerProfileUiAction.OnClickBookAppointment) }
                            )
                        }

                        FeatureVisible(false) {
                            Spacer(Modifier.width(16.dp))

                            ProfileExpandedComponent.HeaderNavigationGroup(
                                destination = content.destination,
                                onClickShare = null //{ onAction(TrainerProfileUiAction.OnClickShareProfile) }
                            )
                        }
                    }
                }
            )

            if (content.isVisiting) {
                SkyFitPrimaryCircularBackButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(top = 48.dp, start = 24.dp)
                        .size(48.dp),
                    onClick = { onAction(TrainerProfileUiAction.OnClickBack) }
                )
            }
        }
    }

    @Composable
    fun AboutContent(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(modifier.fillMaxSize()) {

            TrainerProfileLessonsGroup(content, onAction, Modifier.fillMaxWidth())

            Spacer(Modifier.height(132.dp))
        }
    }

    @Composable
    fun PostsContent(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit,
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
                ) { onAction(TrainerProfileUiAction.OnSendQuickPost(it)) }
            }

            content.posts.forEach { post ->
                SocialPostCard(
                    post = post,
                    onClick = { onAction(TrainerProfileUiAction.OnClickPost) },
                    onClickComment = { onAction(TrainerProfileUiAction.OnClickCommentPost) },
                    onClickLike = { onAction(TrainerProfileUiAction.OnClickLikePost) },
                    onClickShare = { onAction(TrainerProfileUiAction.OnClickSharePost) }
                )
            }
        }
    }

    @Composable
    private fun TrainerSpecialitiesGroup(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {

        Column(
            modifier
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .background(SkyFitColor.background.default)
                .padding(vertical = 16.dp)
        ) {
            // TODO: Specialities

            SkyText(
                text = content.profile.bio,
                styleType = TextStyleType.BodySmallSemibold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
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
                Spacer(Modifier.width(4.dp))
                SkyText(
                    text = content.profile.gymName.toString(),
                    styleType = TextStyleType.BodySmallSemibold,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }

    @Composable
    private fun TrainerProfileLessonsGroup(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit,
        modifier: Modifier = Modifier
    ) {

        val weekDaySelectorController = rememberCalendarWeekDaySelectorController()
        val calendarUiState = rememberWeekDaySelectorState(weekDaySelectorController)

        if (content.isVisiting) {
            LaunchedEffect(calendarUiState.selectedDate) {
                onAction(TrainerProfileUiAction.ChangeDate(calendarUiState.selectedDate))
            }
        }

        if (content.isVisiting) {
            ProfileCompactComponent.WeeklyLessonScheduleGroup(
                calendarUiState = calendarUiState,
                calendarViewModel = weekDaySelectorController,
                lessons = content.lessons,
                goToVisitCalendar = { onAction(TrainerProfileUiAction.OnClickShowSchedule) },
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(SkyFitColor.background.default)
            )
        } else {
            if (content.lessons.isEmpty()) {
                ProfileCompactComponent.NoScheduledLessonsCard(
                    cardModifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(SkyFitColor.background.default)
                )
            } else {
                ProfileCompactComponent.LessonSchedule(
                    lessons = content.lessons,
                    onClickShowAll = { onAction(TrainerProfileUiAction.OnClickShowAllLessons) },
                    onClickLesson = { onAction(TrainerProfileUiAction.OnClickToUpcomingLesson(it.lessonId)) },
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(SkyFitColor.background.default)
                )
            }
        }
    }
}