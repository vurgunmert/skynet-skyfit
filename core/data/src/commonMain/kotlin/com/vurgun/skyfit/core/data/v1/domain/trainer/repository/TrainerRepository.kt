package com.vurgun.skyfit.core.data.v1.domain.trainer.repository

import com.vurgun.skyfit.core.data.v1.domain.lesson.model.Lesson
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonParticipant
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import kotlinx.datetime.LocalDate

interface TrainerRepository {

    suspend fun getTrainerProfile(trainerId: Int): Result<TrainerProfile>

    suspend fun updateTrainerProfile(
        trainerId: Int,
        username: String,
        profileImageBytes: ByteArray?,
        backgroundImageBytes: ByteArray?,
        firstName: String,
        lastName: String,
        bio: String,
        profileTags: List<Int>
    ): Result<Unit>

    // region: Lessons
    suspend fun getLessonsByTrainer(trainerId: Int, startDate: LocalDate, endDate: LocalDate?): Result<List<Lesson>>
    suspend fun getLessonsByTrainer(trainerId: Int, startDate: String, endDate: String?): Result<List<Lesson>>

    suspend fun getUpcomingLessonsByTrainer(trainerId: Int, limit: Int = 3): Result<List<Lesson>>
    suspend fun evaluateParticipants(lessonId: Int, participants: List<LessonParticipant>): Result<Unit>
    // endregion
}