@file:OptIn(FlowPreview::class)

package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutCategories
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutCategory
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutType
import com.vurgun.skyfit.core.data.schedule.domain.repository.UserCalendarRepository
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.now
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed class UserActivityCalendarSearchUiState {
    object Loading : UserActivityCalendarSearchUiState()
    data class Error(val message: String?) : UserActivityCalendarSearchUiState()
    data class Content(
        val searchQuery: String? = null,
        val categories: List<WorkoutCategory> = emptyList(),
        val selectedCategoryId: Int? = null,
        val allWorkoutTypes: List<WorkoutType> = emptyList(),
        val filteredWorkoutTypes: List<WorkoutType> = emptyList(),
        val date: LocalDate = LocalDate.now(),
    ) : UserActivityCalendarSearchUiState()

}

sealed class UserActivityCalendarSearchAction {
    data object NavigateToBack : UserActivityCalendarSearchAction()
    data class OnSelectWorkout(val workoutType: WorkoutType? = null) : UserActivityCalendarSearchAction()
    data class Search(val query: String?) : UserActivityCalendarSearchAction()
    data class SelectCategory(val categoryId: Int?) : UserActivityCalendarSearchAction()
}

sealed class UserActivityCalendarSearchEffect {
    data object NavigateToBack : UserActivityCalendarSearchEffect()
    data class NavigateToNew(
        val date: LocalDate,
        val workoutType: WorkoutType? = null,
        val category: WorkoutCategory? = null
    ) : UserActivityCalendarSearchEffect()
}

class UserActivityCalendarSearchViewModel(
    private val calendarRepository: UserCalendarRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<UserActivityCalendarSearchUiState>(UserActivityCalendarSearchUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<UserActivityCalendarSearchEffect>()
    val effect: SharedFlow<UserActivityCalendarSearchEffect> = _effect

    private val searchQueryFlow = MutableStateFlow<String?>(null)
    val searchQuery = searchQueryFlow.asStateFlow()

    init {
        screenModelScope.launch {
            searchQueryFlow
                .debounce(300)
                .distinctUntilChanged()
                .collect { query ->
                    updateWorkoutList(query)
                }
        }
    }

    fun onAction(action: UserActivityCalendarSearchAction) {
        when (action) {
            is UserActivityCalendarSearchAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, UserActivityCalendarSearchEffect.NavigateToBack)

            is UserActivityCalendarSearchAction.OnSelectWorkout ->
                selectWorkout(action.workoutType)

            is UserActivityCalendarSearchAction.Search -> {
                searchQueryFlow.value = action.query
            }

            is UserActivityCalendarSearchAction.SelectCategory -> selectCategory(action.categoryId)
        }
    }

    fun loadData(onDate: LocalDate? = null) {
        screenModelScope.launch {
            val workoutTypes = calendarRepository.getWorkoutEvents()
                .getOrDefault(emptyList())
                .map { WorkoutType(it.id, it.name) }

            _uiState.update(
                UserActivityCalendarSearchUiState.Content(
                    categories = WorkoutCategories.ALL,
                    allWorkoutTypes = workoutTypes,
                    date = onDate ?: LocalDate.now()
                )
            )

            updateWorkoutList()
        }
    }

    private fun selectWorkout(workoutType: WorkoutType? = null) {
        val content = (uiState.value as? UserActivityCalendarSearchUiState.Content) ?: return

        val category = workoutType?.categoryId?.let { WorkoutCategories.find(it) }
        _effect.emitIn(
            screenModelScope, UserActivityCalendarSearchEffect.NavigateToNew(
                date = content.date,
                workoutType = workoutType,
                category = category
            )
        )
    }

    private fun updateWorkoutList(query: String? = null) {
        val content = uiState.value as? UserActivityCalendarSearchUiState.Content ?: return
        val searchQuery = query?.trim()

        val filtered = content.allWorkoutTypes
            .asSequence()
            .filter { content.selectedCategoryId == null || it.categoryId == content.selectedCategoryId }
            .filter { searchQuery.isNullOrEmpty() || it.name.contains(searchQuery, ignoreCase = true) }
            .toList()

        _uiState.update(
            content.copy(
                filteredWorkoutTypes = filtered
            )
        )
    }

    private fun selectCategory(categoryId: Int?) {
        (uiState.value as? UserActivityCalendarSearchUiState.Content)?.let { content ->
            if (content.selectedCategoryId == categoryId) {
                _uiState.update(content.copy(selectedCategoryId = null))
            } else {
                _uiState.update(content.copy(selectedCategoryId = categoryId))
            }
            updateWorkoutList()
        }
    }
}
