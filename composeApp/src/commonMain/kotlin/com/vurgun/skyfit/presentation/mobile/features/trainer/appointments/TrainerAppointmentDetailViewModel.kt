package com.vurgun.skyfit.presentation.mobile.features.trainer.appointments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TrainerAppointmentDetailViewModel : ViewModel() {

    private val _appointment = MutableStateFlow<AppointmentCardViewData?>(null)
    val appointment: StateFlow<AppointmentCardViewData?> get() = _appointment

    private val _participants = MutableStateFlow<List<ParticipantViewData>>(emptyList())
    val participants: StateFlow<List<ParticipantViewData>> get() = _participants

    fun loadData() {
        viewModelScope.launch {
            // **Simulate fetching appointment details**
            _appointment.value = AppointmentCardViewData(
                iconId = "https://example.com/icons/strength.png",
                title = "Fitness",
                date = "30/11/2024",
                hours = "07:00 - 08:00",
                category = "PT",
                location = "ironstudio",
                trainer = "Micheal Blake",
                capacity = "5",
                cost = "Free",
                note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
                isFull = false,
                canNotify = true,
                status = "Planlanan"
            )

            // **Simulate fetching participant list**
            _participants.value = listOf(
                ParticipantViewData("1", "Selin Kaya"),
                ParticipantViewData("2", "Ali Çelik"),
                ParticipantViewData("3", "Deniz Şahin"),
                ParticipantViewData("4", "Eren Yıldız")
            )
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
