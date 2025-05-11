package com.vurgun.skyfit.core.data.schedule.data.repository

import com.vurgun.skyfit.core.data.schedule.data.mapper.CalendarEventMapper.toDomainCalendarEvents
import com.vurgun.skyfit.core.data.schedule.data.mapper.CalendarEventMapper.toDomainWorkoutEvents
import com.vurgun.skyfit.core.data.schedule.data.model.AddCalendarEventRequest
import com.vurgun.skyfit.core.data.schedule.data.model.GetCalendarEventsRequest
import com.vurgun.skyfit.core.data.schedule.data.service.UserCalendarApiService
import com.vurgun.skyfit.core.data.schedule.domain.model.CalendarEvent
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutEvent
import com.vurgun.skyfit.core.data.schedule.domain.repository.UserCalendarRepository
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class UserCalendarRepositoryImpl(
    private val tokenManager: TokenManager,
    private val dispatchers: DispatcherProvider,
    private val apiService: UserCalendarApiService,
) : UserCalendarRepository {

    override suspend fun addCalendarEvents(
        eventId: Int,
        eventName: String,
        startDate: String,
        endDate: String
    ): Result<Unit> = ioResult(dispatchers) {
        val calendarRequest = AddCalendarEventRequest(eventId, eventName, startDate, endDate)
        val token = tokenManager.getTokenOrThrow()
        apiService.addCalendarEvents(calendarRequest, token).mapOrThrow { Unit }
    }

    override suspend fun getCalendarEvents(
        startDate: String?,
        endDate: String?
    ): Result<List<CalendarEvent>> = ioResult(dispatchers) {
        val calendarRequest = GetCalendarEventsRequest(startDate, endDate)
        val token = tokenManager.getTokenOrThrow()
        apiService.getCalendarEvents(calendarRequest, token)
            .mapOrThrow { it.toDomainCalendarEvents() }
    }

    override suspend fun getWorkoutEvents(): Result<List<WorkoutEvent>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getWorkoutEvents(token).mapOrThrow { it.toDomainWorkoutEvents() }
    }
}