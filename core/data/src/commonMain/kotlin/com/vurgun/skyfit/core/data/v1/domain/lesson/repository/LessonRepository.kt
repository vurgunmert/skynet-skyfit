package com.vurgun.skyfit.core.data.v1.domain.lesson.repository

import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonParticipant
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.ScheduledLessonDetail

interface LessonRepository {

    suspend fun getScheduledLessonDetail(lessonId: Int): Result<ScheduledLessonDetail>
    suspend fun getLessonParticipants(lessonId: Int): Result<List<LessonParticipant>>
}