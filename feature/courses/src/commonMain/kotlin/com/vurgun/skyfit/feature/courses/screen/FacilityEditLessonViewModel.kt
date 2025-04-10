package com.vurgun.skyfit.feature.courses.screen

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.core.domain.model.CalendarRecurrence
import com.vurgun.skyfit.data.core.utility.now
import com.vurgun.skyfit.feature.courses.component.SelectableTrainerMenuItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

data class FacilityEditLessonViewState(
    val iconId: String = "ic_push_up",
    val title: String? = null,
    val trainers: List<SelectableTrainerMenuItemModel> = fakeTrainerMenuItems,
    val trainerNote: String? = null,
    val startDate: LocalDate = LocalDate.now(),
    val endDate: LocalDate = LocalDate.now(),
    val startTime: String = "08:00",
    val endTime: String = "08:30",
    val recurrence: CalendarRecurrence = CalendarRecurrence.Never,
    val isAppointmentMandatory: Boolean = false,
    val cost: Double? = null,
    val capacity: Int = 5,
    val cancelDurationHour: Int = 24,
    val isSaveButtonEnabled: Boolean = false,
    val showCancelDialog: Boolean = true
)

// Simulated
private val fakeTrainerMenuItems = listOf<SelectableTrainerMenuItemModel>(
    SelectableTrainerMenuItemModel(
        1,
        "Sude Kale",
        "https://cdn4.iconfinder.com/data/icons/diversity-v2-0-volume-05/64/fitness-trainer-black-male-512.png"
    ),
    SelectableTrainerMenuItemModel(
        2,
        "Alex Lang",
        "https://cdn4.iconfinder.com/data/icons/diversity-v2-0-volume-05/64/fitness-trainer-black-male-512.png"
    ),
    SelectableTrainerMenuItemModel(
        3,
        "Sophia Hawl",
        "https://cdn4.iconfinder.com/data/icons/diversity-v2-0-volume-05/64/fitness-trainer-black-male-512.png"
    ),
    SelectableTrainerMenuItemModel(
        4,
        "Cenk Kar",
        "https://cdn4.iconfinder.com/data/icons/diversity-v2-0-volume-05/64/fitness-trainer-black-male-512.png"
    ),
    SelectableTrainerMenuItemModel(
        5,
        "Racheal Lee",
        "https://cdn4.iconfinder.com/data/icons/diversity-v2-0-volume-05/64/fitness-trainer-black-male-512.png"
    ),
    SelectableTrainerMenuItemModel(
        6,
        "Karen Maroon",
        "https://cdn4.iconfinder.com/data/icons/diversity-v2-0-volume-05/64/fitness-trainer-black-male-512.png"
    )
)

class FacilityEditLessonViewModel : ViewModel() {

    private var initialState: FacilityEditLessonViewState = FacilityEditLessonViewState()

    private val _uiState = MutableStateFlow(initialState)
    val uiState: StateFlow<FacilityEditLessonViewState> = _uiState

    fun loadClass(facilityId: String, classId: String?) {
//        val fetchedData = classId?.let { fakeFetchClassData(facilityId, it) }
//            ?: FacilityClassViewData(trainers = facilityTrainers)
//
//        initialState = fetchedData.copy()

//        _facilityClassState.update { fetchedData } // 🔥 This will ensure recomposition
    }

    // Compare current state with the initial state, enable Save if changed
    private fun checkIfModified() {
        _uiState.update {
            it.copy(isSaveButtonEnabled = it != initialState) //TODO: Check mandatory fields
        }
    }

    fun updateIcon(icon: String) {
        _uiState.update { it.copy(iconId = icon) }
        checkIfModified()
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
        checkIfModified()
    }

    fun updateSelectedTrainer(trainer: SelectableTrainerMenuItemModel) {
        val updatedTrainers = _uiState.value.trainers.map {
            it.copy(selected = it.id == trainer.id)
        }
        _uiState.update { it.copy(trainers = updatedTrainers) }
        checkIfModified()
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
        _uiState.update { it.copy(startDate = date) }
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

    fun updateCost(amount: Double) {
        _uiState.update { it.copy(cost = amount) }
        checkIfModified()
    }

    fun updateShowCancelDialog(show: Boolean) {
        _uiState.update { it.copy(showCancelDialog = show) }
    }

    private fun fakeFetchClassData(facilityId: String, classId: String): FacilityEditLessonViewState {
        return FacilityEditLessonViewState(
            title = "Example Class",
            trainerNote = "Be sure to stretch before class!",
            startDate = LocalDate(2024, 12, 24),
            endDate = LocalDate(2024, 12, 24),
            startTime = "09:00",
            endTime = "10:30",
            capacity = 10,
            isAppointmentMandatory = false,
            cost = 50.0,
            isSaveButtonEnabled = false,
            showCancelDialog = false
        )
    }
}