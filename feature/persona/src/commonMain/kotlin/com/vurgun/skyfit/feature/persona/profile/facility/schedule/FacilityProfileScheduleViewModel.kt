package com.vurgun.skyfit.feature.persona.profile.facility.schedule

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.persona.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.persona.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.core.data.schedule.data.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.schedule.data.model.LessonSessionItemViewData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed interface FacilityProfileScheduleUiState {
    data object Loading : FacilityProfileScheduleUiState
    data class Error(val message: String) : FacilityProfileScheduleUiState
    data class Content(val profile: FacilityProfile) : FacilityProfileScheduleUiState
}

sealed interface FacilityProfileScheduleAction {
    data object NavigateBack : FacilityProfileScheduleAction
    data class ChangeDate(val startDate: LocalDate, val endDate: LocalDate?) : FacilityProfileScheduleAction
    data class ToggleLessonSelection(val lesson: LessonSessionItemViewData) : FacilityProfileScheduleAction
    data object BookAppointment : FacilityProfileScheduleAction
}

sealed interface FacilityProfileScheduleEffect {
    data object NavigateBack : FacilityProfileScheduleEffect
    data class ShowBookingError(val message: String) : FacilityProfileScheduleEffect
    data class NavigateToAppointmentDetail(val lpId: Int) : FacilityProfileScheduleEffect
}

class FacilityProfileScheduleViewModel(
    private val profileRepository: ProfileRepository,
    private val courseRepository: CourseRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _uiState = MutableStateFlow<FacilityProfileScheduleUiState>(FacilityProfileScheduleUiState.Loading)
    val uiState: StateFlow<FacilityProfileScheduleUiState> = _uiState

    private val _lessons = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val lessons: StateFlow<List<LessonSessionItemViewData>> = _lessons

    private val _effect = SingleSharedFlow<FacilityProfileScheduleEffect>()
    val effect: SharedFlow<FacilityProfileScheduleEffect> = _effect

    private val _isBookingEnabled = MutableStateFlow(false)
    val isBookingEnabled: StateFlow<Boolean> get() = _isBookingEnabled

    private var currentFacilityId: Int? = null
    private var selectedStartDate: LocalDate = LocalDate.now()
    private var selectedEndDate: LocalDate? = null

    fun onAction(action: FacilityProfileScheduleAction) {
        when (action) {
            is FacilityProfileScheduleAction.NavigateBack -> emitEffect(FacilityProfileScheduleEffect.NavigateBack)
            is FacilityProfileScheduleAction.ChangeDate -> {
                selectedStartDate = action.startDate
                selectedEndDate = action.endDate
                updateLessons(action.startDate, action.endDate)
            }
            is FacilityProfileScheduleAction.ToggleLessonSelection -> toggleSelection(action.lesson)
            is FacilityProfileScheduleAction.BookAppointment -> bookAppointment()
        }
    }

    fun loadData(facilityId: Int) {
        currentFacilityId = facilityId

        screenModelScope.launch {
            _uiState.value = FacilityProfileScheduleUiState.Loading

            val profileDeferred = async { profileRepository.getFacilityProfile(facilityId).getOrThrow() }
            val lessonsDeferred = async { fetchLessons(facilityId, selectedStartDate, selectedEndDate) }

            try {
                _uiState.value = FacilityProfileScheduleUiState.Content(profile = profileDeferred.await())
                _lessons.value = lessonsDeferred.await()
            } catch (e: Exception) {
                _uiState.value = FacilityProfileScheduleUiState.Error(e.message ?: "Error loading profile")
            }
        }
    }

    private fun toggleSelection(item: LessonSessionItemViewData) {
        val updatedList = _lessons.value.map {
            if (it.lessonId == item.lessonId) {
                it.copy(selected = !it.selected)
            } else {
                it.copy(selected = false)
            }
        }

        _lessons.value = updatedList

        _isBookingEnabled.value = updatedList.any { it.selected }
    }

    private fun bookAppointment() {
        val selectedLesson = _lessons.value.firstOrNull { it.selected } ?: return

        screenModelScope.launch {
            try {
                val response = courseRepository.bookAppointment(lessonId = selectedLesson.lessonId).getOrThrow()

                _lessons.value = _lessons.value.map { it.copy(selected = false) }
                _isBookingEnabled.value = false

                // Re-fetch with current selected calendar range
                val id = currentFacilityId ?: return@launch
                _lessons.value = fetchLessons(id, selectedStartDate, selectedEndDate)

                emitEffect(FacilityProfileScheduleEffect.NavigateToAppointmentDetail(lpId = response.lpId))

            } catch (e: Exception) {
                emitEffect(FacilityProfileScheduleEffect.ShowBookingError(e.message ?: "Derse kayıt hatası"))
            }
        }
    }

    private fun updateLessons(
        startDate: LocalDate,
        endDate: LocalDate?
    ) {
        val id = currentFacilityId ?: return
        screenModelScope.launch {
            val currentState = _uiState.value
            if (currentState is FacilityProfileScheduleUiState.Content) {
                try {
                    _lessons.value = fetchLessons(id, startDate, endDate)
                } catch (e: Exception) {
                    _uiState.value = FacilityProfileScheduleUiState.Error("Failed to update lessons: ${e.message}")
                }
            }
        }
    }

    private suspend fun fetchLessons(
        facilityId: Int,
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate? = null
    ): List<LessonSessionItemViewData> {
        return courseRepository.getLessonsByFacility(facilityId, startDate, endDate ?: startDate)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private fun emitEffect(effect: FacilityProfileScheduleEffect) {
        screenModelScope.launch {
            _effect.emit(effect)
        }
    }
}
