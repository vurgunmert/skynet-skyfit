package com.vurgun.skyfit.feature.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.data.domain.model.FacilityDetail
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.feature.home.component.HomeAppointmentItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FacilityHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val mapper: LessonSessionItemViewDataMapper
) : ViewModel() {

    private val facilityUser: FacilityDetail
        get() = userManager.user.value as? FacilityDetail
            ?: error("User is not a Facility")

    private val _appointments = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val appointments = _appointments.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _appointments.value = courseRepository.getUpcomingLessonsByFacility(facilityUser.gymId)
                .getOrNull()?.let { list ->
                    list.map { mapper.map(it) }
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