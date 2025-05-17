package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.plusDuration
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.datetime.LocalDateTime
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

sealed class EditWorkoutDurationUiState {
    object Loading : EditWorkoutDurationUiState()
    data class Error(val message: String?) : EditWorkoutDurationUiState()
    data class Content(
        val name: String,
        val startDateTime: LocalDateTime,
        val endDateTime: LocalDateTime,
        val workoutEventId: Int?,
        val durationHours: Int = 0,
        val durationMinutes: Int = 30,
    ) : EditWorkoutDurationUiState()
}

sealed class EditWorkoutTimeAction {
    data object OnClickBack : EditWorkoutTimeAction()
    data object OnClickContinue : EditWorkoutTimeAction()
    data class OnClickStoredDuration(val duration: String) : EditWorkoutTimeAction()
    data class OnUpdateDuration(val hour: String, val minute: String) : EditWorkoutTimeAction()
}

sealed class EditWorkoutTimeEffect {
    object NavigateToBack : EditWorkoutTimeEffect()
    data class NavigateToEditWorkoutConfirm(
        val startDateTime: LocalDateTime,
        val endDateTime: LocalDateTime,
        val workoutName: String,
        val workoutId: Int? = null,
    ) : EditWorkoutTimeEffect()
}

class CalendarWorkoutEditDurationViewModel : ScreenModel {

    private val _uiState = UiStateDelegate<EditWorkoutDurationUiState>(EditWorkoutDurationUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<EditWorkoutTimeEffect>()
    val effect = _effect as SharedFlow<EditWorkoutTimeEffect>

    fun onAction(action: EditWorkoutTimeAction) {
        when (action) {
            EditWorkoutTimeAction.OnClickBack ->
                _effect.emitIn(screenModelScope, EditWorkoutTimeEffect.NavigateToBack)

            is EditWorkoutTimeAction.OnClickStoredDuration -> {
                handleSelectDuration(action.duration)
            }

            is EditWorkoutTimeAction.OnUpdateDuration ->
                updateDuration(action.hour, action.minute)

            EditWorkoutTimeAction.OnClickContinue ->
                handleContinue()
        }
    }

    fun loadData(name: String, startDateTime: LocalDateTime, workoutEventId: Int?) {
        if (uiState.value is EditWorkoutDurationUiState.Content) return

        _uiState.update(
            EditWorkoutDurationUiState.Content(
                name = name,
                startDateTime = startDateTime,
                endDateTime = startDateTime.plusDuration(30.minutes),
                workoutEventId = workoutEventId
            )
        )
    }

    private fun handleContinue() {
        val content = (uiState.value as? EditWorkoutDurationUiState.Content) ?: return

        _effect.emitIn(
            screenModelScope,
            EditWorkoutTimeEffect.NavigateToEditWorkoutConfirm(
                startDateTime = content.startDateTime,
                endDateTime = content.endDateTime,
                workoutName = content.name,
                workoutId = content.workoutEventId
            )
        )
    }

    fun updateDuration(hour: String, minute: String) {
        val content = (uiState.value as? EditWorkoutDurationUiState.Content) ?: return
        val hourCount = hour.toIntOrNull() ?: return
        val minuteCount = minute.toIntOrNull() ?: return
        val endDateTime = content.startDateTime
            .plusDuration(hourCount.hours)
            .plusDuration(minuteCount.minutes)
        _uiState.update(
            content.copy(
                endDateTime = endDateTime,
                durationHours = hourCount,
                durationMinutes = minuteCount
            )
        )
    }

    fun handleSelectDuration(duration: String) {
        val parts = duration.split(":").mapNotNull { it.toIntOrNull() }
        if (parts.size < 2) return

        val hour = parts[0].toString().padStart(2, '0')
        val minute = parts[1].toString().padStart(2, '0')

        updateDuration(hour, minute)
    }
}
