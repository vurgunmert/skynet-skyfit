package com.vurgun.skyfit.data.courses.mapper


import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.model.NewLesson
import com.vurgun.skyfit.data.courses.domain.model.UpdatedLesson
import com.vurgun.skyfit.data.courses.model.CreateLessonRequest
import com.vurgun.skyfit.data.courses.model.LessonDTO
import com.vurgun.skyfit.data.courses.model.UpdateLessonRequest
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

internal fun UpdatedLesson.toUpdateRequest(): UpdateLessonRequest {
    return UpdateLessonRequest(
        lessonId = lessonId,
        iconId = iconId,
        trainerNote = trainerNote,
        trainerId = trainerId,
        startDate = startDateTime.date.toString(), // ISO format
        endDate = endDateTime.date.toString(),
        startTime = startDateTime.time.toString().substring(0, 5), // hh:mm
        endTime = endDateTime.time.toString().substring(0, 5),
        quota = quota,
        lastCancelTime = lastCancelableAt.toString(), // backend expects datetime string
        isRequiredAppointment = isRequiredAppointment,
        price = price,
        participantType = participantType,
        participants = participants
    )
}


internal fun NewLesson.toCreateRequest(): CreateLessonRequest {
    return CreateLessonRequest(
        gymId = gymId,
        iconId = iconId,
        title = title,
        trainerNote = trainerNote,
        trainerId = trainerId,
        startDate = startDateTime.date.toString(), // "2025-04-30"
        endDate = endDateTime.date.toString(),
        startTime = startDateTime.time.toString().substring(0, 5), // "07:30"
        endTime = endDateTime.time.toString().substring(0, 5),
        repetitionType = repetitionType,
        repetition = repetition,
        quota = quota,
        lastCancelTime = lastCancelableHoursBefore,
        isRequiredAppointment = isRequiredAppointment,
        price = price,
        participantType = participantType
    )
}


internal fun LessonDTO.toDomain(): Lesson {
    // Extract LocalDate
    val parsedStartDate = LocalDate.parse(startDate.substringBefore("T"))
    val parsedEndDate = LocalDate.parse(endDate.substringBefore("T"))

    // Extract LocalTime (from "07:30:00")
    val parsedStartTime = LocalTime.parse(startTime.substring(0, 5))
    val parsedEndTime = LocalTime.parse(endTime.substring(0, 5))

    // Combine to LocalDateTime
    val parsedStartDateTime = LocalDateTime(parsedStartDate, parsedStartTime)
    val parsedEndDateTime = LocalDateTime(parsedEndDate, parsedEndTime)

    // Parse lastCancelTime from ISO string
    val parsedLastCancelableAt = Instant.parse(lastCancelTime)

    return Lesson(
        lessonId = lessonId,
        iconId = lessonId, // You can update this if you link to SkyFitIcon later
        title = typeName,
        startDateTime = parsedStartDateTime,
        endDateTime = parsedEndDateTime,
        lastCancelableAt = parsedLastCancelableAt,

        startDate = parsedStartDate,
        endDate = parsedEndDate,
        startTime = parsedStartTime,
        endTime = parsedEndTime,

        capacityRatio = capacity,
        trainerId = trainerId,
        trainerFullName = "$name $surname",
        facilityName = gymName,
        trainerNote = trainerNote,
        price = price,
        status = status,
        statusName = statusName
    )
}

internal fun List<LessonDTO>.toDomain(): List<Lesson> = map { it.toDomain() }
