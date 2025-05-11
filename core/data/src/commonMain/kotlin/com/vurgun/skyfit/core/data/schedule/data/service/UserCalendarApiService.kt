package com.vurgun.skyfit.core.data.schedule.data.service

import com.vurgun.skyfit.core.data.schedule.data.model.AddCalendarEventRequest
import com.vurgun.skyfit.core.data.schedule.data.model.CalendarEventDTO
import com.vurgun.skyfit.core.data.schedule.data.model.GetCalendarEventsRequest
import com.vurgun.skyfit.core.data.schedule.data.model.WorkoutEventDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.http.*

class UserCalendarApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val ADD_WORKOUT_EVENTS = "add/calendar"
        const val GET_CALENDAR_SCHEDULE = "get/calendar"
        const val GET_WORKOUT_EVENTS = "get/events"
    }

    suspend fun addCalendarEvents(request: AddCalendarEventRequest, token: String): ApiResult<List<WorkoutEventDTO>> {
        return apiClient.safeApiCall<List<WorkoutEventDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.ADD_WORKOUT_EVENTS)
            setBody(request)
        }
    }

    suspend fun getCalendarEvents(request: GetCalendarEventsRequest, token: String): ApiResult<List<CalendarEventDTO>> {
        return apiClient.safeApiCall<List<CalendarEventDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_CALENDAR_SCHEDULE)
            setBody(request)
        }
    }

    suspend fun getWorkoutEvents(token: String): ApiResult<List<WorkoutEventDTO>> {
        return apiClient.safeApiCall<List<WorkoutEventDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_WORKOUT_EVENTS)
        }
    }
}