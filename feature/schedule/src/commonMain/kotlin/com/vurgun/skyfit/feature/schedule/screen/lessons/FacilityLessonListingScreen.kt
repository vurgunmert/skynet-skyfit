package com.vurgun.skyfit.feature.schedule.screen.lessons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.ui.components.button.SecondaryMediumButton
import com.vurgun.skyfit.core.ui.components.event.EditableLessonEventItem
import com.vurgun.skyfit.core.ui.components.loader.FullScreenLoaderContent
import com.vurgun.skyfit.core.ui.components.schedule.LessonEventItemPopupMenu
import com.vurgun.skyfit.core.ui.components.schedule.weekly.CalendarWeekDaySelector
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberCalendarWeekDaySelectorController
import com.vurgun.skyfit.core.ui.components.schedule.weekly.rememberWeekDaySelectorState
import com.vurgun.skyfit.core.ui.components.special.CompactTopBar
import com.vurgun.skyfit.core.ui.components.special.ExpandedTopBar
import com.vurgun.skyfit.core.ui.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.core.ui.styling.SkyFitColor
import com.vurgun.skyfit.core.ui.styling.SkyFitTypography
import com.vurgun.skyfit.core.ui.utils.CollectEffect
import com.vurgun.skyfit.core.ui.utils.LocalCompactOverlayController
import com.vurgun.skyfit.core.ui.utils.LocalWindowSize
import com.vurgun.skyfit.core.ui.utils.WindowSize
import org.jetbrains.compose.resources.stringResource
import skyfit.core.ui.generated.resources.*

class FacilityLessonListingScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel = koinScreenModel<FacilityLessonListingViewModel>()
        val overlayController = LocalCompactOverlayController.current

        CollectEffect(viewModel.effect) { effect ->
            when (effect) {
                is FacilityLessonListingEffect.NavigateToBack -> {
                    overlayController?.invoke(null)
                    navigator.pop()
                }

                is FacilityLessonListingEffect.NavigateToEditLesson -> {
                    navigator.push(FacilityLessonEditScreen(effect.lesson))
                }

                FacilityLessonListingEffect.NavigateToNewLesson -> {
                    navigator.push(FacilityLessonEditScreen())
                }
            }
        }

        MobileFacilityLessonListScreen(viewModel)
    }
}

