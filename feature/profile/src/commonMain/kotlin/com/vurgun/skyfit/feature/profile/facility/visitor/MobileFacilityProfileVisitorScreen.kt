package com.vurgun.skyfit.feature.profile.facility.visitor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vurgun.skyfit.feature.calendar.components.component.calendar.weekly.CalendarWeekDaySelectorViewModel
import com.vurgun.skyfit.feature.calendar.components.component.calendar.weekly.rememberWeekDaySelectorState
import com.vurgun.skyfit.feature.profile.components.MobileProfileBackgroundImage
import com.vurgun.skyfit.feature.profile.facility.owner.FacilityProfileComponent
import com.vurgun.skyfit.ui.core.components.loader.FullScreenLoader
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.screen.ErrorScreen
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MobileFacilityProfileVisitorScreen(
    facilityId: Int,
    goToBack: () -> Unit,
    goToVisitCalendar: () -> Unit,
    goToVisitTrainerProfile: (trainerId: Int) -> Unit,
    goToChat: () -> Unit,
    viewModel: FacilityProfileVisitorViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is FacilityProfileVisitorEffect.NavigateToTrainer -> goToVisitTrainerProfile(effect.trainerId)
                FacilityProfileVisitorEffect.NavigateToChat -> goToChat()
                FacilityProfileVisitorEffect.NavigateToCalendar -> goToVisitCalendar()
                FacilityProfileVisitorEffect.NavigateBack -> goToBack()
            }
        }
    }

    LaunchedEffect(facilityId) {
        viewModel.loadProfile(facilityId)
    }

    when (uiState) {
        is FacilityProfileVisitorUiState.Loading -> FullScreenLoader()

        is FacilityProfileVisitorUiState.Error -> {
            val message = (uiState as FacilityProfileVisitorUiState.Error).message
            ErrorScreen(message, onBack = goToBack)
        }

        is FacilityProfileVisitorUiState.Content -> {
            val state = uiState as FacilityProfileVisitorUiState.Content
            FacilityProfileVisitorContent(state, viewModel::onAction)
        }
    }
}

@Composable
private fun FacilityProfileVisitorContent(
    uiState: FacilityProfileVisitorUiState.Content,
    onAction: (FacilityProfileVisitorAction) -> Unit
) {
    val calendarViewModel: CalendarWeekDaySelectorViewModel = viewModel()
    val calendarUiState = rememberWeekDaySelectorState(calendarViewModel)

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
                imageUrl = uiState.profile.backgroundImageUrl,
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

                FacilityProfileComponent.MobileFacilityProfileVisitor_HeaderCard(
                    profile = uiState.profile,
                    isFollowedByVisitor = uiState.isFollowedByVisitor,
                    onClickFollow = { onAction(FacilityProfileVisitorAction.Follow) },
                    onClickUnFollow = { onAction(FacilityProfileVisitorAction.Unfollow) },
                    onClickBookingCalendar = { onAction(FacilityProfileVisitorAction.NavigateToCalendar) },
                    onClickMessage = { onAction(FacilityProfileVisitorAction.NavigateToChat) }
                )

                FacilityProfileComponent.MobileFacilityProfileVisitor_Lessons(
                    calendarUiState = calendarUiState,
                    calendarViewModel = calendarViewModel,
                    lessons = uiState.lessons,
                    goToVisitCalendar = { onAction(FacilityProfileVisitorAction.NavigateToCalendar) },
                    modifier = Modifier
                )

                Spacer(Modifier.height(124.dp))
            }

            FacilityProfileComponent.MobileFacilityProfileVisitor_TopBar(onClickBack = {
                onAction(FacilityProfileVisitorAction.Exit)
            })
        }
    }
}