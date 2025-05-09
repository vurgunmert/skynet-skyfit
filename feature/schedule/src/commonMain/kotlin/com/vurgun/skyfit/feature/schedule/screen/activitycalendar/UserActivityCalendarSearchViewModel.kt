package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.WorkoutCategories
import com.vurgun.skyfit.core.data.domain.model.WorkoutType
import com.vurgun.skyfit.core.data.domain.model.workoutTypes
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class UserActivityCalendarSearchState(
    val searchQuery: String = "",
    val selectedCategoryId: Int? = null
)

sealed class UserActivityCalendarSearchAction {
    data object NavigateToBack : UserActivityCalendarSearchAction()
    data object NavigateToNew : UserActivityCalendarSearchAction()
    data class Search(val query: String?) : UserActivityCalendarSearchAction()
    data class SelectCategory(val categoryId: Int?) : UserActivityCalendarSearchAction()
}

sealed class UserActivityCalendarSearchEffect {
    data object NavigateToBack : UserActivityCalendarSearchEffect()
    data object NavigateToNew : UserActivityCalendarSearchEffect()
}

class UserActivityCalendarSearchViewModel : ScreenModel {

    private val _state = MutableStateFlow(UserActivityCalendarSearchState())
    val state: StateFlow<UserActivityCalendarSearchState> = _state

    private val _effect = SingleSharedFlow<UserActivityCalendarSearchEffect>()
    val effect: SharedFlow<UserActivityCalendarSearchEffect> = _effect

    private val workoutCategories = WorkoutCategories.ALL

    private val _filteredWorkouts = MutableStateFlow<List<Pair<String, List<WorkoutType>>>>(emptyList())
    val filteredWorkouts: StateFlow<List<Pair<String, List<WorkoutType>>>> = _filteredWorkouts

    init {
        updateWorkoutList()
    }

    fun onAction(action: UserActivityCalendarSearchAction) {
        when (action) {
            is UserActivityCalendarSearchAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, UserActivityCalendarSearchEffect.NavigateToBack)

            is UserActivityCalendarSearchAction.NavigateToNew ->
                _effect.emitIn(screenModelScope, UserActivityCalendarSearchEffect.NavigateToNew)

            is UserActivityCalendarSearchAction.Search -> {
                _state.update { it.copy(searchQuery = action.query.orEmpty()) }
                updateWorkoutList()
            }

            is UserActivityCalendarSearchAction.SelectCategory -> {
                _state.update { it.copy(selectedCategoryId = action.categoryId) }
                updateWorkoutList()
            }
        }
    }

    private fun updateWorkoutList(locale: String = "tr") {
        val current = _state.value
        val query = current.searchQuery.trim()
        val selected = current.selectedCategoryId
        val effectiveCategoryId = if (query.isNotBlank()) null else selected

        val filtered = workoutTypes.filter { workout ->
            val matchesQuery = query.isBlank() ||
                    workout.name[locale]?.contains(query, ignoreCase = true) == true ||
                    workout.name["en"]?.contains(query, ignoreCase = true) == true

            val matchesCategory = effectiveCategoryId == null || workout.categoryId == effectiveCategoryId

            matchesQuery && matchesCategory
        }

        val addedIds = mutableSetOf<Int>()

        fun addOnce(list: List<WorkoutType>): List<WorkoutType> {
            return list.filter { addedIds.add(it.id) } // only add if not already added
        }

        val newList = addOnce(filtered.filter { it.isNew })
        val popularList = addOnce(filtered.filter { it.isPopular })
        val others = addOnce(filtered) // remaining ones not in previous groups

        val grouped = listOfNotNull(
            if (newList.isNotEmpty()) "En yeni" to newList else null,
            if (popularList.isNotEmpty()) "En popüler" to popularList else null,
            if (others.isNotEmpty()) "Tümü" to others else null
        )

        _filteredWorkouts.value = grouped
    }

    fun getCategoryChips(locale: String = "tr"): List<Pair<Int?, String>> {
        return listOf(null to "Tümü") + workoutCategories.map {
            it.id to (it.displayName[locale] ?: it.displayName["en"] ?: "Kategori")
        }
    }
}
