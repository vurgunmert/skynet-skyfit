package com.vurgun.skyfit.core.data.schedule.data.mapper

import com.vurgun.skyfit.core.data.schedule.data.model.CalendarEventDTO
import com.vurgun.skyfit.core.data.schedule.data.model.WorkoutEventDTO
import com.vurgun.skyfit.core.data.schedule.domain.model.CalendarEvent
import com.vurgun.skyfit.core.data.schedule.domain.model.WorkoutEvent
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal object CalendarEventMapper {

    fun CalendarEventDTO.toDomainCalendarEvent(): CalendarEvent {
        val startDateTime =
            Instant.parse(this.startDate).toLocalDateTime(TimeZone.UTC)
        val endDateTime =
            Instant.parse(this.endDate).toLocalDateTime(TimeZone.UTC)

        return CalendarEvent(
            calendarEventId = this.calendarEventId,
            userId = this.userId,
            name = this.eventName,
            workoutEventId = this.eventId,
            startDateTime = startDateTime,
            endDateTime = endDateTime,
            lessonId = this.lessonId,
            lessonIcon = this.lessonIcon,
            trainerNote = this.trainerNote,
            trainerFullName = "${this.trainerName} ${this.trainerSurname}",
            gymName = this.gymName,
            username = this.username
        )
    }

    fun List<CalendarEventDTO>.toDomainCalendarEvents(): List<CalendarEvent> {
        return map { it.toDomainCalendarEvent() }
    }

    fun WorkoutEventDTO.toDomainWorkoutEvent(): WorkoutEvent {
        return WorkoutEvent(
            id = this.workoutEventId,
            name = this.eventName
        )
    }

    fun List<WorkoutEventDTO>.toDomainWorkoutEvents(): List<WorkoutEvent> {
        return map { it.toDomainWorkoutEvent() }
    }

}