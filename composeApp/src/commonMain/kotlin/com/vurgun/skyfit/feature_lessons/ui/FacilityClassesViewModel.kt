package com.vurgun.skyfit.feature_lessons.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.ui.resources.SkyFitAsset
import com.vurgun.skyfit.feature_lessons.ui.components.viewdata.LessonSessionItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class FacilityClassesViewModel : ViewModel() {

    private val _allClasses = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val allClasses: StateFlow<List<LessonSessionItemViewData>> = _allClasses

    private val _activeClasses = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val activeClasses: StateFlow<List<LessonSessionItemViewData>> = _activeClasses

    private val _inactiveClasses = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val inactiveClasses: StateFlow<List<LessonSessionItemViewData>> = _inactiveClasses

    fun loadData(date: LocalDate) {
        viewModelScope.launch {
            // Simulating fetching sessions (API or use-case)
            val fetchedClasses = listOf(
                LessonSessionItemViewData(
                    iconId = SkyFitAsset.SkyFitIcon.PUSH_UP.id,
                    title = "Shoulders and Abs",
                    date = "08:00 - 09:00",
                    trainer = "Micheal Blake",
                    note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
                    enabled = true,
                    sessionId = "1111"
                ),
                LessonSessionItemViewData(
                    iconId = SkyFitAsset.SkyFitIcon.HIGH_INTENSITY_TRAINING.id,
                    title = "Reformer Pilates",
                    trainer = "Micheal Blake",
                    category = "Pilates",
                    enabled = false,
                    sessionId = "2222"
                ),
                LessonSessionItemViewData(
                    iconId = SkyFitAsset.SkyFitIcon.BICEPS_FORCE.id,
                    title = "Fitness",
                    date = "09:00 - 10:00",
                    trainer = "Micheal Blake",
                    category = "PT",
                    enabled = true,
                    sessionId = "3333"
                )
            )

            _allClasses.value = fetchedClasses
            filterClasses()
        }
    }

    private fun filterClasses() {
        _activeClasses.value = _allClasses.value.filter { it.enabled }
        _inactiveClasses.value = _allClasses.value.filterNot { it.enabled }
    }

    fun toggleClassStatus(sessionId: String) {
        viewModelScope.launch {
            _allClasses.update { classes ->
                classes.map {
                    if (it.sessionId == sessionId) it.copy(enabled = !it.enabled) else it
                }
            }
            filterClasses()
        }
    }

    fun addNewClass(newSession: LessonSessionItemViewData) {
        _allClasses.update { it + newSession }
        filterClasses()
    }

    fun deleteClass(sessionId: String) {
        _allClasses.update { it.filterNot { it.sessionId == sessionId } }
        filterClasses()
    }
}
