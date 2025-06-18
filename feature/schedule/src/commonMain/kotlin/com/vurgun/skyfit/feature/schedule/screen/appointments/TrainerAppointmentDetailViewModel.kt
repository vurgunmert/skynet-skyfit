package com.vurgun.skyfit.feature.schedule.screen.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonParticipant
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.ScheduledLessonDetail
import com.vurgun.skyfit.core.data.v1.domain.lesson.repository.LessonRepository
import com.vurgun.skyfit.core.data.v1.domain.trainer.repository.TrainerRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
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
    data class EvaluateParticipant(val participant: LessonParticipant, val evaluation: String) :
        TrainerAppointmentDetailAction
}

sealed interface TrainerAppointmentDetailEffect {
    data object NavigateToBack : TrainerAppointmentDetailEffect
}

class TrainerAppointmentDetailViewModel(
    private val trainerRepository: TrainerRepository,
    private val lessonRepository: LessonRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<TrainerAppointmentDetailUiState>(TrainerAppointmentDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = MutableSharedFlow<TrainerAppointmentDetailEffect>()
    val effect: SharedFlow<TrainerAppointmentDetailEffect> = _effect

    private var activeLessonId: Int? = null

    fun onAction(action: TrainerAppointmentDetailAction) {
        when (action) {
            TrainerAppointmentDetailAction.NavigateToBack ->
                _effect.emitIn(screenModelScope, TrainerAppointmentDetailEffect.NavigateToBack)

            is TrainerAppointmentDetailAction.EvaluateParticipant ->
                evaluateParticipant(action.participant, action.evaluation)
        }
    }

    fun loadAppointment(lessonId: Int) {
        activeLessonId = lessonId

        screenModelScope.launch {
            try {
                val lesson = lessonRepository.getScheduledLessonDetail(lessonId).getOrThrow()

                val participants = lessonRepository.getLessonParticipants(lessonId).getOrDefault(emptyList())

                _uiState.update(TrainerAppointmentDetailUiState.Content(lesson, participants))
            } catch (e: Exception) {
                _uiState.update(TrainerAppointmentDetailUiState.Error(e.message ?: "Ders getirme hatasi"))
            }
        }
    }

    fun refresh() {
        loadAppointment(activeLessonId ?: return)
    }

    private fun evaluateParticipant(
        participant: LessonParticipant,
        evaluation: String
    ) {
        val currentState = uiState.value as? TrainerAppointmentDetailUiState.Content ?: return

        screenModelScope.launch {

            runCatching {
                val updatedParticipants = currentState.participants.map {
                    if (it.lpId == participant.lpId) {
                        it.copy(trainerEvaluation = evaluation)
                    } else {
                        it
                    }
                }

                trainerRepository.evaluateParticipants(participant.lpId, updatedParticipants)

                _uiState.update(currentState.copy(participants = updatedParticipants))

            }.onFailure { error ->
                _uiState.update(
                    TrainerAppointmentDetailUiState.Error(
                        error.message ?: "Katilimci degerlendirme hatasi"
                    )
                )
            }
        }
    }
}