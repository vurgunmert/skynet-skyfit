package com.vurgun.skyfit.presentation.shared.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.presentation.mobile.features.user.appointments.AppointmentCardViewData
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class UserAppointmentsViewModel : ViewModel() {

    private val _appointments = MutableStateFlow<List<AppointmentCardViewData>>(emptyList())
    val appointments: StateFlow<List<AppointmentCardViewData>> get() = _appointments

    private val _activeTab = MutableStateFlow(0)
    val activeTab: StateFlow<Int> get() = _activeTab

    // Derived states based on active tab selection
    val filteredAppointments: StateFlow<List<AppointmentCardViewData>> = combine(
        _appointments, _activeTab
    ) { allAppointments, tabIndex ->
        when (tabIndex) {
            0 -> allAppointments.filter { it.status == "Planlanan" } // Active (future scheduled)
            1 -> allAppointments.filter { it.status == "İptal" } // Canceled
            2 -> allAppointments.filter { it.status == "Tamamlandı" || it.status == "Eksik" } // Attendance history
            else -> allAppointments
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val tabTitles: StateFlow<List<String>> = appointments.map { allAppointments ->
        listOf(
            "Aktif (${allAppointments.count { it.status == "Planlanan" }})",
            "İptal edilen (${allAppointments.count { it.status == "İptal" }})",
            "Geçmiş (${allAppointments.count { it.status == "Tamamlandı" || it.status == "Eksik" }})"
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf("Aktif (0)", "İptal edilen (0)", "Geçmiş (0)"))

    fun loadData() {
        viewModelScope.launch {
            val appointments = listOf(
                AppointmentCardViewData(
                    iconId = "ic_push_up",
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
                ),
                AppointmentCardViewData(
                    iconId = "ic_sit_up",
                    title = "Reformer Pilates",
                    date = "30/11/2024",
                    hours = "08:00 - 09:00",
                    category = "Pilates",
                    location = "@ironstudio",
                    trainer = "Michael Blake",
                    capacity = "12",
                    cost = "$20",
                    note = null,
                    isFull = false,
                    canNotify = true,
                    status = "Eksik" // Missed class (no-show)
                ),
                AppointmentCardViewData(
                    iconId = "ic_yoga",
                    title = "Fitness",
                    date = "30/11/2024",
                    hours = "08:00 - 09:00",
                    category = "PT",
                    location = "@ironstudio",
                    trainer = "Michael Blake",
                    capacity = "15",
                    cost = "$25",
                    note = null,
                    isFull = true,
                    canNotify = true,
                    status = "Tamamlandı" // Completed class (attended)
                ),
                AppointmentCardViewData(
                    iconId = "ic_push_up",
                    title = "Spinning Class",
                    date = "15/10/2024",
                    hours = "07:30 - 08:30",
                    category = "Cycling",
                    location = "@fitnesshub",
                    trainer = "Emma Johnson",
                    capacity = "20",
                    cost = "$15",
                    note = "Bring your own water bottle!",
                    isFull = false,
                    canNotify = false,
                    status = "İptal" // Canceled
                ),
                AppointmentCardViewData(
                    iconId = "ic_yoga",
                    title = "Yoga Flow",
                    date = "20/10/2024",
                    hours = "18:00 - 19:00",
                    category = "Yoga",
                    location = "@zenstudio",
                    trainer = "Samantha Green",
                    capacity = "15",
                    cost = "Free",
                    note = "Mats provided, please bring a towel.",
                    isFull = false,
                    canNotify = false,
                    status = "İptal" // Canceled
                ),
                AppointmentCardViewData(
                    iconId = "ic_jumping_rope",
                    title = "Stretching & Mobility",
                    date = "05/09/2024",
                    hours = "12:00 - 13:00",
                    category = "Recovery",
                    location = "@recoverycenter",
                    trainer = "Lisa Harper",
                    capacity = "5",
                    cost = "Free",
                    note = "Foam rollers provided.",
                    isFull = false,
                    canNotify = false,
                    status = "Eksik" // No-show
                )
            )

            _appointments.value = appointments
        }
    }

    fun updateActiveTab(index: Int) {
        _activeTab.value = index
    }

    fun deleteAppointment(appointment: AppointmentCardViewData) {
        _appointments.value = _appointments.value.filterNot { it == appointment }
    }

    fun deleteAllAppointments() {
        _appointments.value = emptyList()
    }

}
