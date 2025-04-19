package com.vurgun.skyfit.feature.courses.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.feature.calendar.components.component.calendar.weekly.CalendarWeekDaySelector
import com.vurgun.skyfit.feature.calendar.components.component.calendar.weekly.CalendarWeekDaySelectorViewModel
import com.vurgun.skyfit.feature.calendar.components.component.calendar.weekly.rememberWeekDaySelectorState
import com.vurgun.skyfit.feature.courses.component.LessonEventItemPopupMenu
import com.vurgun.skyfit.ui.core.components.button.SecondaryMediumButton
import com.vurgun.skyfit.ui.core.components.event.EditableLessonEventItem
import com.vurgun.skyfit.ui.core.components.loader.FullScreenLoader
import com.vurgun.skyfit.ui.core.components.special.SkyFitMobileScaffold
import com.vurgun.skyfit.ui.core.components.special.SkyFitScreenHeader
import com.vurgun.skyfit.ui.core.styling.SkyFitColor
import com.vurgun.skyfit.ui.core.styling.SkyFitTypography
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import skyfit.ui.core.generated.resources.Res
import skyfit.ui.core.generated.resources.ic_plus
import skyfit.ui.core.generated.resources.lesson_add_action
import skyfit.ui.core.generated.resources.lessons_label
import skyfit.ui.core.generated.resources.status_out_of_use

@Composable
fun MobileFacilityLessonListScreen(
    goToBack: () -> Unit,
    onNewLesson: () -> Unit,
    onEditLesson: (Lesson) -> Unit,
    viewModel: FacilityLessonListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val calendarViewModel: CalendarWeekDaySelectorViewModel = viewModel()
    val calendarUiState = rememberWeekDaySelectorState(calendarViewModel)

    LaunchedEffect(calendarUiState.selectedDate) {
        viewModel.loadLessonsFor(date = calendarUiState.selectedDate)
    }

    LaunchedEffect(Unit) {
        viewModel.editLessonFlow.collect { lesson ->
            onEditLesson(lesson)
        }
    }

    SkyFitMobileScaffold(
        topBar = {
            SkyFitScreenHeader(stringResource(Res.string.lessons_label), onClickBack = goToBack)
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            CalendarWeekDaySelector(
                daysOfWeek = calendarUiState.weekDays,
                onDaySelected = calendarViewModel::setSelectedDate,
                onPreviousWeek = calendarViewModel::loadPreviousWeek,
                onNextWeek = calendarViewModel::loadNextWeek
            )

            if (uiState.isLoading) {
                FullScreenLoader()
            } else if (uiState.isEmpty) {
                EmptyLessonListItem(onClickNew = onNewLesson)
            } else {
                FacilityLessonListContentGroup(
                    uiState = uiState,
                    onClickNew = onNewLesson,
                    onEdit = viewModel::navigateToEdit,
                    onDeactivate = viewModel::deactivateLesson,
                    onActivate = viewModel::activateLesson,
                    onDelete = viewModel::deleteLesson,
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
    Box(
        Modifier
            .fillMaxWidth()
            .height(188.dp), contentAlignment = Alignment.Center
    ) {
        SecondaryMediumButton(
            text = stringResource(Res.string.lesson_add_action),
            rightIconPainter = painterResource(Res.drawable.ic_plus),
            onClick = onClickNew
        )
    }
}