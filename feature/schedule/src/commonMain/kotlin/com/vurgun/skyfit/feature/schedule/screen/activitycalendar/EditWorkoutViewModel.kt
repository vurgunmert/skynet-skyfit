package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutCategory
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutType
import com.vurgun.skyfit.core.data.schedule.domain.repository.UserCalendarRepository
import com.vurgun.skyfit.core.data.utility.*
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

sealed class EditWorkoutUiState {
    object Loading : EditWorkoutUiState()
    data class Error(val message: String?) : EditWorkoutUiState()
    data class Content(
        val workoutId: Int? = null,
        val workoutName: String? = null,
        val initialDate: LocalDate? = null,
        val startDate: String? = null,
        val startTime: String? = null,
        val endTime: String? = null,
    ) : EditWorkoutUiState()
}

sealed class EditWorkoutAction {
    object OnClickBack : EditWorkoutAction()
    object AddWorkout : EditWorkoutAction()
    data class OnUpdateStartTime(val time: String) : EditWorkoutAction()
    data class OnUpdateEndTime(val time: String) : EditWorkoutAction()
    data class OnSelectDate(val date: LocalDate? = null) : EditWorkoutAction()
}

sealed class EditWorkoutEffect {
    object NavigateToBack : EditWorkoutEffect()
}

class EditWorkoutViewModel(
    private val calendarRepository: UserCalendarRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<EditWorkoutUiState>(EditWorkoutUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<EditWorkoutEffect>()
    val effect = _effect as SharedFlow<EditWorkoutEffect>

    fun onAction(action: EditWorkoutAction) {
        when (action) {
            EditWorkoutAction.AddWorkout -> submitWorkout()
            EditWorkoutAction.OnClickBack ->
                _effect.emitIn(screenModelScope, EditWorkoutEffect.NavigateToBack)

            is EditWorkoutAction.OnSelectDate -> {

            }

            is EditWorkoutAction.OnUpdateEndTime -> updateEndTime(action.time)
            is EditWorkoutAction.OnUpdateStartTime -> updateStartTime(action.time)
        }
    }

    fun loadData(initialDate: LocalDate, workoutType: WorkoutType?, category: WorkoutCategory?) {
        val startTime = LocalDateTime.now()
        val endTime = startTime.roundUpToNextSlot()

        _uiState.update(
            EditWorkoutUiState.Content(
                initialDate = initialDate,
                workoutId = workoutType?.id,
                workoutName = workoutType?.name,
                startDate = initialDate.formatToServerDate(),
                startTime = startTime.formatToHHMMTime(),
                endTime = endTime.formatToHHMMTime(),
            )
        )
    }

    private fun updateStartTime(time: String) {
        val content = (uiState.value as? EditWorkoutUiState.Content) ?: return
        _uiState.update(content.copy(startTime = time))
    }

    private fun updateEndTime(time: String) {
        val content = (uiState.value as? EditWorkoutUiState.Content) ?: return
        _uiState.update(content.copy(endTime = time))
    }

    private fun submitWorkout() {
        val content = (uiState.value as? EditWorkoutUiState.Content) ?: return
        val workoutName = content.workoutName ?: return


        screenModelScope.launch {

            calendarRepository.addCalendarEvents(
                workoutId = content.workoutId,
                eventName = workoutName,
                startDate = TODO(),
                endDate = TODO()
            )

        }
    }
}