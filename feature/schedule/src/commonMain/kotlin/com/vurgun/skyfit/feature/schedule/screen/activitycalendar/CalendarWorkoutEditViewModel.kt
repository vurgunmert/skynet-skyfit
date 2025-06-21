package com.vurgun.skyfit.feature.schedule.screen.activitycalendar

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.utility.*
import com.vurgun.skyfit.core.data.v1.data.workout.WorkoutTypeUiData
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.time.Duration.Companion.minutes

sealed class CalendarWorkoutEditUiState {
    object Loading : CalendarWorkoutEditUiState()
    data class Error(val message: String?) : CalendarWorkoutEditUiState()
    data class Content(
        val workoutId: Int? = null,
        val workoutName: String? = null,
        val initialDate: LocalDate,
        val startDateTime: LocalDateTime,
        val endDateTime: LocalDateTime,
        val formattedDate: String
    ) : CalendarWorkoutEditUiState()
}

sealed class EditWorkoutAction {
    data object OnClickBack : EditWorkoutAction()
    data object OnClickEditTime : EditWorkoutAction()
    data class OnUpdateName(val name: String) : EditWorkoutAction()
    data class OnTimeChanged(val time: String) : EditWorkoutAction()
    data class OnSelectDate(val date: LocalDate? = null) : EditWorkoutAction()
}

sealed class EditWorkoutEffect {
    object NavigateToBack : EditWorkoutEffect()
    data class NavigateToEditTime(
        val date: LocalDateTime,
        val workoutId: Int?,
        val name: String
    ) : EditWorkoutEffect()
}

class CalendarWorkoutEditViewModel : ScreenModel {

    private val _uiState = UiStateDelegate<CalendarWorkoutEditUiState>(CalendarWorkoutEditUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _effect = SingleSharedFlow<EditWorkoutEffect>()
    val effect = _effect as SharedFlow<EditWorkoutEffect>

    fun onAction(action: EditWorkoutAction) {
        when (action) {
            EditWorkoutAction.OnClickBack ->
                _effect.emitIn(screenModelScope, EditWorkoutEffect.NavigateToBack)

            is EditWorkoutAction.OnSelectDate -> {

            }

            is EditWorkoutAction.OnTimeChanged -> {
                updateTime(action.time)
            }

            is EditWorkoutAction.OnClickEditTime -> {
                navigateToEditTime()
            }
            is EditWorkoutAction.OnUpdateName -> {
                updateName(action.name)
            }
        }
    }

    private fun updateName(value: String) {
        val content = (uiState.value as? CalendarWorkoutEditUiState.Content) ?: return
        _uiState.update(content.copy(workoutName = value))
    }

    private fun updateTime(time: String) {
        val content = (uiState.value as? CalendarWorkoutEditUiState.Content) ?: return

        val startTime = time.parseServerToHHMMTime()
        val startDateTime = LocalDateTime(content.startDateTime.date, startTime)
        val endDateTime = startDateTime.plusDuration(30.minutes)

        _uiState.update(
            content.copy(
                startDateTime = startDateTime,
                endDateTime = endDateTime
            )
        )
    }

    fun navigateToEditTime() {
        val content = (uiState.value as? CalendarWorkoutEditUiState.Content) ?: return
        val workoutName = content.workoutName.takeUnless { it.isNullOrEmpty() } ?: return

        _effect.emitIn(
            screenModelScope, EditWorkoutEffect.NavigateToEditTime(
                date = content.startDateTime,
                workoutId = content.workoutId,
                name = workoutName
            )
        )
    }

    fun loadData(initialDate: LocalDate, workoutType: WorkoutTypeUiData?) {
        if (uiState.value is CalendarWorkoutEditUiState.Content) return

        val startDate = if (initialDate <= LocalDate.now()) {
            LocalDateTime.now()
        } else {
            LocalDateTime(initialDate, LocalTime(9, 0))
        }
        val startDateTime = startDate.roundUpToNextSlot()
        val endDateTime = startDateTime.plusDuration(30.minutes)

        _uiState.update(
            CalendarWorkoutEditUiState.Content(
                initialDate = initialDate,
                workoutId = workoutType?.id,
                workoutName = workoutType?.let { "${workoutType.emojiId} ${workoutType.name}" }.orEmpty(),
                startDateTime = startDateTime,
                endDateTime = endDateTime,
                formattedDate = startDate.date.toTurkishLongDate()
            )
        )
    }

}