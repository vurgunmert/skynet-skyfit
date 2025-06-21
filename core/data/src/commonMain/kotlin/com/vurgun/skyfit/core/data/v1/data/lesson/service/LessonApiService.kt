package com.vurgun.skyfit.core.data.v1.data.lesson.service

import com.vurgun.skyfit.core.data.v1.data.lesson.model.GetLessonParticipantsRequest
import com.vurgun.skyfit.core.data.v1.data.lesson.model.GetScheduledLessonDetailRequest
import com.vurgun.skyfit.core.data.v1.data.lesson.model.LessonParticipantDTO
import com.vurgun.skyfit.core.data.v1.data.lesson.model.ScheduledLessonDetailDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.http.*

class LessonApiService(private val apiClient: ApiClient) {

    private companion object Endpoints {
        const val GET_SCHEDULED_LESSON_DETAIL = "get/lesson/details"
        const val GET_LESSON_PARTICIPANTS = "get/lesson/participants"
    }

    internal suspend fun getScheduledLessonDetail(
        request: GetScheduledLessonDetailRequest,
        token: String
    ): ApiResult<ScheduledLessonDetailDTO> {
        return apiClient.safeApiCall<ScheduledLessonDetailDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_SCHEDULED_LESSON_DETAIL)
            setBody(request)
        }
    }

    internal suspend fun getLessonParticipants(
        request: GetLessonParticipantsRequest,
        token: String
    ): ApiResult<List<LessonParticipantDTO>> {
        return apiClient.safeApiCall<List<LessonParticipantDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_LESSON_PARTICIPANTS)
            setBody(request)
        }
    }

}