package com.vurgun.skyfit.feature.schedule.screen.activitycalendar


import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.schedule.domain.repository.UserCalendarRepository
import com.vurgun.skyfit.core.data.utility.*
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

sealed class CalendarWorkoutEditConfirmUiState {
    object Loading : CalendarWorkoutEditConfirmUiState()
    data class Error(val message: String?) : CalendarWorkoutEditConfirmUiState()
    data class Content(
        val startDateTime: LocalDateTime,
        val endDateTime: LocalDateTime,
        val workoutName: String,
        val workoutId: Int? = null,
        val durationHour: Int,
        val durationMinute: Int,
    ) : CalendarWorkoutEditConfirmUiState()
}

sealed class CalendarWorkoutEditConfirmAction {
    data object OnClickBack : CalendarWorkoutEditConfirmAction()
    data object OnClickAdd : CalendarWorkoutEditConfirmAction()
}

sealed class CalendarWorkoutEditConfirmEffect {
    object NavigateToBack : CalendarWorkoutEditConfirmEffect()
    object NavigateToConfirmed : CalendarWorkoutEditConfirmEffect()
}

class CalendarWorkoutEditConfirmViewModel(
    private val calendarRepository: UserCalendarRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<CalendarWorkoutEditConfirmUiState>(CalendarWorkoutEditConfirmUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<CalendarWorkoutEditConfirmEffect>()
    val effect = _effect as SharedFlow<CalendarWorkoutEditConfirmEffect>

    fun onAction(action: CalendarWorkoutEditConfirmAction) {
        when (action) {
            CalendarWorkoutEditConfirmAction.OnClickBack ->
                _effect.emitIn(screenModelScope, CalendarWorkoutEditConfirmEffect.NavigateToBack)

            CalendarWorkoutEditConfirmAction.OnClickAdd ->
                submitWorkout()
        }
    }

    fun loadData(
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        workoutName: String,
        workoutId: Int? = null,
    ) {
        val duration = durationBetween(startDateTime, endDateTime)
        val (hours, minutes) = duration.toHourMinute()

        _uiState.update(
            CalendarWorkoutEditConfirmUiState.Content(
                startDateTime = startDateTime,
                endDateTime = endDateTime,
                workoutName = workoutName,
                workoutId = workoutId,
                durationHour = hours,
                durationMinute = minutes
            )
        )
    }

    fun submitWorkout() {
        val content = (uiState.value as? CalendarWorkoutEditConfirmUiState.Content) ?: return

        screenModelScope.launch {
            _uiState.update(CalendarWorkoutEditConfirmUiState.Loading)

            runCatching {
                calendarRepository.addCalendarEvents(
                    workoutId = content.workoutId,
                    eventName = content.workoutName,
                    startDate = content.startDateTime.toString(),
                    endDate = content.endDateTime.toString()
                )

                _effect.emitIn(screenModelScope, CalendarWorkoutEditConfirmEffect.NavigateToConfirmed)
            }.onFailure { error ->
                _uiState.update(CalendarWorkoutEditConfirmUiState.Error(error.message))
            }
        }
    }
}


