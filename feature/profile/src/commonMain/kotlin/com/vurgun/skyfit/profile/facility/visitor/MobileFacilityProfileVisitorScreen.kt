package com.vurgun.skyfit.profile.facility.visitor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.profile.MobileProfileBackgroundImage
import com.vurgun.skyfit.core.ui.components.profile.VerticalTrainerProfileCard
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberCalendarWeekDaySelectorController
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberWeekDaySelectorState
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.text.BodyLargeSemiboldText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.profile.facility.owner.FacilityProfileCompactComponent
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.our_coaches_label

class FacilityProfileVisitorScreen(private val facilityId: Int) : Screen {

    @Composable
    override fun Content() {
        val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
        val viewModel = koinScreenModel<FacilityProfileVisitorViewModel>()

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                FacilityProfileVisitorEffect.NavigateToBack -> {
                    appNavigator.pop()
                }

                is FacilityProfileVisitorEffect.NavigateToTrainer -> {
                    appNavigator.push(SharedScreen.TrainerProfileVisitor(effect.trainerId))
                }

                is FacilityProfileVisitorEffect.NavigateToSchedule -> {
                    appNavigator.push(SharedScreen.FacilitySchedule(effect.facilityId))
                }

                is FacilityProfileVisitorEffect.NavigateToChat -> {
                    appNavigator.push(SharedScreen.UserChat(effect.visitorId))
                }
            }
        }

        LaunchedEffect(facilityId) {
            viewModel.loadProfile(facilityId)
        }

        MobileFacilityProfileVisitorScreen(
            viewModel = viewModel
        )
    }
}


@Composable
private fun MobileFacilityProfileVisitorScreen(
    viewModel: FacilityProfileVisitorViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is FacilityProfileVisitorUiState.Loading -> FullScreenLoaderContent()

        is FacilityProfileVisitorUiState.Error -> {
            val message = (uiState as FacilityProfileVisitorUiState.Error).message
            ErrorScreen(message = message, onConfirm = { viewModel.onAction(FacilityProfileVisitorAction.NavigateToBack) })
        }

        is FacilityProfileVisitorUiState.Content -> {
            val state = uiState as FacilityProfileVisitorUiState.Content
            FacilityProfileVisitorContent(state, viewModel::onAction)
        }
    }
}

@Composable
private fun FacilityProfileVisitorContent(
    content: FacilityProfileVisitorUiState.Content,
    onAction: (FacilityProfileVisitorAction) -> Unit
) {
    val weekDaySelectorController = rememberCalendarWeekDaySelectorController()
    val calendarUiState = rememberWeekDaySelectorState(weekDaySelectorController)

    LaunchedEffect(calendarUiState.selectedDate) {
        onAction(FacilityProfileVisitorAction.ChangeDate(calendarUiState.selectedDate))
    }

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

                FacilityProfileCompactComponent.MobileFacilityProfileVisitor_HeaderCard(
                    profile = content.profile,
                    isFollowedByVisitor = content.isFollowedByVisitor,
                    onClickFollow = { onAction(FacilityProfileVisitorAction.Follow) },
                    onClickUnFollow = { onAction(FacilityProfileVisitorAction.Unfollow) },
                    onClickBookingCalendar = { onAction(FacilityProfileVisitorAction.NavigateToCalendar) },
                    onClickMessage = { onAction(FacilityProfileVisitorAction.NavigateToChat) }
                )

                FacilityProfileCompactComponent.MobileFacilityProfileVisitor_Lessons(
                    calendarUiState = calendarUiState,
                    calendarViewModel = weekDaySelectorController,
                    lessons = content.lessons,
                    goToVisitCalendar = { onAction(FacilityProfileVisitorAction.NavigateToCalendar) },
                    modifier = Modifier.fillMaxWidth()
                )

                if (content.trainers.isNotEmpty()) {
                    FacilityProfileVisitor_Trainers(
                        trainers = content.trainers,
                        onClick = { trainerId ->
                            onAction(FacilityProfileVisitorAction.NavigateToTrainer(trainerId))
                        }
                    )
                }

                Spacer(Modifier.height(124.dp))
            }

            FacilityProfileCompactComponent.MobileFacilityProfileVisitor_TopBar(onClickBack = {
                onAction(FacilityProfileVisitorAction.NavigateToBack)
            })
        }
    }
}

@Composable
private fun FacilityProfileVisitor_Trainers(
    trainers: List<FacilityTrainerProfile>,
    onClick: (trainerId: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        BodyLargeSemiboldText(text = stringResource(Res.string.our_coaches_label))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(trainers) { trainer ->
                VerticalTrainerProfileCard(
                    imageUrl = trainer.profileImageUrl,
                    name = trainer.fullName,
                    followerCount = trainer.followerCount,
                    lessonCount = trainer.lessonTypeCount,
                    videoCount = trainer.videoCount,
                    rating = trainer.point,
                    onClick = { onClick(trainer.trainerId) }
                )
            }
        }
    }
}