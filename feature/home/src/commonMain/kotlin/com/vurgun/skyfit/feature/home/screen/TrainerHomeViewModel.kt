package com.vurgun.skyfit.feature.home.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vurgun.skyfit.core.data.domain.model.TrainerDetail
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.courses.model.LessonSessionItemViewData
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrainerHomeViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val mapper: LessonSessionItemViewDataMapper
) : ViewModel() {

    private val trainerUser: TrainerDetail
        get() = userManager.user.value as? TrainerDetail
            ?: error("❌ User is not a Trainer")

    val characterType = trainerUser.characterType

    private val _appointments = MutableStateFlow<List<LessonSessionItemViewData>>(emptyList())
    val appointments = _appointments.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _appointments.value = courseRepository.getUpcomingLessonsByTrainer(trainerUser.trainerId)
                .getOrNull()?.let { list ->
                    list.map { mapper.map(it) }
                }.orEmpty()
        }
    }
}