package com.vurgun.skyfit.data.courses

import com.vurgun.skyfit.data.core.model.EmptyDataResponse
import com.vurgun.skyfit.data.courses.model.ActivateLessonRequest
import com.vurgun.skyfit.data.courses.model.AppointmentDTO
import com.vurgun.skyfit.data.courses.model.AppointmentDetailDTO
import com.vurgun.skyfit.data.courses.model.CancelUserAppointmentRequest
import com.vurgun.skyfit.data.courses.model.CreateLessonRequest
import com.vurgun.skyfit.data.courses.model.CreateUserAppointmentRequest
import com.vurgun.skyfit.data.courses.model.DeactivateLessonRequest
import com.vurgun.skyfit.data.courses.model.DeleteLessonRequest
import com.vurgun.skyfit.data.courses.model.GetAppointmentDetailRequest
import com.vurgun.skyfit.data.courses.model.GetFacilityLessonsRequest
import com.vurgun.skyfit.data.courses.model.GetScheduledLessonDetailRequest
import com.vurgun.skyfit.data.courses.model.GetTrainerLessonsRequest
import com.vurgun.skyfit.data.courses.model.GetUpcomingFacilityLessonsRequest
import com.vurgun.skyfit.data.courses.model.GetUpcomingTrainerLessonsRequest
import com.vurgun.skyfit.data.courses.model.GetUpcomingUserAppointmentsRequest
import com.vurgun.skyfit.data.courses.model.GetUserAppointmentsRequest
import com.vurgun.skyfit.data.courses.model.LessonDTO
import com.vurgun.skyfit.data.courses.model.ScheduledLessonDetailDTO
import com.vurgun.skyfit.data.courses.model.UpdateLessonRequest
import com.vurgun.skyfit.data.network.ApiClient
import com.vurgun.skyfit.data.network.ApiResult
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
        const val GET_LESSONS_BY_GYM = "get/lessons/gym"
        const val GET_UPCOMING_LESSONS_BY_GYM = "get/close/lessons/gym"
        const val GET_LESSONS_BY_TRAINER = "get/trainer/lessons"
        const val GET_UPCOMING_LESSONS_BY_TRAINER = "get/close/trainer/lessons"
        const val GET_SCHEDULED_LESSON_DETAIL = "get/lesson/details"
        const val GET_APPOINTMENTS_BY_USER = "get/appointments"
        const val GET_UPCOMING_APPOINTMENTS_BY_USER = "get/close/appointments"
        const val GET_APPOINTMENT_DETAIL = "get/appointment/details"
        const val CREATE_APPOINTMENT_BY_USER = "create/appointment"
        const val CANCEL_APPOINTMENT_BY_USER = "cancel/appointment"
    }

    internal suspend fun getLessonsByFacility(request: GetFacilityLessonsRequest, token: String): ApiResult<List<LessonDTO>> {
        return apiClient.safeApiCall<List<LessonDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_LESSONS_BY_GYM)
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

    internal suspend fun createLesson(request: CreateLessonRequest, token: String): ApiResult<EmptyDataResponse> {
        return apiClient.safeApiCall<EmptyDataResponse> {
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

    internal suspend fun updateLesson(request: UpdateLessonRequest, token: String): ApiResult<EmptyDataResponse> {
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.UPDATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun deactivateLesson(request: DeactivateLessonRequest, token: String): ApiResult<EmptyDataResponse> {
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.DEACTIVATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun activateLesson(request: ActivateLessonRequest, token: String): ApiResult<EmptyDataResponse> {
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.ACTIVATE_LESSON)
            setBody(request)
        }
    }

    internal suspend fun deleteLesson(request: DeleteLessonRequest, token: String): ApiResult<EmptyDataResponse> {
        return apiClient.safeApiCall<EmptyDataResponse> {
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
    ): ApiResult<EmptyDataResponse> {
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.CREATE_APPOINTMENT_BY_USER)
            setBody(request)
        }
    }

    internal suspend fun cancelUserAppointment(
        request: CancelUserAppointmentRequest,
        token: String
    ): ApiResult<EmptyDataResponse> {
        return apiClient.safeApiCall<EmptyDataResponse> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.CANCEL_APPOINTMENT_BY_USER)
            setBody(request)
        }
    }
}