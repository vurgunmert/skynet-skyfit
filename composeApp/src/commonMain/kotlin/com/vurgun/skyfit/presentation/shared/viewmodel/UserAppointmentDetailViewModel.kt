package com.vurgun.skyfit.presentation.shared.viewmodel

import androidx.lifecycle.ViewModel

class UserAppointmentDetailViewModel : ViewModel() {

    val date = "30/11/2024"
    val time = "07:00-08:00"
    val trainer = "07:00-08:00"
    val facilityName = "ironstudio"
    val participantCount = "5"
    val trainerNote = "Try to arrive 5-10 minutes early to warm up and settle in before the class starts."

    fun loadData(bookingPath: String) {

    }

    fun cancelAppointment() {
    }
}