package com.vurgun.skyfit.core.data.schedule.data.service

import com.vurgun.skyfit.core.data.shared.data.model.EmptyDTO
import com.vurgun.skyfit.core.data.schedule.data.model.LessonParticipantDTO
import com.vurgun.skyfit.core.data.schedule.data.model.ActivateLessonRequest
import com.vurgun.skyfit.core.data.schedule.data.model.AppointmentDTO
import com.vurgun.skyfit.core.data.schedule.data.model.AppointmentDetailDTO
import com.vurgun.skyfit.core.data.schedule.data.model.CancelUserAppointmentRequest
import com.vurgun.skyfit.core.data.schedule.data.model.CreateAppointmentDTO
import com.vurgun.skyfit.core.data.schedule.data.model.CreateLessonRequest
import com.vurgun.skyfit.core.data.schedule.data.model.CreateUserAppointmentRequest
import com.vurgun.skyfit.core.data.schedule.data.model.DeactivateLessonRequest
import com.vurgun.skyfit.core.data.schedule.data.model.DeleteLessonRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetAppointmentDetailRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetFacilityLessonsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetScheduledLessonDetailRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetTrainerLessonsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetUpcomingFacilityLessonsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetUpcomingTrainerLessonsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetUpcomingUserAppointmentsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetUserAppointmentsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.LessonDTO
import com.vurgun.skyfit.core.data.schedule.data.model.ScheduledLessonDetailDTO
import com.vurgun.skyfit.core.data.schedule.data.model.UpdateLessonRequest
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.data.schedule.data.model.EvaluateParticipantsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetLessonParticipantsRequest
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class CourseApiService(private val apiClient: ApiClient) {

    private object Endpoints {
        const val CREATE_LESSON = "create/lesson"
        const val UPDATE_LESSON = "update/lesson"
        const val ACTIVATE_LESSON = "activate/lesson"
        const val DEACTIVATE_LESSON = "deactivate/lesson"
        const val DELETE_LESSON = "delete/lesson"
        const val GET_ACTIVE_LESSONS_BY_GYM = "get/lessons/gym"
        const val GET_ALL_LESSONS_BY_GYM = "get/all/lessons/gym"
        const val GET_UPCOMING_LESSONS_BY_GYM = "get/close/lessons/gym"
        const val GET_LESSONS_BY_TRAINER = "get/trainer/lessons"
        const val GET_UPCOMING_LESSONS_BY_TRAINER = "get/close/trainer/lessons"
        const val GET_SCHEDULED_LESSON_DETAIL = "get/lesson/details"
        const val GET_APPOINTMENTS_BY_USER = "get/appointments"
        const val GET_UPCOMING_APPOINTMENTS_BY_USER = "get/close/appointments"
        const val GET_APPOINTMENT_DETAIL = "get/appointment/details"
        const val CREATE_APPOINTMENT_BY_USER = "create/appointment"
        const val CANCEL_APPOINTMENT_BY_USER = "cancel/appointment"
        const val GET_LESSON_PARTICIPANTS = "get/lesson/participants"
        const val EVALUATE_PARTICIPANTS = "complete/lesson"
    }

    internal suspend fun getLessonsByFacility(request: GetFacilityLessonsRequest, token: String): ApiResult<List<LessonDTO>> {
        return apiClient.safeApiCall<List<LessonDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_ACTIVE_LESSONS_BY_GYM)
            setBody(request)
        }
    }

    internal suspend fun getUpcomingLessonsByFacility(
        request: GetUpcomingFacilityLessonsRequest,
        token: String
    ): ApiResult<List<LessonDTO>> {
        return apiClient.safeApiCall<List<LessonDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_UPCOMING_LESSONS_BY_GYM)
            setBody(request)
        }
    }

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

    internal suspend fun createLesson(request: CreateLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.CREATE_LESSON)
            setBody(request)
        }
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

    internal suspend fun updateLesson(request: UpdateLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.UPDATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun deactivateLesson(request: DeactivateLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.DEACTIVATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun activateLesson(request: ActivateLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.ACTIVATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun deleteLesson(request: DeleteLessonRequest, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Delete
            bearerAuth(token)
            url(Endpoints.DELETE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun getAppointmentsByUser(request: GetUserAppointmentsRequest, token: String): ApiResult<List<AppointmentDTO>> {
        return apiClient.safeApiCall<List<AppointmentDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_APPOINTMENTS_BY_USER)
            setBody(request)
        }
    }

    internal suspend fun getAppointmentDetail(request: GetAppointmentDetailRequest, token: String): ApiResult<AppointmentDetailDTO> {
        return apiClient.safeApiCall<AppointmentDetailDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_APPOINTMENT_DETAIL)
            setBody(request)
        }
    }

    internal suspend fun getUpcomingAppointmentsByUser(
        request: GetUpcomingUserAppointmentsRequest,
        token: String
    ): ApiResult<List<AppointmentDTO>> {
        return apiClient.safeApiCall<List<AppointmentDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_UPCOMING_APPOINTMENTS_BY_USER)
            setBody(request)
        }
    }

    internal suspend fun createUserAppointment(
        request: CreateUserAppointmentRequest,
        token: String
    ): ApiResult<CreateAppointmentDTO> {
        return apiClient.safeApiCall<CreateAppointmentDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.CREATE_APPOINTMENT_BY_USER)
            setBody(request)
        }
    }

    internal suspend fun cancelUserAppointment(
        request: CancelUserAppointmentRequest,
        token: String
    ): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.CANCEL_APPOINTMENT_BY_USER)
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
}