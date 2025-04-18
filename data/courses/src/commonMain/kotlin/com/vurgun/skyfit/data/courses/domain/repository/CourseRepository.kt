package com.vurgun.skyfit.data.courses.domain.repository

import com.vurgun.skyfit.data.courses.domain.model.Appointment
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.model.LessonCreationInfo
import com.vurgun.skyfit.data.courses.domain.model.LessonUpdateInfo

interface CourseRepository {

    // region: Lessons
    suspend fun getLessonsByFacility(gymId: Int, startDate: String, endDate: String? = null): Result<List<Lesson>>
    suspend fun getLessonsByTrainer(trainerId: Int, startDate: String, endDate: String?): Result<List<Lesson>>

    suspend fun getUpcomingLessonsByFacility(gymId: Int, limit: Int = 3): Result<List<Lesson>>
    suspend fun getUpcomingLessonsByTrainer(trainerId: Int, limit: Int = 3): Result<List<Lesson>>

    suspend fun createLesson(info: LessonCreationInfo): Result<Unit>
    suspend fun updateLesson(info: LessonUpdateInfo): Result<Unit>
    suspend fun deactivateLesson(lessonId: Int): Result<Unit>
    suspend fun deleteLesson(lessonId: Int): Result<Unit>
    // endregion

    // region: Appointments
    suspend fun getAppointmentsByUser(userId: Int): Result<List<Appointment>>
    suspend fun getUpcomingAppointmentsByUser(userId: Int, limit: Int): Result<List<Appointment>>
    // endregion

    // region: Lesson Participation
    suspend fun joinLesson(lessonId: Int): Result<Unit>
    // endregion
}
