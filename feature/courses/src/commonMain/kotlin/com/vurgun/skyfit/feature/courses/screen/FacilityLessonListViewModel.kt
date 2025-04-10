package com.vurgun.skyfit.feature.courses.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.ui.core.styling.SkyFitAsset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

data class FacilityLessonListUiState(
    val isLoading: Boolean = true,
    val lessons: List<LessonSessionItemViewData> = emptyList(),
    val activeLessons: List<LessonSessionItemViewData> = emptyList(),
    val inactiveLessons: List<LessonSessionItemViewData> = emptyList()
) {
    val isEmpty: Boolean get() = lessons.isEmpty()
}


class FacilityLessonListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FacilityLessonListUiState())
    val uiState: StateFlow<FacilityLessonListUiState> = _uiState

    fun loadDataAt(date: LocalDate) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val fetchedClasses = fakeLessons //TODO: Load from data source

            _uiState.update {
                it.copy(
                    isLoading = false,
                    lessons = fetchedClasses,
                    activeLessons = fetchedClasses.filter { it.enabled },
                    inactiveLessons = fetchedClasses.filterNot { it.enabled }
                )
            }
        }
    }

    fun toggleClassStatus(sessionId: String) {
        _uiState.update { state ->
            val updated = state.lessons.map {
                if (it.sessionId == sessionId) it.copy(enabled = !it.enabled) else it
            }
            state.copy(
                lessons = updated,
                activeLessons = updated.filter { it.enabled },
                inactiveLessons = updated.filterNot { it.enabled }
            )
        }
    }

    fun addNewClass(newSession: LessonSessionItemViewData) {
        _uiState.update { state ->
            val updated = state.lessons + newSession
            state.copy(
                lessons = updated,
                activeLessons = updated.filter { it.enabled },
                inactiveLessons = updated.filterNot { it.enabled }
            )
        }
    }

    fun deleteClass(sessionId: String) {
        _uiState.update { state ->
            val updated = state.lessons.filterNot { it.sessionId == sessionId }
            state.copy(
                lessons = updated,
                activeLessons = updated.filter { it.enabled },
                inactiveLessons = updated.filterNot { it.enabled }
            )
        }
    }
}


val fakeLessons = listOf(
    LessonSessionItemViewData(
        iconId = SkyFitAsset.SkyFitIcon.PUSH_UP.id,
        title = "Shoulders and Abs",
        date = "18/11/2024",
        hours = "08:00 - 09:00",
        trainer = "Micheal Blake",
        location = "@ironstudio (İzmir - Bornova)",
        note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
        enabled = true,
        sessionId = "1111"
    ),
    LessonSessionItemViewData(
        iconId = SkyFitAsset.SkyFitIcon.HIGH_INTENSITY_TRAINING.id,
        title = "Reformer Pilates",
        trainer = "Micheal Blake",
        date = "18/11/2024",
        hours = "08:00 - 09:00",
        category = "Pilates",
        location = "@ironstudio (İzmir - Bornova)",
        enabled = false,
        sessionId = "2222"
    ),
    LessonSessionItemViewData(
        iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
        title = "Fitness",
        date = "18/11/2024",
        hours = "08:00 - 09:00",
        trainer = "Micheal Blake",
        location = "@ironstudio (İzmir - Bornova)",
        category = "PT",
        enabled = true,
        sessionId = "3333"
    ),
    LessonSessionItemViewData(
        iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
        title = "Fitness",
        date = "18/11/2024",
        hours = "08:00 - 09:00",
        trainer = "Micheal Blake",
        location = "@ironstudio (İzmir - Bornova)",
        category = "PT",
        enabled = true,
        sessionId = "3333"
    ),
    LessonSessionItemViewData(
        iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
        title = "Fitness",
        date = "18/11/2024",
        hours = "08:00 - 09:00",
        trainer = "Micheal Blake",
        location = "@ironstudio (İzmir - Bornova)",
        category = "PT",
        enabled = true,
        sessionId = "3333"
    ),
    LessonSessionItemViewData(
        iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
        title = "Fitness",
        date = "18/11/2024",
        hours = "08:00 - 09:00",
        trainer = "Micheal Blake",
        location = "@ironstudio (İzmir - Bornova)",
        category = "PT",
        enabled = true,
        sessionId = "3333"
    ),
    LessonSessionItemViewData(
        iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
        title = "Fitness",
        date = "18/11/2024",
        hours = "08:00 - 09:00",
        trainer = "Micheal Blake",
        location = "@ironstudio (İzmir - Bornova)",
        category = "PT",
        enabled = true,
        sessionId = "3333"
    )
)