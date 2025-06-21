package com.vurgun.skyfit.feature.home.screen.trainer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import com.vurgun.skyfit.core.navigation.SharedScreen.*
import com.vurgun.skyfit.core.navigation.findRootNavigator
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.CharacterImage
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.feature.home.component.HomeCompactComponent
import com.vurgun.skyfit.feature.home.component.HomeStatisticComponents.StatCardComponent
import com.vurgun.skyfit.feature.home.component.LessonFilterData
import com.vurgun.skyfit.feature.home.model.TrainerHomeAction
import com.vurgun.skyfit.feature.home.model.TrainerHomeAction.ApplyLessonFilter
import com.vurgun.skyfit.feature.home.model.TrainerHomeEffect.*
import com.vurgun.skyfit.feature.home.model.TrainerHomeUiState
import com.vurgun.skyfit.feature.home.model.TrainerHomeViewModel
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.refresh_action

@Composable
internal fun TrainerHomeCompact(viewModel: TrainerHomeViewModel) {

    val appNavigator = LocalNavigator.currentOrThrow.findRootNavigator()
    val uiState by viewModel.uiState.collectAsState()

    CollectEffect(viewModel.effect) { effect ->
        when (effect) {
            is NavigateToVisitFacility -> {
                appNavigator.push(FacilityProfile(effect.facilityId))
            }

            NavigateToConversations ->
                appNavigator.push(Conversations)

            NavigateToAppointments ->
                appNavigator.push(TrainerAppointmentListing)

            NavigateToNotifications ->
                appNavigator.push(Notifications)

            NavigateToChatBot ->
                appNavigator.push(ChatBot)

            is ShowLessonFilter ->
                appNavigator.push(LessonFilter(effect.lessons, onApply = {
                    viewModel.onAction(
                        ApplyLessonFilter(it as LessonFilterData)
                    )
                }))

            is NavigateToAppointment -> appNavigator.push(TrainerAppointmentDetail(effect.lessonId))
            is ShowOverlay -> Unit
            DismissOverlay -> Unit
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
                    TrainerHomeCompactComponent.TopBar(content, viewModel::onAction)
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
        content: TrainerHomeUiState.Content,
        onAction: (TrainerHomeAction) -> Unit
    ) {

        HomeCompactComponent.BasicTopBar(
            notificationsEnabled = content.notificationsEnabled,
            conversationsEnabled = content.conversationsEnabled,
            onClickNotifications = { onAction(TrainerHomeAction.OnClickNotifications) },
            onClickConversations = { onAction(TrainerHomeAction.OnClickConversations) }
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

            HomeCompactComponent.LessonCards(
                lessons = content.upcomingLessons,
                onClickShowAll = { onAction(TrainerHomeAction.OnClickAppointments) },
                onClickLesson = { onAction(TrainerHomeAction.OnClickAppointment(it.lessonId)) }
            )

            StatCardComponent(content.statistics)

            Spacer(Modifier.height(128.dp))
        }
    }
}