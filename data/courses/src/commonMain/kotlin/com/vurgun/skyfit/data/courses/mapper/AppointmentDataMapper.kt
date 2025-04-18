package com.vurgun.skyfit.data.courses.mapper

import com.vurgun.skyfit.data.courses.domain.model.Appointment
import com.vurgun.skyfit.data.courses.model.AppointmentDTO
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

internal fun AppointmentDTO.toLessonDomain(): Appointment {

    // Extract LocalDate
    val parsedStartDate = LocalDate.parse(startDate.substringBefore("T"))
    val parsedEndDate = LocalDate.parse(endDate.substringBefore("T"))

    // Extract LocalTime (from "07:30:00")
    val parsedStartTime = LocalTime.parse(startTime.substring(0, 5))
    val parsedEndTime = LocalTime.parse(endTime.substring(0, 5))

    // Combine to LocalDateTime
    val parsedStartDateTime = LocalDateTime(parsedStartDate, parsedStartTime)
    val parsedEndDateTime = LocalDateTime(parsedEndDate, parsedEndTime)

    val cancelInstant = Instant.parse(lastCancelTime)
    val joinedInstant = Instant.parse(joinedAt)

    return Appointment(
        lessonId = lessonId,
        iconId = lessonIcon,
        title = typeName,

        startDateTime = parsedStartDateTime,
        endDateTime = parsedEndDateTime,
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
        participantStatus = participantStatus
    )
}

internal fun List<AppointmentDTO>.toLessonDomain() = this.map { it.toLessonDomain() }