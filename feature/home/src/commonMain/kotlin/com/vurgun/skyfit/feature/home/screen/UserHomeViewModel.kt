package com.vurgun.skyfit.feature.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.feature.home.component.HomeAppointmentItemViewData
import com.vurgun.skyfit.ui.core.components.event.AppointmentCardViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val user: UserDetail
        get() = userManager.user.value as? UserDetail
            ?: error("❌ current account is not user")

    val characterType = user.characterType

    private val _appointments = MutableStateFlow<List<HomeAppointmentItemViewData>>(emptyList())
    val appointments = _appointments.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
           courseRepository.getUpcomingAppointmentsByUser(user.normalUserId)
                .map { appointments ->
                    appointments.map {
                        HomeAppointmentItemViewData(it.lessonId, it.iconId, it.title, it.startTime.toString(), it.facilityName)
                    }
                }.fold(
                   onSuccess = {
                       _appointments.value = it
                   },
                   onFailure = {
                       print("❌ Appointments failed as you can see ${it.message}")
                   }
               )
        }
    }
}