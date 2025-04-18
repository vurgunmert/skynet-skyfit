package com.vurgun.skyfit.data.courses.mapper

import com.vurgun.skyfit.data.courses.domain.model.Appointment
import com.vurgun.skyfit.data.courses.model.AppointmentDTO
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

internal fun AppointmentDTO.toLessonDomain(): Appointment {
    val parsedStartDate = LocalDate.parse(startDate)
    val parsedEndDate = LocalDate.parse(endDate)
    val parsedStartTime = LocalTime.parse(startTime)
    val parsedEndTime = LocalTime.parse(endTime)
    val startDateTime = LocalDateTime(parsedStartDate, parsedStartTime)
    val endDateTime = LocalDateTime(parsedEndDate, parsedEndTime)
    val cancelInstant = Instant.parse(lastCancelTime)
    val joinedInstant = Instant.parse(joinedAt)

    return Appointment(
        lessonId = lessonId,
        iconId = lessonIcon,
        title = typeName,

        startDateTime = startDateTime,
        endDateTime = endDateTime,
        lastCancelableAt = cancelInstant,

        startDate = parsedStartDate,
        endDate = parsedEndDate,
        startTime = parsedStartTime,
        endTime = parsedEndTime,

        trainerId = trainerId,
        trainerFullName = "$name $surname",
        facilityName = gymName,
        trainerNote = trainerNote,

        joinedAt = joinedInstant,
        price = price,
        lessonStatus = lessonStatus,
        participantStatus = participantStatus,
        statusName = statusName
    )
}

internal fun List<AppointmentDTO>.toLessonDomain() = this.map { it.toLessonDomain() }