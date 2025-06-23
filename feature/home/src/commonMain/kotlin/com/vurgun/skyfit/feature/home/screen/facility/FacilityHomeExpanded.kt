package com.vurgun.skyfit.feature.home.screen.facility

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.ui.components.layout.ExpandedLayout
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.feature.home.component.HomeCompactComponent
import com.vurgun.skyfit.feature.home.component.HomeLessonTableComponents
import com.vurgun.skyfit.feature.home.component.HomeNoUpcomingAppointmentCard
import com.vurgun.skyfit.feature.home.component.HomeStatisticComponents
import com.vurgun.skyfit.feature.home.model.FacilityHomeAction
import com.vurgun.skyfit.feature.home.model.FacilityHomeEffect
import com.vurgun.skyfit.feature.home.model.FacilityHomeUiState
import com.vurgun.skyfit.feature.home.model.FacilityHomeViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.refresh_action

@Composable
fun FacilityHomeExpanded(viewModel: FacilityHomeViewModel) {

    val dashboardNavigator = LocalNavigator.currentOrThrow
    val overlayController = LocalCompactOverlayController.current
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {

            is FacilityHomeEffect.ShowOverlay ->
                overlayController?.invoke(effect.screen)

            FacilityHomeEffect.NavigateToManageLessons ->
                overlayController?.invoke(SharedScreen.FacilityManageLessons)

            FacilityHomeEffect.DismissOverlay ->
                overlayController?.invoke(null)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.loadData()
    }

    when (uiState) {
        FacilityHomeUiState.Loading -> {
            FullScreenLoaderContent()
        }

        is FacilityHomeUiState.Error -> {
            val message = (uiState as FacilityHomeUiState.Error).message
            ErrorScreen(
                message = message,
                confirmText = stringResource(Res.string.refresh_action),
                onConfirm = { viewModel.loadData() }
            )
        }

        is FacilityHomeUiState.Content -> {
            val content = uiState as FacilityHomeUiState.Content
            FacilityHomeExpandedComponent.Content(content, viewModel::onAction)
        }
    }
}

private object FacilityHomeExpandedComponent {

    @Composable
    fun Content(
        content: FacilityHomeUiState.Content,
        onAction: (FacilityHomeAction) -> Unit
    ) {

        ExpandedLayout.LeftLargeMultiLaneScaffold(
            modifier = Modifier.fillMaxSize(),
            leftContent = {
                HomeStatisticComponents.StatCardComponent(content.statistics)
                HomeLessonTableComponents.LessonScheduleGroup(
                    activeFilterData = content.appliedFilter,
                    sessions = content.filteredLessons,
                    onShowFilter = {
                        onAction(FacilityHomeAction.OnClickFilter)
                    },
                    onApplyFilter = {
                        onAction(FacilityHomeAction.ApplyLessonFilter(it))
                    })
            },
            rightContent = {
                if (content.upcomingLessons.isEmpty()) {
                    HomeNoUpcomingAppointmentCard(
                        assignedFacilityId = content.facility.gymId,
                        onClickAdd = { onAction(FacilityHomeAction.OnClickLessons) }
                    )
                } else {
                    HomeCompactComponent.LessonCards(
                        lessons = content.upcomingLessons,
                        onClickShowAll = { onAction(FacilityHomeAction.OnClickLessons) },
                        onClickLesson = { onAction(FacilityHomeAction.OnClickLessons) }
                    )
                }
            }
        )
    }

}