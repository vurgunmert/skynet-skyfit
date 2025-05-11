package com.vurgun.skyfit.feature.schedule.screen.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.schedule.domain.model.LessonParticipant
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.schedule.domain.model.ScheduledLessonDetail
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface TrainerAppointmentDetailUiState {
    data object Loading : TrainerAppointmentDetailUiState
    data class Error(val message: String) : TrainerAppointmentDetailUiState
    data class Content(
        val lesson: ScheduledLessonDetail,
        val participants: List<LessonParticipant>
    ) : TrainerAppointmentDetailUiState
}

sealed interface TrainerAppointmentDetailAction {
    data object NavigateToBack : TrainerAppointmentDetailAction
    data class EvaluateParticipant(val participant: LessonParticipant, val evaluation: String) : TrainerAppointmentDetailAction
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
            TrainerAppointmentDetailAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, TrainerAppointmentDetailEffect.NavigateToBack)

            is TrainerAppointmentDetailAction.EvaluateParticipant ->
                evaluateParticipant(action.participant, action.evaluation)
        }
    }

    fun loadAppointment(lessonId: Int) {
        screenModelScope.launch {
            try {
                val lesson = courseRepository.getScheduledLessonDetail(lessonId).getOrThrow()

                val participants = courseRepository.getLessonParticipants(lessonId).getOrDefault(emptyList())

                _uiState.value = TrainerAppointmentDetailUiState.Content(lesson, participants)
            } catch (e: Exception) {
                _uiState.value = TrainerAppointmentDetailUiState.Error(e.message ?: "Ders getirme hatasi")
            }
        }
    }

    private fun evaluateParticipant(
        participant: LessonParticipant,
        evaluation: String
    ) {
        val currentState = _uiState.value as? TrainerAppointmentDetailUiState.Content ?: return

        val updatedParticipants = currentState.participants.map {
            if (it.lpId == participant.lpId) {
                it.copy(trainerEvaluation = evaluation)
            } else {
                it
            }
        }

        screenModelScope.launch {
            try {
                courseRepository.evaluateParticipants(participant.lpId, updatedParticipants)
                _uiState.value = currentState.copy(participants = updatedParticipants)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}