@Composable
private fun MobileFacilityLessonListScreen(viewModel: FacilityLessonListingViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    val weekDaySelectorController = rememberCalendarWeekDaySelectorController()
    val calendarUiState = rememberWeekDaySelectorState(weekDaySelectorController)

    LaunchedEffect(calendarUiState.selectedDate) {
        viewModel.loadLessonsFor(date = calendarUiState.selectedDate)
    }

    SkyFitMobileScaffold(
        topBar = {
            when (LocalWindowSize.current) {
                WindowSize.COMPACT, WindowSize.MEDIUM -> {
                    CompactTopBar(
                        stringResource(Res.string.lessons_label),
                        onClickBack = { viewModel.onAction(FacilityLessonListingAction.NavigateToBack) })
                }

                WindowSize.EXPANDED -> {
                    ExpandedTopBar(
                        stringResource(Res.string.lessons_label),
                        onClickBack = { viewModel.onAction(FacilityLessonListingAction.NavigateToBack) })
                }
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            CalendarWeekDaySelector(
                daysOfWeek = calendarUiState.weekDays,
                onDaySelected = weekDaySelectorController::setSelectedDate,
                onPreviousWeek = weekDaySelectorController::loadPreviousWeek,
                onNextWeek = weekDaySelectorController::loadNextWeek
            )

            if (uiState.isLoading) {
                FullScreenLoaderContent()
            } else if (uiState.isEmpty) {
                EmptyLessonListItem(onClickNew = { viewModel.onAction(FacilityLessonListingAction.NavigateToNewLesson) })
            } else {
                FacilityLessonListContentGroup(
                    uiState = uiState,
                    onClickNew = { viewModel.onAction(FacilityLessonListingAction.NavigateToNewLesson) },
                    onEdit = { viewModel.onAction(FacilityLessonListingAction.NavigateToEditLesson(it)) },
                    onDeactivate = { viewModel.onAction(FacilityLessonListingAction.DeactivateLesson(it)) },
                    onActivate = { viewModel.onAction(FacilityLessonListingAction.ActivateLesson(it)) },
                    onDelete = { viewModel.onAction(FacilityLessonListingAction.DeleteLesson(it)) },
                )
            }

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
private fun FacilityLessonListContentGroup(
    uiState: FacilityLessonListUiState,
    onClickNew: () -> Unit,
    onActivate: (lessonId: Int) -> Unit,
    onDeactivate: (lessonId: Int) -> Unit,
    onEdit: (lessonId: Int) -> Unit,
    onDelete: (lessonId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        ActiveSessionItemsColumn(uiState.activeLessons, onClickNew, onDeactivate, onEdit, onDelete)

        if (uiState.inactiveLessons.isNotEmpty()) {
            Spacer(Modifier.height(40.dp))
            InactiveSessionItemsColumn(uiState.inactiveLessons, onClickNew, onActivate, onEdit, onDelete)
        }
    }
}

@Composable
private fun ActiveSessionItemsColumn(
    items: List<LessonSessionItemViewData>,
    onClickNew: () -> Unit,
    onDeactivate: (lessonId: Int) -> Unit,
    onEdit: (lessonId: Int) -> Unit,
    onDelete: (lessonId: Int) -> Unit
) {
    var openMenuItemId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Aktif", style = SkyFitTypography.heading5)
            Spacer(Modifier.weight(1f))
            Text(
                text = "+ Yeni",
                style = SkyFitTypography.bodyMediumRegular,
                color = SkyFitColor.text.secondary,
                modifier = Modifier.clickable(onClick = onClickNew)
            )
        }

        items.forEach { item ->
            EditableLessonEventItem(
                title = item.title,
                iconId = item.iconId,
                date = item.date.toString(),
                timePeriod = item.hours.toString(),
                location = item.location.toString(),
                trainer = item.trainer.toString(),
                note = item.note,
                isMenuOpen = openMenuItemId == item.lessonId,
                onMenuToggle = { isOpen -> openMenuItemId = if (isOpen) item.lessonId else null },
                menuContent = {
                    LessonEventItemPopupMenu(
                        isOpen = openMenuItemId == item.lessonId,
                        onDismiss = { openMenuItemId = null },
                        onDeactivate = { onDeactivate(item.lessonId) },
                        onEdit = { onEdit(item.lessonId) },
                        onDelete = { onDelete(item.lessonId) }
                    )
                }
            )
        }
    }
}

@Composable
private fun InactiveSessionItemsColumn(
    items: List<LessonSessionItemViewData>,
    onClickNew: () -> Unit,
    onActivate: (lessonId: Int) -> Unit,
    onEdit: (lessonId: Int) -> Unit,
    onDelete: (lessonId: Int) -> Unit
) {
    var openMenuItemId by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(Res.string.status_out_of_use), style = SkyFitTypography.heading5)
            Spacer(Modifier.weight(1f))
        }

        items.forEach { item ->

            EditableLessonEventItem(
                title = item.title,
                iconId = item.iconId,
                date = item.date.toString(),
                timePeriod = item.hours.toString(),
                location = item.location.toString(),
                trainer = item.trainer.toString(),
                note = item.note,
                isMenuOpen = openMenuItemId == item.lessonId,
                onMenuToggle = { isOpen -> openMenuItemId = if (isOpen) item.lessonId else null },
                menuContent = {
                    LessonEventItemPopupMenu(
                        isOpen = openMenuItemId == item.lessonId,
                        onDismiss = { openMenuItemId = null },
                        onActivate = { onActivate(item.lessonId) },
                        onEdit = { onEdit(item.lessonId) },
                        onDelete = { onDelete(item.lessonId) }
                    )
                }
            )
        }
    }
}


@Composable
private fun EmptyLessonListItem(onClickNew: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 188.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.facility_no_lesson_on_selected_date_message),
            style = SkyFitTypography.bodyMediumSemibold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        SecondaryMediumButton(
            text = stringResource(Res.string.lesson_add_action),
            rightIconRes = Res.drawable.ic_plus,
            onClick = onClickNew
        )
    }
}