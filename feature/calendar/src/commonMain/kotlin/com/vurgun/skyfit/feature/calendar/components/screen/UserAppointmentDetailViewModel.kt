package com.vurgun.skyfit.feature.calendar.components.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.courses.domain.model.Appointment
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserAppointmentDetailViewModel(
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _appointment = MutableStateFlow<Appointment?>(null)
    val appointment: StateFlow<Appointment?> get() = _appointment

    fun loadData(appointment: Appointment) {
        _appointment.value = appointment
    }

    fun cancelAppointment() {
        viewModelScope.launch {
            appointment.value?.let {

                courseRepository.cancelAppointment(it.lessonId, it.lpId)
                    .getOrThrow()
            }
        }
    }
}
