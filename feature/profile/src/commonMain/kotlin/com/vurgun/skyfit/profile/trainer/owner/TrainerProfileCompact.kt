package com.vurgun.skyfit.profile.trainer.owner

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
import com.vurgun.skyfit.core.navigation.SharedScreen.TrainerAppointmentDetail
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SecondaryIconButton
import com.vurgun.skyfit.core.ui.components.button.SkyButton
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.profile.LifestyleActionRow
import com.vurgun.skyfit.core.ui.components.profile.SocialPostCard
import com.vurgun.skyfit.core.ui.components.profile.SocialQuickPostInputCard
import com.vurgun.skyfit.core.ui.components.profile.VerticalProfileStatisticItem
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberCalendarWeekDaySelectorController
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberWeekDaySelectorState
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.FeatureVisible
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.text.SkyText
import com.vurgun.skyfit.core.ui.components.text.TextStyleType
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.profile.component.ProfileCompactComponent
import com.vurgun.skyfit.profile.model.ProfileDestination
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

@Composable
fun TrainerProfileCompact(
    viewModel: TrainerProfileViewModel
) {
    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val localNavigator = LocalNavigator.currentOrThrow
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            TrainerProfileUiEffect.NavigateBack -> {
                localNavigator.pop()
            }

            TrainerProfileUiEffect.NavigateToCreatePost -> {
                appNavigator.push(SharedScreen.NewPost)
            }

            TrainerProfileUiEffect.NavigateToSettings -> {
                appNavigator.push(SharedScreen.Settings)
            }

            TrainerProfileUiEffect.NavigateToAppointments -> {
                appNavigator.push(SharedScreen.TrainerAppointmentListing)
            }

            is TrainerProfileUiEffect.NavigateToLessonDetail -> {
                appNavigator.push(TrainerAppointmentDetail(effect.lessonId))
            }

            is TrainerProfileUiEffect.NavigateToChatWithTrainer -> {
                appNavigator.push(SharedScreen.ChatWithTrainer(effect.trainerId))
            }

            is TrainerProfileUiEffect.NavigateToTrainerSchedule -> {
                appNavigator.push(SharedScreen.TrainerSchedule(effect.trainerId))
            }
        }
    }

    when (uiState) {
        is TrainerProfileUiState.Loading -> {
            FullScreenLoaderContent()
        }

        is TrainerProfileUiState.Error -> {
            val message = (uiState as TrainerProfileUiState.Error).message
            ErrorScreen(message = message, onConfirm = { viewModel.refreshData() })
        }

        is TrainerProfileUiState.Content -> {
            val content = uiState as TrainerProfileUiState.Content
            TrainerProfileCompactComponent.Content(content, viewModel::onAction)
        }
    }
}

private object TrainerProfileCompactComponent {

