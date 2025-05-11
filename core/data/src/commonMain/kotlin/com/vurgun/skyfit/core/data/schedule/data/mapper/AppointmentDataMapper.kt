package com.vurgun.skyfit.core.data.schedule.data.mapper

import com.vurgun.skyfit.core.data.utility.parseServerToDateOnly
import com.vurgun.skyfit.core.data.utility.parseServerToHHMMTime
import com.vurgun.skyfit.core.data.schedule.domain.model.Appointment
import com.vurgun.skyfit.core.data.schedule.domain.model.AppointmentDetail
import com.vurgun.skyfit.core.data.schedule.data.model.AppointmentDTO
import com.vurgun.skyfit.core.data.schedule.data.model.AppointmentDetailDTO
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
        lpId = lpId,
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
        participantStatus = participantStatus,
        status = status,
        statusName = statusName,
        quotaInfo = quotaInfo,
    )
}

internal fun List<AppointmentDTO>.toLessonDomain() = this.map { it.toLessonDomain() }

internal fun AppointmentDetailDTO.toAppointmentDetailDomain(): AppointmentDetail {
    return AppointmentDetail(
        lpId = lpId,
        lessonId = lessonId,
        nmId = nmId,
        status = status,
        statusName = statusName,
        title = typeName,
        startDate = startDate.parseServerToDateOnly(),
        startTime = startTime.parseServerToHHMMTime(),
        endDate = endDate.parseServerToDateOnly(),
        endTime = endTime.parseServerToHHMMTime(),
        trainerFullName = "$trainerName $trainerSurname",
        trainerNote = trainerNote,
        gymName = gymName,
        participantCount = totalParticipants
    )
}