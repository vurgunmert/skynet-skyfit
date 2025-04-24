package com.vurgun.skyfit.feature.calendar.components.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.courses.domain.model.Appointment
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.user.repository.UserManager
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class UserAppointmentsViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val user: UserDetail
        get() = userManager.user.value as? UserDetail
            ?: error("❌ current account is not user")

    private val _appointments = MutableStateFlow<List<Appointment>>(emptyList())
    val appointments: StateFlow<List<Appointment>> get() = _appointments

    private val _activeTab = MutableStateFlow(0)
    val activeTab: StateFlow<Int> get() = _activeTab

    // Derived states based on active tab selection
    val filteredAppointments: StateFlow<List<Appointment>> = combine(
        _appointments, _activeTab
    ) { allAppointments, tabIndex ->
        when (tabIndex) {
            0 -> allAppointments.filter { it.status == 1 } // Active (future scheduled)
            1 -> allAppointments.filter { it.status in listOf(3, 4, 5) } // Canceled
            2 -> allAppointments.filter { it.status in listOf(6, 7) } // Attendance history
            else -> allAppointments
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val tabTitles: StateFlow<List<String>> = appointments.map { allAppointments ->
        listOf(
            "Aktif (${allAppointments.count { it.status == 1 }})",
            "İptal Edilen (${allAppointments.count { it.status in listOf(3, 4, 5) }})",
            "Devamsızlık (${allAppointments.count { it.status in listOf(6, 7) }})"
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf("Aktif (0)", "İptal edilen (0)", "Geçmiş (0)"))

    fun refreshData() {
        viewModelScope.launch {
            try {
                _appointments.value = courseRepository.getAppointmentsByUser(user.normalUserId).getOrThrow()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateActiveTab(index: Int) {
        _activeTab.value = index
    }

    fun cancelAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                courseRepository.cancelAppointment(appointment.lessonId, appointment.lpId).getOrThrow()
                refreshData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
