package com.vurgun.skyfit.feature.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.utility.now
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.feature.home.component.HomeAppointmentItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class FacilityHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val _appointments = MutableStateFlow<List<HomeAppointmentItemViewData>>(emptyList())
    val appointments = _appointments.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val gymId = userManager.user.value?.gymId
            if (gymId == null) {
                // You could also emit a separate UI state for error
                println("❌ No gymId found in userManager")
                return@launch
            }

            val today = LocalDate.now()
            val result = courseRepository.getLessons(gymId, today.toString())

            _appointments.value = result
                .mapCatching { lessons ->
                    lessons.sortedBy { it.startDateTime }
                        .take(5)
                        .map { it.toHomeViewData() }
                }
                .getOrElse {
                    println("❌ Failed to load lessons: $it")
                    emptyList()
                }
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