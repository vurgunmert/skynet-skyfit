package com.vurgun.skyfit.feature.calendar.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.data.courses.domain.model.ScheduledLessonDetail
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface TrainerAppointmentDetailUiState {
    data object Loading : TrainerAppointmentDetailUiState
    data class Error(val message: String) : TrainerAppointmentDetailUiState
    data class Content(val lesson: ScheduledLessonDetail) : TrainerAppointmentDetailUiState
}

sealed interface TrainerAppointmentDetailAction {
    data object NavigateToBack : TrainerAppointmentDetailAction
}

sealed interface TrainerAppointmentDetailEffect {
    data object NavigateToBack : TrainerAppointmentDetailEffect
}

class TrainerAppointmentDetailViewModel(
    private val courseRepository: CourseRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow<TrainerAppointmentDetailUiState>(TrainerAppointmentDetailUiState.Loading)
    val uiState: StateFlow<TrainerAppointmentDetailUiState> get() = _uiState

    private val _effect = MutableSharedFlow<TrainerAppointmentDetailEffect>()
    val effect: SharedFlow<TrainerAppointmentDetailEffect> = _effect

    fun onAction(action: TrainerAppointmentDetailAction) {
        when (action) {
            TrainerAppointmentDetailAction.NavigateToBack -> emitEffect(TrainerAppointmentDetailEffect.NavigateToBack)
        }
    }

    fun loadAppointment(lessonId: Int) {
        screenModelScope.launch {
            try {
                val lesson = courseRepository.getScheduledLessonDetail(lessonId).getOrThrow()
                _uiState.value = TrainerAppointmentDetailUiState.Content(lesson)
            } catch (e: Exception) {
                _uiState.value = TrainerAppointmentDetailUiState.Error(e.message ?: "Ders getirme hatasi")
            }
        }
    }

    private fun loadParticipants() {
        //            // **Simulate fetching appointment details**
//            _appointment.value =
//            // **Simulate fetching participant list**
//            _participants.value = listOf(
//                ParticipantViewData("1", "Selin Kaya"),
//                ParticipantViewData("2", "Ali Çelik"),
//                ParticipantViewData("3", "Deniz Şahin"),
//                ParticipantViewData("4", "Eren Yıldız")
//            )
    }

    private fun emitEffect(effect: TrainerAppointmentDetailEffect) {
        screenModelScope.launch {
            _effect.emit(effect)
        }
    }
}

data class ParticipantViewData(
    val id: String,
    val name: String,
    var isPresent: Boolean = false
)
