package com.vurgun.skyfit.feature.calendar.components.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.courses.domain.model.Appointment
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrainerAppointmentDetailViewModel(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _appointment = MutableStateFlow<Appointment?>(null)
    val appointment: StateFlow<Appointment?> get() = _appointment

    private val _participants = MutableStateFlow<List<ParticipantViewData>>(emptyList())
    val participants: StateFlow<List<ParticipantViewData>> get() = _participants

    fun loadData() {
        viewModelScope.launch {
//            // **Simulate fetching appointment details**
//            _appointment.value =
//            // **Simulate fetching participant list**
//            _participants.value = listOf(
//                ParticipantViewData("1", "Selin Kaya"),
//                ParticipantViewData("2", "Ali Çelik"),
//                ParticipantViewData("3", "Deniz Şahin"),
//                ParticipantViewData("4", "Eren Yıldız")
//            )
        }
    }

    fun toggleAttendance(participantId: String) {
        _participants.value = _participants.value.map {
            if (it.id == participantId) it.copy(isPresent = !it.isPresent) else it
        }
    }

    fun saveAttendance() {
    }
}

data class ParticipantViewData(
    val id: String,
    val name: String,
    var isPresent: Boolean = false
)
