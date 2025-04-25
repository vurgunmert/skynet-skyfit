package com.vurgun.skyfit.feature.profile.trainer.schedule

import androidx.lifecycle.ViewModel
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.LessonSessionItemViewDataMapper
import com.vurgun.skyfit.data.user.repository.ProfileRepository

class TrainerProfileScheduleViewModel(
    private val profileRepository: ProfileRepository,
    private val courseRepository: CourseRepository,
    private val lessonMapper: LessonSessionItemViewDataMapper
): ViewModel() {
}