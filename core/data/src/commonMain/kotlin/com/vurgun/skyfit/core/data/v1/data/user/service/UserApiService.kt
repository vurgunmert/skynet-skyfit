package com.vurgun.skyfit.core.data.v1.data.user.service

import com.vurgun.skyfit.core.data.v1.data.global.model.EmptyDTO
import com.vurgun.skyfit.core.data.v1.data.user.model.*
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class UserApiService(private val apiClient: ApiClient) {

    private companion object Endpoints {
        const val GET_USER_PROFILE = "profile/get/user"
        const val UPDATE_USER_PROFILE = "profile/update/user"

        const val GET_APPOINTMENTS_BY_USER = "get/appointments"
        const val GET_UPCOMING_APPOINTMENTS_BY_USER = "get/close/appointments"
        const val GET_APPOINTMENT_DETAIL = "get/appointment/details"
        const val CREATE_APPOINTMENT_BY_USER = "create/appointment"
        const val CANCEL_APPOINTMENT_BY_USER = "cancel/appointment"

        const val ADD_WORKOUT_EVENTS = "add/calendar"
        const val GET_CALENDAR_SCHEDULE = "get/calendar"
    }

    //region Profile
    suspend fun getUserProfile(request: GetUserProfileRequestDTO, token: String): ApiResult<UserProfileDTO> {
        return apiClient.safeApiCall<UserProfileDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_USER_PROFILE)
            setBody(request)
        }
    }

    suspend fun updateUserProfile(request: UpdateUserProfileRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Put
            bearerAuth(token)
            url(Endpoints.UPDATE_USER_PROFILE)
            setBody(buildUserProfileFormData(request))
        }
    }
    private fun buildUserProfileFormData(request: UpdateUserProfileRequestDTO): MultiPartFormDataContent {
        return MultiPartFormDataContent(
            formData {
                append("username", request.username)
                append("name", request.name)
                append("surname", request.surname)
                append("weight", request.weight.toString())
                append("height", request.height.toString())
                append("bodyTypeId", request.bodyTypeId.toString())

                request.profilePhoto?.let { bytes ->
                    append(
                        "profilePhoto", bytes,
                        headersOf(
                            HttpHeaders.ContentType to listOf("image/png"),
                            HttpHeaders.ContentDisposition to listOf("filename=${request.userId}-profile-image.png")
                        )
                    )
                }

                request.backgroundImage?.let { bytes ->
                    append(
                        "backgroundImage", bytes,
                        headersOf(
                            HttpHeaders.ContentType to listOf("image/png"),
                            HttpHeaders.ContentDisposition to listOf("filename=${request.userId}-background-image.png")
                        )
                    )
                }
            }
        )
    }
    //endregion Profile

    //region Appointment
    internal suspend fun getAppointmentsByUser(
        request: GetUserAppointmentsRequest,
        token: String
    ): ApiResult<List<AppointmentDTO>> {
        return apiClient.safeApiCall<List<AppointmentDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_APPOINTMENTS_BY_USER)
            setBody(request)
        }
    }

    internal suspend fun getAppointmentDetail(
        request: GetAppointmentDetailRequest,
        token: String
    ): ApiResult<AppointmentDetailDTO> {
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
    //endregion Appointment

    //region Calendar
    internal suspend fun addCalendarEvents(request: AddCalendarEventRequestDTO, token: String): ApiResult<EmptyDTO> {
        return apiClient.safeApiCall<EmptyDTO> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.ADD_WORKOUT_EVENTS)
            setBody(request)
        }
    }

    internal suspend fun getCalendarEvents(request: GetCalendarEventsRequestDTO, token: String): ApiResult<List<CalendarEventDTO>> {
        return apiClient.safeApiCall<List<CalendarEventDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoints.GET_CALENDAR_SCHEDULE)
            setBody(request)
        }
    }
    //endregion Calendar
}