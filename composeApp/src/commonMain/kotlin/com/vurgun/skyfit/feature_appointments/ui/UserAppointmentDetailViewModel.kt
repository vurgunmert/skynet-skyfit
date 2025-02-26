package com.vurgun.skyfit.feature_appointments.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UserAppointmentDetailViewModel : ViewModel() {
    private val _appointment = MutableStateFlow<AppointmentCardViewData?>(null)
    val appointment: StateFlow<AppointmentCardViewData?> get() = _appointment

    fun loadData() {
        _appointment.value = fakeAppointment
    }

    fun cancelAppointment() {
        _appointment.update { it?.copy(status = "İptal") }
    }

    val fakeAppointment = AppointmentCardViewData(
        iconId = "https://example.com/icons/strength.png",
        title = "Shoulders and Abs",
        date = "30/11/2024",
        hours = "08:00 - 09:00",
        category = "Group Fitness",
        location = "@ironstudio",
        trainer = "Michael Blake",
        capacity = "10",
        cost = "Free",
        note = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts.",
        isFull = false,
        canNotify = true,
        status = "Planlanan" // Scheduled for the future
    )
}
