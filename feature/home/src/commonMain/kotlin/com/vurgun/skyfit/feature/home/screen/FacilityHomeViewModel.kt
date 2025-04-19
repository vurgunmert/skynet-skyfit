package com.vurgun.skyfit.feature.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.user.repository.UserManager
import com.vurgun.skyfit.data.core.domain.model.FacilityDetail
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.feature.home.component.HomeAppointmentItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FacilityHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility")

    private val _appointments = MutableStateFlow<List<HomeAppointmentItemViewData>>(emptyList())
    val appointments = _appointments.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _appointments.value = courseRepository.getUpcomingLessonsByFacility(facilityUser.gymId)
                .getOrNull()?.let { list ->
                    list.map { it.toHomeViewData() }
                }.orEmpty()
        }
    }
}

fun Lesson.toHomeViewData(): HomeAppointmentItemViewData {
    return HomeAppointmentItemViewData(
        lessonId = lessonId,
        iconId = iconId,
        title = title,
        time = startTime.toString(),
        location = facilityName
    )
}