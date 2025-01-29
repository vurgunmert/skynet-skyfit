package com.vurgun.skyfit.presentation.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class UserAppointmentsViewModel : ViewModel() {

    private val _appointments = MutableStateFlow<List<AppointmentCardItem>>(emptyList())
    val appointments: StateFlow<List<AppointmentCardItem>> get() = _appointments

    val tabTitles: StateFlow<List<String>> = appointments.map {
        listOf(
            "Aktif (${3}})",
            "Iptal edilen (${5})",
            "Devamsızlık (${2})"
        )
    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun loadData() {
        val randomAppointments = List(6) {
            AppointmentCardItem(
                iconUrl = "https://example.com/icon${it + 1}.png", // Example icon URL
                title = listOf(
                    "Yoga Class",
                    "Boxing Training",
                    "Strength Training",
                    "Pilates Session",
                    "Cycling Class",
                    "Swimming Lesson"
                ).random(),
                date = listOf("Jan 30", "Feb 5", "Feb 10", "Mar 2", "Mar 15", "Apr 1").random(),
                hours = listOf("10:00 AM - 11:00 AM", "2:00 PM - 3:30 PM", "5:00 PM - 6:00 PM").random(),
                category = listOf("Fitness", "Wellness", "Sports", "Mindfulness").random(),
                location = listOf("Downtown Gym", "Wellness Center", "City Park", "Online", "Health Club").random(),
                trainer = listOf("John Doe", "Sarah Lee", "Michael Brown", "Emma Watson", "David Green").random(),
                capacity = listOf("10", "15", "20", null).random(),
                cost = listOf("$10", "$15", "$20", "Free", null).random(),
                note = listOf("Bring a towel", "Yoga mats provided", "Beginner-friendly class", null).random(),
                isFull = listOf(true, false, null).random(),
                canNotify = listOf(true, false).random()
            )
        }

        _appointments.value = randomAppointments
    }

    fun cancelBooking() {

    }

    fun deleteAllInactiveAppointments() {

    }
}