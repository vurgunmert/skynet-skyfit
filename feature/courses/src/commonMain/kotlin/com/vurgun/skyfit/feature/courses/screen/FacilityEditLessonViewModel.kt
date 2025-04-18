package com.vurgun.skyfit.feature.courses.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.model.CalendarRecurrence
import com.vurgun.skyfit.data.core.domain.model.FacilityDetail
import com.vurgun.skyfit.data.core.utility.now
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.model.LessonCreationInfo
import com.vurgun.skyfit.data.courses.domain.model.LessonUpdateInfo
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.settings.domain.repository.MemberRepository
import com.vurgun.skyfit.data.settings.domain.repository.TrainerRepository
import com.vurgun.skyfit.feature.courses.component.SelectableTrainerMenuItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

data class FacilityEditLessonViewState(
    val lessonId: Int? = null,
    val iconId: Int = 1,
    val title: String? = null,
    val trainerNote: String? = null,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val startTime: String = "08:00",
    val endTime: String = "08:30",
    val recurrence: CalendarRecurrence = CalendarRecurrence.Never,
    val isAppointmentMandatory: Boolean = true,
    val cost: Int = 0,
    val capacity: Int = 5,
    val cancelDurationHour: Int = 24,
    val showCancelDialog: Boolean = false,
    val isSaveButtonEnabled: Boolean = false,
) {
    val isEditing: Boolean = lessonId != null
}

class FacilityEditLessonViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val trainerRepository: TrainerRepository,
    private val memberRepository: MemberRepository
) : ViewModel() {

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility!")

    val gymId: Int
        get() = facilityUser.gymId

    private var initialState: FacilityEditLessonViewState = FacilityEditLessonViewState()

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<FacilityEditLessonViewState> = _uiState

    private val _trainers = MutableStateFlow<List<SelectableTrainerMenuItemModel>>(emptyList())
    val trainers: StateFlow<List<SelectableTrainerMenuItemModel>> = _trainers

    var createdLessonInfo: LessonCreationInfo? = null
    var updatedLessonInfo: LessonUpdateInfo? = null

    init {
        viewModelScope.launch {
            _trainers.value = trainerRepository.getFacilityTrainers(gymId)
                .getOrThrow()
                .map {
                    SelectableTrainerMenuItemModel(it.trainerId, it.fullName, it.profileImageUrl)
                }
        }
    }

    fun loadLesson(lesson: Lesson? = null) {
        if (lesson != null) {
            val cancelPeriod = getLastCancelDurationHours(lesson.startDateTime, lesson.lastCancelableAt)

            initialState = FacilityEditLessonViewState(
                lessonId = lesson.lessonId,
                iconId = lesson.iconId,
                title = lesson.title,
                trainerNote = lesson.trainerNote,
                startDate = lesson.startDate,
                endDate = lesson.endDate,
                startTime = lesson.startTime.toString(),
                endTime = lesson.endTime.toString(),
                cost = lesson.price,
                capacity = lesson.capacityRatio.substringAfter("/").toIntOrNull() ?: 1,
                cancelDurationHour = cancelPeriod.toInt(),
            )
        } else {
            initialState = FacilityEditLessonViewState()
        }
        _uiState.value = initialState
    }

    // Compare current state with the initial state, enable Save if changed
    private fun checkIfModified() {
        _uiState.update {
            it.copy(isSaveButtonEnabled = it != initialState) //TODO: Check mandatory fields
        }
    }

    fun updateIcon(icon: Int) {
        _uiState.update { it.copy(iconId = icon) }
        checkIfModified()
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
        checkIfModified()
    }

    fun updateSelectedTrainer(trainer: SelectableTrainerMenuItemModel) {
        _trainers.value = _trainers.value.map {
            it.copy(selected = it.id == trainer.id)
        }
    }

    fun updateTrainerNote(note: String) {
        _uiState.update { it.copy(trainerNote = note) }
        checkIfModified()
    }

    fun updateStartDate(date: LocalDate) {
        _uiState.update { it.copy(startDate = date) }
        checkIfModified()
    }

    fun updateEndDate(date: LocalDate) {
        _uiState.update { it.copy(endDate = date) }
        checkIfModified()
    }

    fun updateStartTime(time: String) {
        _uiState.update { it.copy(startTime = time) }
        checkIfModified()
    }

    fun updateEndTime(time: String) {
        _uiState.update { it.copy(endTime = time) }
        checkIfModified()
    }

    fun updateRecurrence(option: CalendarRecurrence) {
        _uiState.update { it.copy(recurrence = option) }
        checkIfModified()
    }

    fun updateCapacity(capacity: Int) {
        _uiState.update { it.copy(capacity = capacity) }
        checkIfModified()
    }

    fun updateCancelDurationHour(value: Int) {
        _uiState.update { it.copy(cancelDurationHour = value) }
        checkIfModified()
    }

    fun updateAppointmentMandatory(mandatory: Boolean) {
        _uiState.update { it.copy(isAppointmentMandatory = mandatory) }
        checkIfModified()
    }

    fun updateCost(amount: Int) {
        _uiState.update { it.copy(cost = amount) }
        checkIfModified()
    }

    fun updateShowCancelDialog(show: Boolean) {
        _uiState.update { it.copy(showCancelDialog = show) }
    }

    fun submitLesson() {
        viewModelScope.launch {
            uiState.value.let {
                if (it.isEditing) {
                    updateLesson(it)
                } else {
                    createLesson(it)
                }
            }
        }
    }

    private suspend fun createLesson(state: FacilityEditLessonViewState) {
        val trainerId = trainers.value.firstOrNull { it.selected }?.id ?: error("NO TRAINER ID")

        val creationInfo = state.let {
            val repetition = (it.recurrence as? CalendarRecurrence.SomeDays)?.days?.mapNotNull { dayOfWeek ->
                when (dayOfWeek) {
                    DayOfWeek.MONDAY -> 2
                    DayOfWeek.TUESDAY -> 3
                    DayOfWeek.WEDNESDAY -> 4
                    DayOfWeek.THURSDAY -> 5
                    DayOfWeek.FRIDAY -> 6
                    DayOfWeek.SATURDAY -> 7
                    DayOfWeek.SUNDAY -> 1
                    else -> null
                }
            }.orEmpty()

            LessonCreationInfo(
                gymId = gymId,
                iconId = it.iconId,
                title = it.title.toString(),
                trainerNote = it.trainerNote,
                trainerId = trainerId,
                startDateTime = LocalDateTime(it.startDate, LocalTime.parse(it.startTime)),
                endDateTime = LocalDateTime(it.endDate, LocalTime.parse(it.endTime)),
                repetitionType = it.recurrence.id,
                repetition = repetition,
                quota = it.capacity,
                lastCancelableHoursBefore = it.cancelDurationHour,
                isRequiredAppointment = it.isAppointmentMandatory,
                price = it.cost,
            )
        }

        courseRepository.createLesson(info = creationInfo).fold(
            onSuccess = {
                createdLessonInfo = creationInfo
                //TODO: NAVIGATE TO CREATED BY DISMISSING THIS ONE
            },
            onFailure = { error ->
                println("❌ CANNOT CREATE LESSON: $error")
            }
        )
    }

    private suspend fun updateLesson(state: FacilityEditLessonViewState) {
        val trainerId = trainers.value.first { it.selected }.id

        val updateInfo = state.let {
            LessonUpdateInfo(
                lessonId = it.lessonId!!,
                iconId = it.iconId,
                trainerNote = it.trainerNote,
                trainerId = trainerId,
                startDateTime = LocalDateTime(it.startDate, LocalTime.parse(it.startTime)),
                endDateTime = LocalDateTime(it.endDate, LocalTime.parse(it.endTime)),
                quota = it.capacity,
                lastCancelableHoursBefore = it.cancelDurationHour,
                isRequiredAppointment = it.isAppointmentMandatory,
                price = it.cost,
            )
        }

        courseRepository.updateLesson(info = updateInfo).fold(
            onSuccess = {
                updatedLessonInfo = updateInfo
                //TODO: NAVIGATE TO CREATED BY DISMISSING THIS ONE
            },
            onFailure = { error ->
                println("❌ CANNOT UPDATE LESSON: $error")
            }
        )
    }

    private fun getLastCancelDurationHours(
        startDateTime: LocalDateTime,
        lastCancelableAt: Instant,
    ): Long {
        val utc = TimeZone.UTC
        val startInstant = startDateTime.toInstant(utc)
        val duration = startInstant - lastCancelableAt
        val hours = duration.inWholeHours
        return hours
    }
}