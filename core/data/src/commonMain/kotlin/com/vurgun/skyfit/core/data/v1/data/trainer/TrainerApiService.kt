package com.vurgun.skyfit.core.data.v1.data.trainer

import com.vurgun.skyfit.core.data.v1.data.global.model.EmptyDTO
import com.vurgun.skyfit.core.data.v1.data.lesson.model.LessonDTO
import com.vurgun.skyfit.core.data.v1.data.trainer.model.*
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class TrainerApiService(private val apiClient: ApiClient) {

    private companion object Endpoints {
        const val GET_TRAINER_PROFILE = "profile/get/trainer"
        const val UPDATE_TRAINER_PROFILE = "profile/update/trainer"
        const val GET_LESSONS_BY_TRAINER = "get/trainer/lessons"
        const val GET_UPCOMING_LESSONS_BY_TRAINER = "get/close/trainer/lessons"
        const val EVALUATE_PARTICIPANTS = "complete/lesson"
    }

    //region Profile
    suspend fun getTrainerProfile(request: GetTrainerProfileRequestDTO, token: String): ApiResult<TrainerProfileDTO> {
        return apiClient.safeApiCall<TrainerProfileDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_TRAINER_PROFILE)
            setBody(request)
        }
    }

    suspend fun updateTrainerProfile(request: UpdateTrainerProfileRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.UPDATE_TRAINER_PROFILE)
            setBody(buildTrainerProfileFormData(request))
        }
    }

    private fun buildTrainerProfileFormData(request: UpdateTrainerProfileRequestDTO): MultiPartFormDataContent {
        return MultiPartFormDataContent(
            formData {
                append("username", request.username)
                append("name", request.name)
                append("surname", request.surname)
                append("bio", request.bio)
                append("profileTags", request.profileTags.toString())

                request.profilePhoto?.let { bytes ->
                    append(
                        "profilePhoto", bytes,
                        headersOf(
                            HttpHeaders.ContentType to listOf("image/png"),
                            HttpHeaders.ContentDisposition to listOf("filename=${request.trainerId}-profile-image.png")
                        )
                    )
                }

                request.backgroundImage?.let { bytes ->
                    append(
                        "backgroundImage", bytes,
                        headersOf(
                            HttpHeaders.ContentType to listOf("image/png"),
                            HttpHeaders.ContentDisposition to listOf("filename=${request.trainerId}-background-image.png")
                        )
                    )
                }
            }
        )
    }
    // endregion Profile

    //region Lesson

    internal suspend fun getLessonsByTrainer(request: GetTrainerLessonsRequest, token: String): ApiResult<List<LessonDTO>> {
        return apiClient.safeApiCall<List<LessonDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_LESSONS_BY_TRAINER)
            setBody(request)
        }
    }

    internal suspend fun getUpcomingLessonsByTrainer(request: GetUpcomingTrainerLessonsRequest, token: String): ApiResult<List<LessonDTO>> {
        return apiClient.safeApiCall<List<LessonDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_UPCOMING_LESSONS_BY_TRAINER)
            setBody(request)
        }
    }

    internal suspend fun evaluateParticipants(
        request: EvaluateParticipantsRequest,
        token: String
    ): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.EVALUATE_PARTICIPANTS)
            setBody(request)
        }
    }
    //endregion Lesson
}