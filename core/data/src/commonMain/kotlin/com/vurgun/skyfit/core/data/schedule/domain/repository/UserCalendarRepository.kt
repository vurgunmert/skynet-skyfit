package com.vurgun.skyfit.core.data.schedule.domain.repository

import com.vurgun.skyfit.core.data.schedule.domain.model.CalendarEvent
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutEvent

interface UserCalendarRepository {
    suspend fun addCalendarEvents(eventId: Int, eventName: String, startDate: String, endDate: String): Result<Unit>
    suspend fun getCalendarEvents(startDate: String? = null, endDate: String? = null): Result<List<CalendarEvent>>
    suspend fun getWorkoutEvents(): Result<List<WorkoutEvent>>
}