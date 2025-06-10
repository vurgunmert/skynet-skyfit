@file:OptIn(FlowPreview::class)

package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.v1.data.workout.WorkoutTypeUiData
import com.vurgun.skyfit.core.data.v1.domain.user.repository.UserRepository
import com.vurgun.skyfit.core.data.v1.domain.workout.model.WorkoutCategory
import com.vurgun.skyfit.core.data.v1.domain.workout.repository.WorkoutRepository
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
        val allWorkoutTypes: List<WorkoutTypeUiData> = emptyList(),
        val filteredWorkoutTypes: List<WorkoutTypeUiData> = emptyList(),
        val date: LocalDate = LocalDate.now(),
    ) : UserActivityCalendarSearchUiState()

}

sealed class UserActivityCalendarSearchAction {
    data object NavigateToBack : UserActivityCalendarSearchAction()
    data class OnSelectWorkout(val workoutType: WorkoutTypeUiData? = null) : UserActivityCalendarSearchAction()
    data class Search(val query: String?) : UserActivityCalendarSearchAction()
    data class SelectCategory(val categoryId: Int?) : UserActivityCalendarSearchAction()
}

sealed class UserActivityCalendarSearchEffect {
    data object NavigateToBack : UserActivityCalendarSearchEffect()
    data class NavigateToNew(
        val date: LocalDate,
        val workoutType: WorkoutTypeUiData? = null,
        val category: WorkoutCategory? = null
    ) : UserActivityCalendarSearchEffect()
}

class UserActivityCalendarSearchViewModel(
    private val userRepository: UserRepository,
    private val workoutRepository: WorkoutRepository,
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
            runCatching {
                val workoutTypes = userRepository.getWorkoutEvents()
                    .getOrDefault(emptyList())
                    .map { WorkoutTypeUiData(it.id, it.name) }

                val workoutCategories = workoutRepository.getCategories()
                    .getOrThrow()

                _uiState.update(
                    UserActivityCalendarSearchUiState.Content(
                        categories = workoutCategories,
                        allWorkoutTypes = workoutTypes,
                        date = onDate ?: LocalDate.now()
                    )
                )

                updateWorkoutList()
            }.onFailure {
                _uiState.update(UserActivityCalendarSearchUiState.Error(it.message))
            }
        }
    }

    private fun selectWorkout(workoutType: WorkoutTypeUiData? = null) {
        val content = (uiState.value as? UserActivityCalendarSearchUiState.Content) ?: return

        val category = workoutType?.categoryId?.let { catId -> content.categories.first{ it.id == catId } }
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
