package com.vurgun.skyfit.core.data.v1.data.lesson.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.facility.mapper.FacilityDataMapper.toDomainLessonParticipants
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonDataMapper.toScheduledLessonDetail
import com.vurgun.skyfit.core.data.v1.data.lesson.model.GetLessonParticipantsRequest
import com.vurgun.skyfit.core.data.v1.data.lesson.model.GetScheduledLessonDetailRequest
import com.vurgun.skyfit.core.data.v1.data.lesson.service.LessonApiService
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonParticipant
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.ScheduledLessonDetail
import com.vurgun.skyfit.core.data.v1.domain.lesson.repository.LessonRepository
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class LessonRepositoryImpl(
    private val apiService: LessonApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
): LessonRepository {

    override suspend fun getScheduledLessonDetail(lessonId: Int): Result<ScheduledLessonDetail> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetScheduledLessonDetailRequest(lessonId)
        apiService.getScheduledLessonDetail(request, token).mapOrThrow { it.toScheduledLessonDetail() }
    }

    override suspend fun getLessonParticipants(lessonId: Int): Result<List<LessonParticipant>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetLessonParticipantsRequest(lessonId)
        apiService.getLessonParticipants(request, token).mapOrThrow { it.toDomainLessonParticipants() }
    }
}