package com.vurgun.skyfit.profile.trainer.visitor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.button.SkyFitPrimaryCircularBackButton
import com.vurgun.skyfit.core.ui.components.divider.VerticalDivider
import com.vurgun.skyfit.core.ui.components.event.AvailableActivityCalendarEventItem
import com.vurgun.skyfit.core.ui.components.image.NetworkImage
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.ButtonSize
import com.vurgun.skyfit.core.ui.components.special.ButtonVariant
import com.vurgun.skyfit.core.ui.components.special.SkyFitButtonComponent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitAsset
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.feature.persona.components.*
import com.vurgun.skyfit.feature.persona.components.viewdata.LifestyleActionItemViewData
import com.vurgun.skyfit.feature.persona.components.viewdata.LifestyleActionRowViewData
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class TrainerProfileVisitorScreen(private val trainerId: Int) : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<TrainerProfileVisitorViewModel>()

        MobileTrainerProfileVisitorScreen(
            trainerId = trainerId,
            goToBack = { navigator.pop() },
            goToChat = { navigator.push(SharedScreen.UserChat(trainerId)) },
            goToSchedule = { navigator.push(SharedScreen.TrainerSchedule(trainerId)) },
            viewModel = viewModel
        )
    }
}

@Composable
private fun MobileTrainerProfileVisitorScreen(
    trainerId: Int,
    goToBack: () -> Unit,
    goToChat: () -> Unit,
    goToSchedule: () -> Unit,
    viewModel: TrainerProfileVisitorViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                TrainerProfileVisitorEffect.NavigateBack -> goToBack()
                TrainerProfileVisitorEffect.NavigateToSchedule -> goToSchedule()
                TrainerProfileVisitorEffect.NavigateToChat -> goToChat()
            }
        }
    }

    LaunchedEffect(trainerId) {
        viewModel.loadProfile(trainerId)
    }

    when (uiState) {
        is TrainerProfileVisitorUiState.Loading -> FullScreenLoaderContent()

        is TrainerProfileVisitorUiState.Error -> {
            val message = (uiState as TrainerProfileVisitorUiState.Error).message
            ErrorScreen(message = message, onConfirm = goToBack)
        }

        is TrainerProfileVisitorUiState.Content -> {
            val state = uiState as TrainerProfileVisitorUiState.Content
            TrainerProfileVisitorContent(state, viewModel::onAction)
        }
    }
}

@Composable
private fun TrainerProfileVisitorContent(
    content: TrainerProfileVisitorUiState.Content,
    onAction: (TrainerProfileVisitorAction) -> Unit,
) {
    val scrollState = rememberScrollState()

    var backgroundAlpha by remember { mutableStateOf(1f) }
    val transitionThreshold = 300f

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

                TrainerProfileVisitorComponent.MobileTrainerVisitor_HeaderCard(
                    profile = content.profile,
                    isFollowedByVisitor = content.isFollowedByVisitor,
                    onFollow = { onAction(TrainerProfileVisitorAction.Follow) },
                    onUnfollow = { onAction(TrainerProfileVisitorAction.Unfollow) },
                    onSchedule = { onAction(TrainerProfileVisitorAction.NavigateToSchedule) }
                )

                TrainerProfileVisitorComponent.MobileTrainerProfileVisitor_Lessons(
                    lessons = content.lessons,
                    goToVisitCalendar = { onAction(TrainerProfileVisitorAction.NavigateToSchedule) },
                    modifier = Modifier
                )
            }

            TrainerProfileVisitorComponent.MobileTrainerProfileVisitor_TopBar(onClickBack = {
                onAction(TrainerProfileVisitorAction.Exit)
            })
        }
    }
}


private object TrainerProfileVisitorComponent {

    @Composable
    fun MobileTrainerProfileVisitor_TopBar(onClickBack: () -> Unit) {
        Box(Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 8.dp)) {
            SkyFitPrimaryCircularBackButton(onClick = onClickBack)
        }
    }

    @Composable
    fun MobileTrainerVisitor_HeaderCard(
        profile: TrainerProfile,
        isFollowedByVisitor: Boolean,
        onFollow: () -> Unit,
        onUnfollow: () -> Unit,
        onSchedule: () -> Unit
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(top = 70.dp)
                    .width(398.dp)
                    .background(SkyFitColor.background.fillTransparent, RoundedCornerShape(16.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                        .padding(start = 16.dp, top = 36.dp, end = 16.dp, bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = profile.firstName,
                            style = SkyFitTypography.bodyLargeSemibold
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Space between name and social link
                        Text(
                            text = profile.username,
                            style = SkyFitTypography.bodySmallMedium,
                            color = SkyFitColor.text.secondary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
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

                    Spacer(modifier = Modifier.height(16.dp))

                    SkyFitButtonComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(if (isFollowedByVisitor) Res.string.unfollow_action else Res.string.follow_action),
                        onClick = if (isFollowedByVisitor) onUnfollow else onFollow,
                        variant = ButtonVariant.Primary,
                        size = ButtonSize.Large
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        SkyFitButtonComponent(
                            modifier = Modifier.weight(1f),
                            text = stringResource(Res.string.appointment_book_action),
                            onClick = onSchedule,
                            variant = ButtonVariant.Secondary,
                            size = ButtonSize.Large,
                            leftIconPainter = painterResource(Res.drawable.ic_calendar_dots)
                        )
                    }
                }
            }

            NetworkImage(
                imageUrl = profile.profileImageUrl,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .align(Alignment.TopCenter)
            )
        }
    }

    @Composable
    fun MobileTrainerProfileVisitor_ActionRow(
        postsSelected: Boolean,
        showMessage: Boolean,
        onClickAbout: () -> Unit,
        onClickPosts: () -> Unit,
        onClickMessage: () -> Unit
    ) {
        Row(
            Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            MobileVisitedProfileActionsComponent(
                modifier = Modifier.weight(1f),
                onClickAbout = onClickAbout,
                onClickPosts = onClickPosts
            )
//            MobileProfileActionTabsRow(
//                modifier = Modifier.weight(1f),
//                onClickAbout = onClickAbout,
//                onClickPosts = onClickPosts,
//                postsSelected = postsSelected
//            )

            if (showMessage) {
                Box(
                    Modifier
                        .background(SkyFitColor.background.surfaceSecondary, RoundedCornerShape(16.dp))
                        .clickable(onClick = onClickMessage)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_send),
                        contentDescription = "Send",
                        tint = SkyFitColor.icon.default,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun MobileTrainerProfileVisitor_Specialities(specialities: List<LifestyleActionItemViewData>) {
        if (specialities.isEmpty()) return

        LifestyleActionRow(
            viewData = LifestyleActionRowViewData(
                iconId = SkyFitAsset.SkyFitIcon.MEDAL.id,
                title = stringResource(Res.string.specialities_label),
                items = specialities,
                iconSizePx = 24
            )
        )
    }

    @Composable
    fun MobileTrainerProfileVisitor_Lessons(
        lessons: List<LessonSessionItemViewData>,
        goToVisitCalendar: () -> Unit,
        modifier: Modifier = Modifier,
    ) {
        if (lessons.isEmpty()) return

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

            if (lessons.isNotEmpty()) {
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
}

