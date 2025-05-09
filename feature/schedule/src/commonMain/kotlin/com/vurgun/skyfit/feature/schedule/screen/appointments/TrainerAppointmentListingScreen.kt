package com.vurgun.skyfit.feature.schedule.screen.appointments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.utility.formatToSlashedDate
import com.vurgun.skyfit.core.navigation.SharedScreen
import com.vurgun.skyfit.core.navigation.push
import com.vurgun.skyfit.core.ui.components.chip.SecondaryPillChip
import com.vurgun.skyfit.core.ui.components.event.BasicAppointmentEventItem
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.core.ui.components.text.BodyMediumMediumText
import com.vurgun.skyfit.core.ui.screen.ErrorScreen
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.Res
import skyfit.core.ui.generated.resources.appointments_title
import skyfit.core.ui.generated.resources.ic_filter
import skyfit.core.ui.generated.resources.lesson_status_active
import skyfit.core.ui.generated.resources.lesson_status_cancelled
import skyfit.core.ui.generated.resources.lesson_status_completed

class TrainerAppointmentListingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<TrainerAppointmentListingViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val activeTab = (uiState as? TrainerAppointmentListingUiState.Content)?.activeTab
            ?: TrainerAppointmentListingTab.Completed

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                TrainerAppointmentListingEffect.NavigateToBack ->
                    navigator.pop()

                is TrainerAppointmentListingEffect.NavigateToDetail ->
                    navigator.push(SharedScreen.TrainerAppointmentDetail(effect.lessonId))

                TrainerAppointmentListingEffect.ShowFilter ->
                    navigator.push(TrainerAppointmentListingFilterScreen(viewModel))
            }
        }

        SkyFitMobileScaffold(
            topBar = {
                Column {
                    SkyFitScreenHeader(
                        title = stringResource(Res.string.appointments_title),
                        onClickBack = { viewModel.onAction(TrainerAppointmentListingAction.NavigateToBack) })

                    TrainerAppointmentListingTabRow(
                        selectedTab = activeTab,
                        onTabSelected = { tab ->
                            viewModel.onAction(TrainerAppointmentListingAction.ChangeTab(tab))
                        },
                        onClickFilter = {
                            viewModel.onAction(TrainerAppointmentListingAction.ShowFilter)
                        }
                    )
                }
            }
        ) {

            when (uiState) {
                TrainerAppointmentListingUiState.Loading -> FullScreenLoaderContent()
                is TrainerAppointmentListingUiState.Error -> {
                    val message = (uiState as TrainerAppointmentListingUiState.Error)
                    ErrorScreen(message = message.toString(), onConfirm = { navigator.pop() })
                }

                is TrainerAppointmentListingUiState.Content -> {
                    val content = (uiState as TrainerAppointmentListingUiState.Content)

                    TrainerAppointmentListingContent(content, viewModel::onAction)
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TrainerAppointmentListingContent(
    content: TrainerAppointmentListingUiState.Content,
    onAction: (TrainerAppointmentListingAction) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (content.currentFilter.hasAny) {
            item {
                FlowRow(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    content.currentFilter.selectedTitles.forEach { filterForTitle ->
                        SecondaryPillChip(filterForTitle, selected = false, onClick = {
                            onAction(TrainerAppointmentListingAction.RemoveTitleFilter(filterForTitle))
                        })
                    }
                    content.currentFilter.selectedHours.forEach { filterForTime ->
                        SecondaryPillChip(filterForTime.toString(), selected = false, onClick = {
                            onAction(TrainerAppointmentListingAction.RemoveTimeFilter(filterForTime))
                        })
                    }
                    content.currentFilter.selectedDates.forEach { filterForDate ->
                        SecondaryPillChip(filterForDate.toString(), selected = false, onClick = {
                            onAction(TrainerAppointmentListingAction.RemoveDateFilter(filterForDate))
                        })
                    }
                }
            }
        }

        items(content.filteredLessons) { lesson ->

            BasicAppointmentEventItem(
                title = lesson.title,
                iconId = lesson.iconId,
                date = lesson.startDate.formatToSlashedDate(),
                timePeriod = "${lesson.startTime} - ${lesson.endTime}",
                location = lesson.facilityName,
                trainer = lesson.trainerFullName,
                note = lesson.trainerNote,
                onClick = { onAction(TrainerAppointmentListingAction.NavigateToDetail(lesson.lessonId)) }
            )
        }
    }
}


@Composable
fun AppointmentListingTabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit = {}
) {
    var contentWidth by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .clickable(onClick = onClick)
            .onGloballyPositioned { coordinates ->
                contentWidth = coordinates.size.width
            }
    ) {
        BodyMediumMediumText(
            text = text,
            modifier = Modifier.padding(16.dp)
        )

        if (selected) {
            Box(
                modifier = Modifier
                    .width(with(LocalDensity.current) { contentWidth.toDp() })
                    .height(2.dp)
                    .align(Alignment.BottomCenter)
                    .background(SkyFitColor.border.secondaryButton)
            )
        }
    }
}

@Composable
private fun TrainerAppointmentListingTabRow(
    selectedTab: TrainerAppointmentListingTab,
    onTabSelected: (TrainerAppointmentListingTab) -> Unit,
    onClickFilter: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppointmentListingTabItem(
                text = stringResource(Res.string.lesson_status_completed),
                selected = selectedTab == TrainerAppointmentListingTab.Completed,
                onClick = { onTabSelected(TrainerAppointmentListingTab.Completed) }
            )
            AppointmentListingTabItem(
                text = stringResource(Res.string.lesson_status_active),
                selected = selectedTab == TrainerAppointmentListingTab.Active,
                onClick = { onTabSelected(TrainerAppointmentListingTab.Active) }
            )
            AppointmentListingTabItem(
                text = stringResource(Res.string.lesson_status_cancelled),
                selected = selectedTab == TrainerAppointmentListingTab.Cancelled,
                onClick = { onTabSelected(TrainerAppointmentListingTab.Cancelled) }
            )
            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { onClickFilter() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                BodyMediumMediumText(
                    text = "Filtre",
                    modifier = Modifier,
                    color = SkyFitColor.text.linkInverse
                )
                Icon(
                    painter = painterResource(Res.drawable.ic_filter),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = SkyFitColor.icon.linkInverse
                )
            }

        }

        Divider(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = SkyFitColor.border.secondaryButtonDisabled
        )
    }
}


