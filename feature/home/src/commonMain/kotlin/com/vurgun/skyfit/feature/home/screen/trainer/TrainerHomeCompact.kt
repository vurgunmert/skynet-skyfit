package com.vurgun.skyfit.feature.home.screen.trainer

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.SharedScreen.FacilityProfileVisitor
import com.vurgun.skyfit.core.navigation.findParentByKey
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.event.TrainerHomeLessonEventItem
import com.vurgun.skyfit.core.ui.components.icon.SkyIcon
import com.vurgun.skyfit.core.ui.components.icon.SkyIconSize
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.CharacterImage
import com.vurgun.skyfit.core.ui.components.topbar.CompactTopBar
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.home.component.HomeNoUpcomingAppointmentCard
import com.vurgun.skyfit.feature.home.component.LessonFilterData
import com.vurgun.skyfit.feature.home.model.TrainerHomeAction
import com.vurgun.skyfit.feature.home.model.TrainerHomeEffect.*
import com.vurgun.skyfit.feature.home.model.TrainerHomeUiState
import com.vurgun.skyfit.feature.home.model.TrainerHomeViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.ic_bell
import skyfit.core.ui.generated.resources.ic_chat
import skyfit.core.ui.generated.resources.refresh_action

@Composable
internal fun TrainerHomeCompact(viewModel: TrainerHomeViewModel) {

    val dashboardNavigator = LocalNavigator.currentOrThrow.findParentByKey("dashboard")
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is NavigateToVisitFacility ->
                dashboardNavigator?.push(FacilityProfileVisitor(effect.facilityId))

            NavigateToConversations ->
                dashboardNavigator?.push(SharedScreen.Conversations)

            NavigateToAppointments ->
                dashboardNavigator?.push(SharedScreen.Appointments)

            NavigateToNotifications ->
                dashboardNavigator?.push(SharedScreen.Notifications)

            NavigateToChatBot ->
                dashboardNavigator?.push(SharedScreen.ChatBot)

            is ShowLessonFilter ->
                dashboardNavigator?.push(SharedScreen.LessonFilter(effect.lessons, onApply = {
                    viewModel.onAction(
                        TrainerHomeAction.ApplyLessonFilter(it as LessonFilterData)
                    )
                }))
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
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TrainerHomeCompactComponent.TopBar(viewModel::onAction)
                },
                content = {
                    TrainerHomeCompactComponent.Content(content, viewModel::onAction)
                }
            )
        }
    }
}

private object TrainerHomeCompactComponent {

    @Composable
    fun TopBar(
        onAction: (TrainerHomeAction) -> Unit
    ) {
        CompactTopBar.TopBarWithAccountAndNavigation(
            actions = {
                SkyIcon(
                    res = Res.drawable.ic_bell,
                    size = SkyIconSize.Normal,
                    onClick = { onAction(TrainerHomeAction.OnClickNotifications) }
                )
                SkyIcon(
                    res = Res.drawable.ic_chat,
                    size = SkyIconSize.Normal,
                    onClick = { onAction(TrainerHomeAction.OnClickConversations) }
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

    @Composable
    fun Content(
        content: TrainerHomeUiState.Content,
        onAction: (TrainerHomeAction) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            CharacterImage(
                characterType = content.account.characterType,
                modifier = Modifier
            )

            Spacer(Modifier.height(24.dp))

            TrainerUpcomingLessons(
                content.profile.gymId,
                lessons = content.upcomingLessons,
                onClickShowAll = { onAction(TrainerHomeAction.OnClickAppointments) },
                onClickAddLesson = {}
            )

            Spacer(Modifier.height(128.dp))
        }
    }


    @Composable
    fun TrainerUpcomingLessons(
        assignedFacilityId: Int?,
        lessons: List<LessonSessionItemViewData>,
        onClickShowAll: () -> Unit = {},
        onClickAddLesson: (facilityId: Int) -> Unit = {},
    ) {

        if (lessons.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {

                lessons.forEach { appointment ->
                    Spacer(modifier = Modifier.height(8.dp))
                    TrainerHomeLessonEventItem(
                        title = appointment.title,
                        iconId = appointment.iconId,
                        date = appointment.date.toString(),
                        timePeriod = appointment.hours.toString(),
                        facility = appointment.facility.toString(),
                        onClick = onClickShowAll
                    )
                }
            }
        } else {
            HomeNoUpcomingAppointmentCard(
                assignedFacilityId = assignedFacilityId,
                onClickAdd = onClickAddLesson
            )
        }
    }


}