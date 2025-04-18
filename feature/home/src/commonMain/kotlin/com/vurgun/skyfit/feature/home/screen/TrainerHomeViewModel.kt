package com.vurgun.skyfit.feature.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.data.core.domain.manager.UserManager
import com.vurgun.skyfit.data.core.domain.model.TrainerDetail
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.feature.home.component.HomeAppointmentItemViewData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainerHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private val trainerUser: TrainerDetail
        get() = userManager.user.value as? TrainerDetail
            ?: error("❌ User is not a Trainer")

    val characterType = trainerUser.characterType

    private val _appointments = MutableStateFlow<List<HomeAppointmentItemViewData>>(emptyList())
    val appointments = _appointments.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _appointments.value = courseRepository.getUpcomingLessonsByTrainer(trainerUser.trainerId)
                .getOrNull()?.let { list ->
                    list.map { it.toHomeViewData() }
                }.orEmpty()
        }
    }
}