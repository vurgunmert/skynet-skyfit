package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.schedule.domain.repository.UserCalendarRepository
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.UiStateDelegate
import com.vurgun.skyfit.core.data.utility.emitIn
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

sealed class EditWorkoutTimeUiState {
    object Loading : EditWorkoutTimeUiState()
    data class Error(val message: String?) : EditWorkoutTimeUiState()
    data class Content(
        val name: String,
        val startDate: LocalDate,
        val startTime: String,
        val endTime: String,
        val workoutEventId: Int?,
    ) : EditWorkoutTimeUiState()
}

sealed class EditWorkoutTimeAction {
    data object OnClickBack : EditWorkoutTimeAction()
    data object OnClickContinue : EditWorkoutTimeAction()
    data class OnClickDuration(val duration: String) : EditWorkoutTimeAction()
    data class OnUpdateStartTime(val time: String) : EditWorkoutTimeAction()
    data class OnUpdateEndTime(val time: String) : EditWorkoutTimeAction()
}

sealed class EditWorkoutTimeEffect {
    object NavigateToBack : EditWorkoutTimeEffect()
    data object NavigateToEditWorkoutConfirm : EditWorkoutTimeEffect()
}

class EditWorkoutTimeViewModel(
    private val calendarRepository: UserCalendarRepository
) : ScreenModel {

    private val _uiState = UiStateDelegate<EditWorkoutTimeUiState>(EditWorkoutTimeUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<EditWorkoutTimeEffect>()
    val effect = _effect as SharedFlow<EditWorkoutTimeEffect>

    fun onAction(action: EditWorkoutTimeAction) {
        when (action) {
            EditWorkoutTimeAction.OnClickBack ->
                _effect.emitIn(screenModelScope, EditWorkoutTimeEffect.NavigateToBack)

            is EditWorkoutTimeAction.OnClickDuration -> {
                handleSelectDuration(action.duration)
            }

            is EditWorkoutTimeAction.OnUpdateEndTime ->
                updateEndTime(action.time)

            is EditWorkoutTimeAction.OnUpdateStartTime ->
                updateStartTime(action.time)

            EditWorkoutTimeAction.OnClickContinue ->
                _effect.emitIn(screenModelScope, EditWorkoutTimeEffect.NavigateToEditWorkoutConfirm)
        }
    }

    fun updateStartTime(time: String) {
        val content = (uiState.value as? EditWorkoutTimeUiState.Content) ?: return
        _uiState.update(content.copy(startTime = time))
    }

    fun updateEndTime(time: String) {
        val content = (uiState.value as? EditWorkoutTimeUiState.Content) ?: return
        _uiState.update(content.copy(endTime = time))
    }

    fun loadData(name: String, date: LocalDate, workoutEventId: Int?) {

        val content = (uiState.value as? EditWorkoutTimeUiState.Content)
            ?: EditWorkoutTimeUiState.Content(
            name = name,
            startDate = date,
            startTime = "09:30",
            endTime =  "10:00",
            workoutEventId = workoutEventId
        )

        _uiState.update(content)
    }

    private fun handleSelectDuration(duration: String) {
        val content = (uiState.value as? EditWorkoutTimeUiState.Content) ?: return

        val startTimeParts = content.startTime?.split(":")?.mapNotNull { it.toIntOrNull() }
        val durationParts = duration.split(":").mapNotNull { it.toIntOrNull() }

        if (startTimeParts?.size == 2 && durationParts.size == 3) {
            val startHour = startTimeParts[0]
            val startMinute = startTimeParts[1]

            val durationHour = durationParts[0]
            val durationMinute = durationParts[1]
            val durationSecond = durationParts[2]

            // Convert everything to total minutes
            val startTotalMinutes = startHour * 60 + startMinute
            val durationTotalMinutes =
                durationHour * 60 + durationMinute + if (durationSecond >= 30) 1 else 0

            val endTotalMinutes = startTotalMinutes + durationTotalMinutes
            val endHour = (endTotalMinutes / 60) % 24
            val endMinute = endTotalMinutes % 60

            val endTime =
                "${endHour.toString().padStart(2, '0')}:${endMinute.toString().padStart(2, '0')}"

            _uiState.update(content.copy(endTime = endTime))
        }
    }

    fun submitWorkout() {
        val content = (uiState.value as? EditWorkoutTimeUiState.Content) ?: return

        screenModelScope.launch {

            fun normalizeTimeToHHMMSS(time: String): String {
                val parts = time.split(":").map { it.padStart(2, '0') }

                return when (parts.size) {
                    1 -> "${parts[0]}:00:00"
                    2 -> "${parts[0]}:${parts[1]}:00"
                    3 -> "${parts[0]}:${parts[1]}:${parts[2]}"
                    else -> "00:00:00"
                }
            }

            fun combineDateTimeString(date: LocalDate, time: String): String {
                return "${date.toString()} ${normalizeTimeToHHMMSS(time)}"
            }

            val startDateTime = combineDateTimeString(content.startDate, content.startTime.orEmpty())
            val endDateTime = combineDateTimeString(content.startDate, content.endTime.orEmpty())

            runCatching {
                calendarRepository.addCalendarEvents(
                    workoutId = content.workoutEventId,
                    eventName = content.name,
                    startDate = startDateTime,
                    endDate = endDateTime
                )
            }.onFailure { error ->

            }
        }
    }
}
