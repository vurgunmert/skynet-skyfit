package com.vurgun.skyfit.feature_lessons.ui

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.core.utils.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate

// region: FacilityTrainerViewData (Data from Facility API/Database)
data class FacilityTrainerViewData(
    val id: String,
    val name: String,
    val imageUrl: String?
)
// endregion

// region: FacilityClassViewData (User-selected class details)
data class FacilityClassViewData(
    val title: String? = null,
    val description: String? = null,
    val icon: String? = null,
    val selectedTrainer: FacilityTrainerViewData? = null, // Nullable to allow selection
    val trainers: List<FacilityTrainerViewData> = fakeFacilityTrainers,
    val selectedDate: LocalDate = LocalDate.now(),
    val startTime: String = "08:00",
    val endTime: String = "08:30",
    val repeatOption: String = "Hergün",
    val selectedDaysOfWeek: List<String> = emptyList(),
    val monthlyOption: String = classRepeatMonthlyOptions.first(),
    val isAppointmentMandatory: Boolean = false,
    val isPaymentMandatory: Boolean = false,
    val price: String = "0.00",
    val capacity: String = "5",
    val userGroup: String = "Herkes",
    val trainerNote: String = "",
    val isSaveButtonEnabled: Boolean = false,
    val showCancelDialog: Boolean = false
)
// endregion

val classRepeatPeriodOptions = listOf("Hergün", "Haftada belirli günler", "Ayda bir kez", "Tekrar yok")
val classRepeatMonthlyOptions = listOf("Her ayın ilk pazartesi", "Her ayın 15. günü")
val classRepeatDaysOfWeek = listOf("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi", "Pazar")


// Simulated function to load a class
private val fakeFacilityTrainers = listOf(
    FacilityTrainerViewData("1","Sude Kale", "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680"),
    FacilityTrainerViewData("2","Alex Lang", "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680"),
    FacilityTrainerViewData("3","Sophia Hawl", "https://example.com/sophia.jpg"),
    FacilityTrainerViewData("4","Cenk Kar", "https://ik.imagekit.io/skynet2skyfit/avatar_sample.png?updatedAt=1738866499680"),
    FacilityTrainerViewData("5","Racheal Lee", "https://example.com/racheal.jpg"),
    FacilityTrainerViewData("6","Karen Maroon", "https://example.com/karen.jpg")
)

class MobileFacilityClassEditScreenViewModel : ViewModel() {

    private val _facilityClassState = MutableStateFlow(FacilityClassViewData()) // Holds current state
    val facilityClassState: StateFlow<FacilityClassViewData> = _facilityClassState

    private var initialState: FacilityClassViewData? = null // To track modifications



    fun loadClass(facilityId: String, classId: String?) {
//        val fetchedData = classId?.let { fakeFetchClassData(facilityId, it) }
//            ?: FacilityClassViewData(trainers = facilityTrainers)
//
//        initialState = fetchedData.copy()

//        _facilityClassState.update { fetchedData } // 🔥 This will ensure recomposition
    }

    // Compare current state with the initial state, enable Save if changed
    private fun checkIfModified() {
        _facilityClassState.update {
            it.copy(isSaveButtonEnabled = it != initialState)
        }
    }

    // ✅ Update functions that modify state and trigger `checkIfModified()`
    fun updateIcon(icon: String) {
        _facilityClassState.update { it.copy(icon = icon) }
        checkIfModified()
    }

    fun updateTitle(title: String) {
        _facilityClassState.update { it.copy(title = title) }
        checkIfModified()
    }

    fun updateDescription(description: String) {
        _facilityClassState.update { it.copy(description = description) }
        checkIfModified()
    }

    fun updateSelectedTrainer(trainer: FacilityTrainerViewData) {
        _facilityClassState.update { it.copy(selectedTrainer = trainer) }
        checkIfModified()
    }

    fun updateTrainerNote(note: String) {
        _facilityClassState.update { it.copy(trainerNote = note) }
        checkIfModified()
    }

    fun updateSelectedDate(date: LocalDate) {
        _facilityClassState.update { it.copy(selectedDate = date) }
        checkIfModified()
    }

    fun updateStartTime(time: String) {
        _facilityClassState.update { it.copy(startTime = time) }
        checkIfModified()
    }

    fun updateEndTime(time: String) {
        _facilityClassState.update { it.copy(endTime = time) }
        checkIfModified()
    }

    fun updateRepeatOption(option: String) {
        _facilityClassState.update { it.copy(repeatOption = option) }
        checkIfModified()
    }

    fun toggleDaySelection(day: String) {
        _facilityClassState.update {
            val updatedDays = if (it.selectedDaysOfWeek.contains(day)) {
                it.selectedDaysOfWeek - day
            } else {
                it.selectedDaysOfWeek + day
            }
            it.copy(selectedDaysOfWeek = updatedDays)
        }
        checkIfModified()
    }

    fun updateMonthlyOption(option: String) {
        _facilityClassState.update { it.copy(monthlyOption = option) }
        checkIfModified()
    }

    fun updateCapacity(capacity: String) {
        _facilityClassState.update { it.copy(capacity = capacity) }
        checkIfModified()
    }

    fun updateUserGroup(group: String) {
        _facilityClassState.update { it.copy(userGroup = group) }
        checkIfModified()
    }

    fun updateAppointmentMandatory(mandatory: Boolean) {
        _facilityClassState.update { it.copy(isAppointmentMandatory = mandatory) }
        checkIfModified()
    }

    fun updatePaymentMandatory(mandatory: Boolean) {
        _facilityClassState.update { it.copy(isPaymentMandatory = mandatory) }
        checkIfModified()
    }

    fun updatePrice(amount: String) {
        _facilityClassState.update { it.copy(price = amount) }
        checkIfModified()
    }

    fun updateShowCancelDialog(show: Boolean) {
        _facilityClassState.update { it.copy(showCancelDialog = show) }
    }

    // Simulated function to fetch a class from the database
    private fun fakeFetchClassData(facilityId: String, classId: String): FacilityClassViewData {
        return FacilityClassViewData(
            title = "Example Class",
            selectedTrainer = FacilityTrainerViewData("1","Sude Kale", "https://example.com/sude.jpg"),
            trainers = fakeFacilityTrainers,
            trainerNote = "Be sure to stretch before class!",
            selectedDate = LocalDate(2024, 12, 24),
            startTime = "09:00",
            endTime = "10:30",
            repeatOption = "Haftada belirli günler",
            selectedDaysOfWeek = listOf("Pazartesi", "Çarşamba"),
            monthlyOption = "Her ayın ilk pazartesi",
            capacity = "10",
            userGroup = "Herkes",
            isAppointmentMandatory = false,
            isPaymentMandatory = false,
            price = "50.00",
            isSaveButtonEnabled = false, // Initial state should not be modified yet
            showCancelDialog = false
        )
    }
}