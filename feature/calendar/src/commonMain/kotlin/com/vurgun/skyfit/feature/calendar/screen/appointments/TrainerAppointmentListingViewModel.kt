package com.vurgun.skyfit.feature.calendar.screen.appointments

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.vurgun.skyfit.core.data.domain.model.TrainerDetail
import com.vurgun.skyfit.core.data.domain.repository.UserManager
import com.vurgun.skyfit.core.data.utility.SingleSharedFlow
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

sealed interface TrainerAppointmentListingAction {
    data object NavigateToBack : TrainerAppointmentListingAction
    data object ShowFilter : TrainerAppointmentListingAction
    data class ChangeTab(val index: Int) : TrainerAppointmentListingAction
    data class NavigateToDetail(val lessonId: Int) : TrainerAppointmentListingAction
}

sealed interface TrainerAppointmentListingEffect {
    data object NavigateToBack : TrainerAppointmentListingEffect
    data object ShowFilter : TrainerAppointmentListingEffect
    data class NavigateToDetail(val lessonId: Int) : TrainerAppointmentListingEffect
}

class TrainerAppointmentListingViewModel(
    private val userManager: UserManager,
    private val courseRepository: CourseRepository,
    private val mapper: LessonSessionItemViewDataMapper
) : ScreenModel {

    private val _effect = SingleSharedFlow<TrainerAppointmentListingEffect>()
    val effect: SharedFlow<TrainerAppointmentListingEffect> = _effect

    private val trainer: TrainerDetail
        get() = userManager.user.value as? TrainerDetail
            ?: error("❌ current account is not trainer")


    fun refreshData() {
        screenModelScope.launch {
            try {
               val lessons = courseRepository.getLessonsByTrainer(trainerId = trainer.trainerId, "", "")
                    .getOrDefault(emptyList())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}