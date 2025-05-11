package com.vurgun.skyfit.feature.schedule.screen.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.persona.domain.model.TrainerDetail
import com.vurgun.skyfit.core.data.persona.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.schedule.domain.model.Lesson
import com.vurgun.skyfit.core.data.schedule.domain.model.StatusType
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class TrainerAppointmentListingFilter(
    val selectedTitles: Set<String> = emptySet(),
    val selectedHours: Set<LocalTime> = emptySet(),
    val selectedDates: Set<LocalDate> = emptySet()
) {
    val hasAny = selectedTitles.isNotEmpty() || selectedHours.isNotEmpty() || selectedDates.isNotEmpty()
}

sealed class TrainerAppointmentListingUiState {
    data object Loading : TrainerAppointmentListingUiState()
    data class Error(val message: String) : TrainerAppointmentListingUiState()
    data class Content(
        val activeTab: TrainerAppointmentListingTab = TrainerAppointmentListingTab.Completed,
        val lessons: List<Lesson> = emptyList(),
        val filteredLessons: List<Lesson> = emptyList(),
        val tabCounts: Map<TrainerAppointmentListingTab, Int> = emptyMap(),
        val currentFilter: TrainerAppointmentListingFilter = TrainerAppointmentListingFilter()
    ) : TrainerAppointmentListingUiState()
}

sealed interface TrainerAppointmentListingAction {
    data object NavigateToBack : TrainerAppointmentListingAction
    data object ShowFilter : TrainerAppointmentListingAction
    data class ChangeTab(val tab: TrainerAppointmentListingTab) : TrainerAppointmentListingAction
    data class NavigateToDetail(val lessonId: Int) : TrainerAppointmentListingAction
    data class RemoveTitleFilter(val title: String) : TrainerAppointmentListingAction
    data class RemoveTimeFilter(val time: LocalTime) : TrainerAppointmentListingAction
    data class RemoveDateFilter(val date: LocalDate) : TrainerAppointmentListingAction
}

sealed interface TrainerAppointmentListingEffect {
    data object NavigateToBack : TrainerAppointmentListingEffect
    data object ShowFilter : TrainerAppointmentListingEffect
    data class NavigateToDetail(val lessonId: Int) : TrainerAppointmentListingEffect
}

sealed class TrainerAppointmentListingTab {
    data object Completed : TrainerAppointmentListingTab()
    data object Active : TrainerAppointmentListingTab()
    data object Cancelled : TrainerAppointmentListingTab()
}

class TrainerAppointmentListingViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<TrainerAppointmentListingUiState>(TrainerAppointmentListingUiState.Loading)
    val uiState: StateFlow<TrainerAppointmentListingUiState> = _uiState

    private val _effect = SingleSharedFlow<TrainerAppointmentListingEffect>()
    val effect: SharedFlow<TrainerAppointmentListingEffect> = _effect

    private val trainer: TrainerDetail
        get() = userManager.user.value as? TrainerDetail
            ?: error("❌ current account is not trainer")

    init {
        refreshData()
    }

    fun onAction(action: TrainerAppointmentListingAction) {
        when (action) {
            TrainerAppointmentListingAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, TrainerAppointmentListingEffect.NavigateToBack)

            is TrainerAppointmentListingAction.ChangeTab -> {
                val current = _uiState.value as? TrainerAppointmentListingUiState.Content ?: return
                applyFilter(current.currentFilter, tab = action.tab)
            }

            is TrainerAppointmentListingAction.NavigateToDetail ->
                _effect.emitIn(screenModelScope, TrainerAppointmentListingEffect.NavigateToDetail(action.lessonId))

            TrainerAppointmentListingAction.ShowFilter ->
                _effect.emitIn(screenModelScope, TrainerAppointmentListingEffect.ShowFilter)

            is TrainerAppointmentListingAction.RemoveDateFilter -> {
                val current = _uiState.value as? TrainerAppointmentListingUiState.Content ?: return
                val updatedFilter = current.currentFilter.copy(
                    selectedDates = current.currentFilter.selectedDates - action.date
                )
                applyFilter(updatedFilter)
            }

            is TrainerAppointmentListingAction.RemoveTimeFilter -> {
                val current = _uiState.value as? TrainerAppointmentListingUiState.Content ?: return
                val updatedFilter = current.currentFilter.copy(
                    selectedHours = current.currentFilter.selectedHours - action.time
                )
                applyFilter(updatedFilter)
            }

            is TrainerAppointmentListingAction.RemoveTitleFilter -> {
                val current = _uiState.value as? TrainerAppointmentListingUiState.Content ?: return
                val updatedFilter = current.currentFilter.copy(
                    selectedTitles = current.currentFilter.selectedTitles - action.title
                )
                applyFilter(updatedFilter)
            }

        }
    }

    private fun refreshData() {
        screenModelScope.launch {
            try {
                val allLessons = courseRepository
                    .getLessonsByTrainer(trainerId = trainer.trainerId, "", "")
                    .getOrDefault(emptyList())

                val tabCounts = allLessons.mapNotNull { it.toTabType() }.groupingBy { it }.eachCount()
                val prevState = _uiState.value as? TrainerAppointmentListingUiState.Content
                val selectedTab = prevState?.activeTab ?: TrainerAppointmentListingTab.Completed
                val currentFilter = prevState?.currentFilter ?: TrainerAppointmentListingFilter()

                // Reapply filter if exists
                val filtered = allLessons
                    .filter { it.toTabType() == selectedTab }
                    .filter { lesson ->
                        val title = lesson.title
                        val time = lesson.startTime
                        val date = lesson.startDate

                        (currentFilter.selectedTitles.isEmpty() || title in currentFilter.selectedTitles) &&
                                (currentFilter.selectedHours.isEmpty() || time in currentFilter.selectedHours) &&
                                (currentFilter.selectedDates.isEmpty() || date in currentFilter.selectedDates)
                    }

                _uiState.value = TrainerAppointmentListingUiState.Content(
                    activeTab = selectedTab,
                    lessons = allLessons,
                    filteredLessons = filtered,
                    tabCounts = tabCounts,
                    currentFilter = currentFilter
                )

            } catch (e: Exception) {
                _uiState.value = TrainerAppointmentListingUiState.Error(e.message ?: "Veri alınamadı")
            }
        }
    }

    private fun Lesson.toTabType(): TrainerAppointmentListingTab? = when (statusType) {
        StatusType.Active -> TrainerAppointmentListingTab.Active
        StatusType.Completed -> TrainerAppointmentListingTab.Completed
        StatusType.Cancelled -> TrainerAppointmentListingTab.Cancelled
        else -> null
    }

    fun applyFilter(
        filter: TrainerAppointmentListingFilter,
        tab: TrainerAppointmentListingTab? = null
    ) {
        val current = _uiState.value as? TrainerAppointmentListingUiState.Content ?: return
        val selectedTab = tab ?: current.activeTab

        val filtered = current.lessons
            .filter { it.toTabType() == selectedTab }
            .filter { lesson ->
                val title = lesson.title
                val time = lesson.startTime
                val date = lesson.startDate

                (filter.selectedTitles.isEmpty() || title in filter.selectedTitles) &&
                        (filter.selectedHours.isEmpty() || time in filter.selectedHours) &&
                        (filter.selectedDates.isEmpty() || date in filter.selectedDates)
            }

        _uiState.value = current.copy(
            activeTab = selectedTab,
            currentFilter = filter,
            filteredLessons = filtered
        )
    }

    fun resetFilter() {
        val current = _uiState.value as? TrainerAppointmentListingUiState.Content ?: return
        _uiState.value = current.copy(
            currentFilter = TrainerAppointmentListingFilter(),
            filteredLessons = current.lessons
        )
    }

}