    @Composable
    fun Header(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit
    ) {
        val profile = content.profile

        ProfileCompactComponent.Header(
            backgroundImageUrl = profile.backgroundImageUrl,
            backgroundImageModifier = Modifier.fillMaxWidth().height(180.dp),
            profileImageUrl = profile.profileImageUrl,
            cardContents = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SkyText(
                        text = profile.firstName,
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
                        title = "${profile.followerCount}",
                        subtitle = stringResource(Res.string.follower_label)
                    )
                    VerticalDivider(Modifier.height(48.dp))
                    VerticalProfileStatisticItem(
                        title = "${profile.lessonCount}",
                        subtitle = stringResource(Res.string.private_lessons_label)
                    )
                    VerticalDivider(Modifier.height(48.dp))
                    VerticalProfileStatisticItem(
                        title = "${profile.postCount}",
                        subtitle = stringResource(Res.string.posts_label)
                    )
                }

                Spacer(Modifier.height(16.dp))

                SkyText(
                    text = profile.bio,
                    styleType = TextStyleType.BodySmall,
                    modifier = Modifier.fillMaxWidth()
                )

                profile.gymName.takeUnless { it.isNullOrEmpty() }?.let { gymName ->
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
                            text = gymName,
                            styleType = TextStyleType.BodySmallSemibold,
                            modifier = Modifier.weight(1f).padding(horizontal = 4.dp)
                        )
                    }
                }

                if (content.isVisiting) {
                    Spacer(Modifier.height(16.dp))

                    SkyButton(
                        label = stringResource(Res.string.appointment_book_action),
                        leftIcon = painterResource(Res.drawable.ic_calendar_dots),
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onAction(TrainerProfileUiAction.OnClickBookAppointment) }
                    )
                    FeatureVisible(false) {
                        SecondaryIconButton(
                            res = Res.drawable.ic_send,
                            onClick = { onAction(TrainerProfileUiAction.OnClickSendMessage) }
                        )
                    }
                }
            },
            cardContentsModifier = Modifier.padding(top = 140.dp),
            canNavigateBack = content.isVisiting,
            onClickBack = { onAction(TrainerProfileUiAction.OnClickBack) }
        )
    }

    @Composable
    fun NavigationGroup(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit
    ) {
        if (content.isVisiting) {
            ProfileCompactComponent.NavigationMenuWithAction(
                onDestinationChanged = { onAction(TrainerProfileUiAction.OnDestinationChanged(it)) },
                destination = content.destination,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            ProfileCompactComponent.NavigationMenuWithAction(
                onDestinationChanged = { onAction(TrainerProfileUiAction.OnDestinationChanged(it)) },
                destination = content.destination,
                action = {
                    if (content.destination == ProfileDestination.Posts) {
                        ProfileCompactComponent.NavigationMenuAction(
                            res = Res.drawable.ic_plus,
                            onClick = { onAction(TrainerProfileUiAction.OnClickNewPost) }
                        )
                    } else {
                        ProfileCompactComponent.NavigationMenuAction(
                            res = Res.drawable.ic_settings,
                            onClick = { onAction(TrainerProfileUiAction.OnClickSettings) }
                        )
                    }

                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    @Composable
    fun Content(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit
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

                    NavigationGroup(content, onAction)

                    if (content.destination == ProfileDestination.Posts) {
                        PostsContent(content, onAction)
                    } else {
                        AboutGroup(content, onAction)
                    }

                    Spacer(Modifier.height(124.dp))
                }

            }
        )
    }

    @Composable
    fun ColumnScope.AboutGroup(
        content: TrainerProfileUiState.Content,
        onAction: (TrainerProfileUiAction) -> Unit
    ) {
        val weekDaySelectorController = rememberCalendarWeekDaySelectorController()
        val calendarUiState = rememberWeekDaySelectorState(weekDaySelectorController)

        if (content.isVisiting) {
            LaunchedEffect(calendarUiState.selectedDate) {
                onAction(TrainerProfileUiAction.ChangeDate(calendarUiState.selectedDate))
            }
        }

        FeatureVisible(false) {
            if (content.specialties == null) {
                NoSpecialityCard {}
            } else {
                LifestyleActionRow(viewData = content.specialties)
            }
        }

        if (content.isVisiting) {
            ProfileCompactComponent.WeeklyLessonScheduleGroup(
                calendarUiState = calendarUiState,
                calendarViewModel = weekDaySelectorController,
                lessons = content.lessons,
                goToVisitCalendar = { onAction(TrainerProfileUiAction.OnClickShowSchedule) },
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            if (content.lessons.isEmpty()) {
                ProfileCompactComponent.NoScheduledLessonsCard()
            } else {
                ProfileCompactComponent.LessonSchedule(
                    lessons = content.lessons,
                    onClickShowAll = { onAction(TrainerProfileUiAction.OnClickShowAllLessons) },
                    onClickLesson = { onAction(TrainerProfileUiAction.OnClickToUpcomingLesson(it.lessonId)) }
                )
            }
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
            FeatureVisible(!content.isVisiting) {
                SocialQuickPostInputCard(
                    creatorImageUrl = content.profile.backgroundImageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SkyFitColor.background.fillTransparentSecondary, RoundedCornerShape(16.dp))
                ) { onAction(TrainerProfileUiAction.OnSendQuickPost(it)) }
            }

            content.posts.forEach { post ->
                SocialPostCard(
                    post = post,
                    onClick = { onAction(TrainerProfileUiAction.OnClickPost) },
                    onClickComment = { onAction(TrainerProfileUiAction.OnClickCommentPost) },
                    onClickLike = { onAction(TrainerProfileUiAction.OnClickLikePost) },
                    onClickShare = { onAction(TrainerProfileUiAction.OnClickSharePost) },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(SkyFitColor.background.fillTransparentSecondary)
                        .fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun NoSpecialityCard(onClickAdd: () -> Unit) {
        Box(
            Modifier
                .fillMaxWidth()
                .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(24.dp))
                .padding(vertical = 56.dp),
            contentAlignment = Alignment.Center
        ) {
            SkyFitButtonComponent(
                modifier = Modifier.wrapContentWidth(), text = "Profili Düzenle",
                onClick = onClickAdd,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Micro,
                rightIconPainter = painterResource(Res.drawable.ic_app_logo)
            )
        }
    }
}


