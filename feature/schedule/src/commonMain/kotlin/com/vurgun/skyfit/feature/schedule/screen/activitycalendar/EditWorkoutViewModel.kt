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
        val workoutName: String = "Workout",
        val initialDate: LocalDate? = null,
        val startDate: String? = null,
        val startTime: String? = null,
        val endTime: String? = null,
    ) : EditWorkoutUiState()
}

sealed class EditWorkoutAction {
    data object OnClickBack : EditWorkoutAction()
    data object OnClickSubmit : EditWorkoutAction()
    data object OnClickEditTime : EditWorkoutAction()
    data class TimeChanged(val hour: Int, val minute: Int) : EditWorkoutAction()
    data class OnSelectDate(val date: LocalDate? = null) : EditWorkoutAction()
}

sealed class EditWorkoutEffect {
    object NavigateToBack : EditWorkoutEffect()
    data class NavigateToEditTime(
        val date: LocalDate,
        val workoutId: Int?,
        val name: String
    ): EditWorkoutEffect()
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
            EditWorkoutAction.OnClickSubmit -> submitWorkout()
            EditWorkoutAction.OnClickBack ->
                _effect.emitIn(screenModelScope, EditWorkoutEffect.NavigateToBack)

            is EditWorkoutAction.OnSelectDate -> {

            }

            is EditWorkoutAction.TimeChanged -> {

            }

            is EditWorkoutAction.OnClickEditTime -> navigateToEditTime()
        }
    }

    fun navigateToEditTime() {
        val content = (uiState.value as? EditWorkoutUiState.Content) ?: return
        val date = content.initialDate ?: LocalDate.now()

        _effect.emitIn(screenModelScope, EditWorkoutEffect.NavigateToEditTime(
            date = date,
            workoutId = content.workoutId,
            name = content.workoutName
        ))
    }

    fun loadData(initialDate: LocalDate, workoutType: WorkoutType?, category: WorkoutCategory?) {
        val startTime = LocalDateTime.now().roundUpToNextSlot()
        val endTime = startTime.roundUpToNextSlot()

        _uiState.update(
            EditWorkoutUiState.Content(
                initialDate = initialDate,
                workoutId = workoutType?.id,
                workoutName = workoutType?.let { "${workoutType.emojiId} ${workoutType.name}" } ?: "WORKOUT",
                startDate = initialDate.toTurkishLongDate(),
                startTime = startTime.formatToHHMMTime(),
                endTime = endTime.formatToHHMMTime(),
            )
        )
    }

    fun submitWorkout() {
        val content = (uiState.value as? EditWorkoutUiState.Content) ?: return
        val workoutName = content.workoutName ?: return


        content.initialDate
        content.startTime
        content.endTime

        screenModelScope.launch {
            // TODO:

            calendarRepository.addCalendarEvents(
                workoutId = content.workoutId,
                eventName = workoutName,
                startDate = TODO(),
                endDate = TODO()
            )

        }
    }
}