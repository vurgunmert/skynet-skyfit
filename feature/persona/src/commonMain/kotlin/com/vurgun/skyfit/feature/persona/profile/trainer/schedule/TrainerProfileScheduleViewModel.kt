package com.vurgun.skyfit.feature.persona.profile.trainer.schedule

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.persona.domain.model.TrainerProfile
import com.vurgun.skyfit.core.data.persona.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.core.data.schedule.data.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.core.data.schedule.data.model.LessonSessionItemViewData
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

sealed interface TrainerProfileScheduleUiState {
    data object Loading : TrainerProfileScheduleUiState
    data class Error(val message: String) : TrainerProfileScheduleUiState
    data class Content(val profile: TrainerProfile) : TrainerProfileScheduleUiState
}

sealed interface TrainerProfileScheduleAction {
    data object NavigateBack : TrainerProfileScheduleAction
    data class ChangeDate(val startDate: LocalDate, val endDate: LocalDate?) : TrainerProfileScheduleAction
    data class ToggleLessonSelection(val lesson: LessonSessionItemViewData) : TrainerProfileScheduleAction
    data object BookAppointment : TrainerProfileScheduleAction
}

sealed interface TrainerProfileScheduleEffect {
    data object NavigateBack : TrainerProfileScheduleEffect
    data class ShowBookingError(val message: String) : TrainerProfileScheduleEffect
    data class NavigateToAppointmentDetail(val lpId: Int) : TrainerProfileScheduleEffect
}

class TrainerProfileScheduleViewModel(
    private val profileRepository: ProfileRepository,
    private val courseRepository: CourseRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _uiState = MutableStateFlow<TrainerProfileScheduleUiState>(TrainerProfileScheduleUiState.Loading)
    val uiState: StateFlow<TrainerProfileScheduleUiState> = _uiState

    private val _lessons = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val lessons: StateFlow<List<LessonSessionItemViewData>> = _lessons

    private val _effect = MutableSharedFlow<TrainerProfileScheduleEffect>()
    val effect: SharedFlow<TrainerProfileScheduleEffect> = _effect

    private val _isBookingEnabled = MutableStateFlow(false)
    val isBookingEnabled: StateFlow<Boolean> get() = _isBookingEnabled

    private var currentTrainerId: Int? = null
    private var selectedStartDate: LocalDate = LocalDate.now()
    private var selectedEndDate: LocalDate? = null

    fun onAction(action: TrainerProfileScheduleAction) {
        when (action) {
            TrainerProfileScheduleAction.NavigateBack -> emitEffect(TrainerProfileScheduleEffect.NavigateBack)
            is TrainerProfileScheduleAction.ChangeDate -> {
                selectedStartDate = action.startDate
                selectedEndDate = action.endDate
                updateLessons(action.startDate, action.endDate)
            }

            is TrainerProfileScheduleAction.ToggleLessonSelection -> toggleSelection(action.lesson)
            is TrainerProfileScheduleAction.BookAppointment -> bookAppointment()
        }
    }

    fun loadData(trainerId: Int) {
        currentTrainerId = trainerId

        screenModelScope.launch {
            _uiState.value = TrainerProfileScheduleUiState.Loading

            val profileDeferred = async { profileRepository.getTrainerProfile(trainerId).getOrThrow() }
            val lessonsDeferred = async { fetchLessons(trainerId, selectedStartDate, selectedEndDate) }

            try {
                _uiState.value = TrainerProfileScheduleUiState.Content(profile = profileDeferred.await())
                _lessons.value = lessonsDeferred.await()
            } catch (e: Exception) {
                _uiState.value = TrainerProfileScheduleUiState.Error(e.message ?: "Error loading profile")
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
                val id = currentTrainerId ?: return@launch
                _lessons.value = fetchLessons(id, selectedStartDate, selectedEndDate)

                emitEffect(TrainerProfileScheduleEffect.NavigateToAppointmentDetail(lpId = response.lpId))

            } catch (e: Exception) {
                emitEffect(TrainerProfileScheduleEffect.ShowBookingError(e.message ?: "Derse kayıt hatası"))
            }
        }
    }

    private fun updateLessons(
        startDate: LocalDate,
        endDate: LocalDate?
    ) {
        val id = currentTrainerId ?: return
        screenModelScope.launch {
            val currentState = _uiState.value
            if (currentState is TrainerProfileScheduleUiState.Content) {
                try {
                    _lessons.value = fetchLessons(id, startDate, endDate)
                } catch (e: Exception) {
                    _uiState.value = TrainerProfileScheduleUiState.Error("Failed to update lessons: ${e.message}")
                }
            }
        }
    }

    private suspend fun fetchLessons(
        trainerId: Int,
        startDate: LocalDate = LocalDate.now(),
        endDate: LocalDate? = null
    ): List<LessonSessionItemViewData> {
        return courseRepository.getLessonsByTrainer(trainerId, startDate, endDate ?: startDate)
            .map { it.map(lessonMapper::map) }
            .getOrDefault(emptyList())
    }

    private fun emitEffect(effect: TrainerProfileScheduleEffect) {
        screenModelScope.launch {
            _effect.emit(effect)
        }
    }
}