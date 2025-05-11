package com.vurgun.skyfit.feature.schedule.screen.lessons

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.CalendarRecurrence
import com.vurgun.skyfit.core.data.domain.model.CalendarRecurrenceType
import com.vurgun.skyfit.core.data.domain.model.FacilityDetail
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.core.data.utility.emitIn
import com.vurgun.skyfit.core.data.utility.formatToSlashedDate
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.ui.components.event.AppointmentCardViewData
import com.vurgun.skyfit.core.data.schedule.domain.model.Lesson
import com.vurgun.skyfit.core.data.schedule.domain.model.LessonCreationInfo
import com.vurgun.skyfit.core.data.schedule.domain.model.LessonUpdateInfo
import com.vurgun.skyfit.core.data.schedule.domain.repository.CourseRepository
import com.vurgun.skyfit.core.data.domain.model.Member
import com.vurgun.skyfit.core.data.domain.repository.MemberRepository
import com.vurgun.skyfit.core.data.domain.repository.TrainerRepository
import com.vurgun.skyfit.core.ui.components.schedule.SelectableTrainerMenuItemModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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
    val title: String = "",
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
    val trainer: SelectableTrainerMenuItemModel? = null,
    val trainers: List<SelectableTrainerMenuItemModel> = emptyList(),
    val participantMembers: List<ParticipatedMember> = emptyList()
) {
    val isEditing: Boolean = lessonId != null
}

data class ParticipatedMember(
    val added: Boolean,
    val member: Member
)

sealed class FacilityLessonEditAction {
    data object NavigateToBack : FacilityLessonEditAction()
    data object Save : FacilityLessonEditAction()
}

sealed class FacilityLessonEditEffect {
    data object NavigateToBack : FacilityLessonEditEffect()
    data object ShowNoTrainerError : FacilityLessonEditEffect()
    data class NavigateToCreateComplete(
        val lesson: AppointmentCardViewData
    ) : FacilityLessonEditEffect()

    data class NavigateToUpdateComplete(
        val lesson: AppointmentCardViewData
    ) : FacilityLessonEditEffect()
}

class FacilityLessonEditViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val trainerRepository: TrainerRepository,
    private val memberRepository: MemberRepository
) : ScreenModel {

    private val _effect = SingleSharedFlow<FacilityLessonEditEffect>()
    val effect: SharedFlow<FacilityLessonEditEffect> = _effect

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility!")

    val gymId: Int
        get() = facilityUser.gymId

    private var initialState: FacilityEditLessonViewState = FacilityEditLessonViewState()

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<FacilityEditLessonViewState> = _uiState

    fun onAction(action: FacilityLessonEditAction) {
        when (action) {
            FacilityLessonEditAction.NavigateToBack -> _effect.emitIn(screenModelScope, FacilityLessonEditEffect.NavigateToBack)
            FacilityLessonEditAction.Save -> submitLesson()
        }
    }

    fun loadLesson(lesson: Lesson? = null) {

        screenModelScope.launch {
            val facilityTrainers = trainerRepository.getFacilityTrainers(gymId)
                .getOrDefault(emptyList()) //TODO: FAIL TO USER
                .map {
                    SelectableTrainerMenuItemModel(it.trainerId, it.fullName, it.profileImageUrl)
                }

            if (facilityTrainers.isEmpty()) {
                _effect.emitIn(this, FacilityLessonEditEffect.ShowNoTrainerError)
                return@launch
            }

            if (lesson != null) {
                val cancelPeriod = getLastCancelDurationHours(lesson.startDateTime, lesson.lastCancelableAt)

                val deferredMembers = async { getParticipantMembers(lesson.lessonId) }

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
                    trainer = facilityTrainers.firstOrNull { it.id == lesson.trainerId },
                    trainers = facilityTrainers,
                    participantMembers = deferredMembers.await()
                )
            } else {
                initialState = FacilityEditLessonViewState(
                    trainer = facilityTrainers.firstOrNull(),
                    trainers = facilityTrainers
                )
            }
            _uiState.value = initialState
        }
    }

    private suspend fun getParticipantMembers(lessonId: Int): List<ParticipatedMember> {
        val participantUserIdList = courseRepository.getLessonParticipants(lessonId = lessonId).getOrDefault(emptyList()).map { it.userId }
        val memberList = memberRepository.getFacilityMembers(gymId = facilityUser.gymId).getOrDefault(emptyList())

        return memberList.map { member ->
            ParticipatedMember(added = member.userId in participantUserIdList, member)
        }
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
        _uiState.update { it.copy(trainer = trainer) }
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

    fun updateParticipants(participantMembers: List<ParticipatedMember>) {
        _uiState.update { it.copy(participantMembers = participantMembers) }
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

    private fun submitLesson() {
        screenModelScope.launch {
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
        val trainer = state.trainer ?: error("NO TRAINER!")

        val creationInfo = state.let {
            val repetition = (it.recurrence as? CalendarRecurrence.SomeDays)?.days?.mapNotNull { dayOfWeek ->
                when (dayOfWeek) {
                    DayOfWeek.SUNDAY -> 0
                    DayOfWeek.MONDAY -> 1
                    DayOfWeek.TUESDAY -> 2
                    DayOfWeek.WEDNESDAY -> 3
                    DayOfWeek.THURSDAY -> 4
                    DayOfWeek.FRIDAY -> 5
                    DayOfWeek.SATURDAY -> 6
                    else -> null
                }
            }.orEmpty()

            LessonCreationInfo(
                gymId = gymId,
                iconId = it.iconId,
                title = it.title.toString(),
                trainerNote = it.trainerNote,
                trainerId = trainer.id,
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
                val createdLesson = AppointmentCardViewData(
                    iconId = state.iconId,
                    title = state.title,
                    date = state.startDate.formatToSlashedDate(),
                    hours = "${state.startTime} - ${state.endTime}",
                    category = when (state.recurrence.type) {
                        CalendarRecurrenceType.NEVER -> "Tekrar yok"
                        CalendarRecurrenceType.DAILY -> "Gunluk"
                        CalendarRecurrenceType.SOMEDAYS -> "Belirli gunler"
                    },
                    location = facilityUser.gymName,
                    trainer = trainer.name,
                    capacity = "0/${state.capacity}",
                    cost = if (state.cost <= 0f) "Ucretsiz" else "${state.cost}",
                    note = state.trainerNote
                )

                _effect.emitIn(screenModelScope, FacilityLessonEditEffect.NavigateToCreateComplete(createdLesson))
            },
            onFailure = { error ->
                println("❌ CANNOT CREATE LESSON: $error")
            }
        )
    }

    private suspend fun updateLesson(state: FacilityEditLessonViewState) {
        val trainer = state.trainer ?: error("NO TRAINER!")

        val participantIds = state.participantMembers.filter { it.added }.map { it.member.userId }
        val newCapacity = if (participantIds.size > state.capacity) participantIds.size else state.capacity

        val updateInfo = LessonUpdateInfo(
            lessonId = state.lessonId!!,
            iconId = state.iconId,
            trainerNote = state.trainerNote,
            trainerId = trainer.id,
            startDateTime = LocalDateTime(state.startDate, LocalTime.parse(state.startTime)),
            endDateTime = LocalDateTime(state.endDate, LocalTime.parse(state.endTime)),
            quota = newCapacity,
            lastCancelableHoursBefore = state.cancelDurationHour,
            isRequiredAppointment = state.isAppointmentMandatory,
            price = state.cost,
            participantsIds = participantIds
        )

        courseRepository.updateLesson(info = updateInfo).fold(
            onSuccess = {

                val updatedLesson = AppointmentCardViewData(
                    iconId = state.iconId,
                    title = state.title,
                    date = state.startDate.formatToSlashedDate(),
                    hours = "${state.startTime} - ${state.endTime}",
                    category = when (state.recurrence.type) {
                        CalendarRecurrenceType.NEVER -> "Tekrar yok"
                        CalendarRecurrenceType.DAILY -> "Gunluk"
                        CalendarRecurrenceType.SOMEDAYS -> "Belirli gunler"
                    },
                    location = facilityUser.gymName,
                    trainer = trainer.name,
                    capacity = "${participantIds.size}/${newCapacity}",
                    cost = if (state.cost <= 0f) "Ucretsiz" else "${state.cost}",
                    note = state.trainerNote,
                )

                _effect.emitIn(screenModelScope, FacilityLessonEditEffect.NavigateToUpdateComplete(updatedLesson))
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