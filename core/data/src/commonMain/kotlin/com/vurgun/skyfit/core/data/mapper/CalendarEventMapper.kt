package com.vurgun.skyfit.core.data.mapper

import com.vurgun.skyfit.core.data.domain.model.CalendarEvent
import com.vurgun.skyfit.core.data.domain.model.WorkoutEvent
import com.vurgun.skyfit.core.data.model.CalendarEventDTO
import com.vurgun.skyfit.core.data.model.WorkoutEventDTO
import kotlinx.datetime.LocalDateTime

internal object CalendarEventMapper {

    fun CalendarEventDTO.toDomainCalendarEvent(): CalendarEvent {
        return CalendarEvent(
            calendarEventId = this.calendarEventId,
            userId = this.userId,
            name = this.eventName,
            workoutEventId = this.eventId,
            startDate = LocalDateTime.parse(this.startDate),
            endDate = LocalDateTime.parse(this.endDate),
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