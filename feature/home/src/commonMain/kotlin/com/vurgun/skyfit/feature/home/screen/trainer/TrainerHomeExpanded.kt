package com.vurgun.skyfit.feature.home.screen.trainer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.SharedScreen.FacilityProfile
import com.vurgun.skyfit.core.navigation.SharedScreen.LessonFilter
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.layout.ExpandedLayout
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.feature.home.component.HomeCompactComponent
import com.vurgun.skyfit.feature.home.component.HomeLessonTableComponents
import com.vurgun.skyfit.feature.home.component.HomeStatisticComponents
import com.vurgun.skyfit.feature.home.component.LessonFilterData
import com.vurgun.skyfit.feature.home.model.TrainerHomeAction
import com.vurgun.skyfit.feature.home.model.TrainerHomeAction.ApplyLessonFilter
import com.vurgun.skyfit.feature.home.model.TrainerHomeEffect.*
import com.vurgun.skyfit.feature.home.model.TrainerHomeUiState
import com.vurgun.skyfit.feature.home.model.TrainerHomeViewModel
import org.jetbrains.compose.resources.stringResource
import fiwe.core.ui.generated.resources.Res
import fiwe.core.ui.generated.resources.refresh_action

@Composable
internal fun TrainerHomeExpanded(viewModel: TrainerHomeViewModel) {

    val dashboardNavigator = LocalNavigator.currentOrThrow
    val overlayController = LocalCompactOverlayController.current
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is NavigateToVisitFacility ->
                dashboardNavigator.push(FacilityProfile(effect.facilityId))

            NavigateToConversations ->
                overlayController?.invoke(SharedScreen.Conversations)

            NavigateToAppointments ->
                overlayController?.invoke(SharedScreen.TrainerAppointmentListing)

            NavigateToNotifications ->
                overlayController?.invoke(SharedScreen.Notifications)

            NavigateToChatBot ->
                overlayController?.invoke(SharedScreen.ChatBot)

            is ShowLessonFilter -> {
                overlayController?.invoke(LessonFilter(effect.lessons) {
                    viewModel.onAction(ApplyLessonFilter(it as LessonFilterData))
                })
            }

            is ShowOverlay -> {
                overlayController?.invoke(effect.screen)
            }

            is NavigateToAppointment ->
                overlayController?.invoke(SharedScreen.TrainerAppointmentDetail(effect.lessonId))

            DismissOverlay ->
                overlayController?.invoke(null)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.loadData()
    }

    when (uiState) {
        TrainerHomeUiState.Loading -> FullScreenLoaderContent()
        is TrainerHomeUiState.Error -> {
            val message = (uiState as TrainerHomeUiState.Error).message
            ErrorScreen(
                message = message,
                confirmText = stringResource(Res.string.refresh_action),
                onConfirm = { viewModel.loadData() }
            )
        }

        is TrainerHomeUiState.Content -> {
            val content = uiState as TrainerHomeUiState.Content
            TrainerHomeExpandedComponent.Content(content, viewModel::onAction)
        }
    }
}

private object TrainerHomeExpandedComponent {

    @Composable
    fun Content(
        content: TrainerHomeUiState.Content,
        onAction: (TrainerHomeAction) -> Unit
    ) {
        ExpandedLayout.LeftLargeMultiLaneScaffold(
            leftContent = {
                HomeStatisticComponents.StatCardComponent(content.statistics)

                HomeLessonTableComponents.LessonScheduleGroup(
                    sessions = content.filteredLessons,
                    activeFilterData = content.appliedFilter,
                    onShowFilter = {
                        onAction(TrainerHomeAction.OnClickFilter)
                    },
                    onApplyFilter = {
                        onAction(TrainerHomeAction.ApplyLessonFilter(it))
                    })
            },
            rightContent = {
                HomeCompactComponent.LessonCards(
                    content.upcomingLessons,
                    onClickShowAll = {
                        onAction(TrainerHomeAction.OnClickAppointments)
                    },
                    onClickLesson = {
                        onAction(TrainerHomeAction.OnClickAppointment(it.lessonId))
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}