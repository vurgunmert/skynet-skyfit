package com.vurgun.skyfit.data.courses.mapper


import com.vurgun.skyfit.core.data.utility.formatToServerDate
import com.vurgun.skyfit.core.data.utility.formatToServerTime
import com.vurgun.skyfit.core.data.utility.parseServerToDateOnly
import com.vurgun.skyfit.core.data.utility.parseServerToHHMMTime
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.model.LessonCreationInfo
import com.vurgun.skyfit.data.courses.domain.model.LessonUpdateInfo
import com.vurgun.skyfit.data.courses.domain.model.ScheduledLessonDetail
import com.vurgun.skyfit.data.courses.model.CreateLessonRequest
import com.vurgun.skyfit.data.courses.model.LessonDTO
import com.vurgun.skyfit.data.courses.model.ScheduledLessonDetailDTO
import com.vurgun.skyfit.data.courses.model.UpdateLessonRequest
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

internal fun LessonCreationInfo.toCreateLessonRequest(): CreateLessonRequest {
    return CreateLessonRequest(
        gymId = gymId,
        iconId = iconId,
        title = title,
        trainerNote = trainerNote,
        trainerId = trainerId,
        startDate = startDateTime.formatToServerDate(), // " YYYY-MM-DD HH:mm:ss"
        endDate = endDateTime.formatToServerDate(),
        startTime = startDateTime.formatToServerTime(), // "HH:mm:ss"
        endTime = endDateTime.formatToServerTime(),
        repetitionType = repetitionType,
        repetition = repetition,
        quota = quota,
        lastCancelTime = lastCancelableHoursBefore,
        isRequiredAppointment = isRequiredAppointment,
        price = price,
        participantType = participantType
    )
}

internal fun LessonUpdateInfo.toUpdateLessonRequest(): UpdateLessonRequest {
    return UpdateLessonRequest(
        lessonId = lessonId,
        iconId = iconId,
        trainerNote = trainerNote,
        trainerId = trainerId,
        startDate = startDateTime.formatToServerDate(),
        endDate = endDateTime.formatToServerDate(),
        startTime = startDateTime.formatToServerTime(),
        endTime = endDateTime.formatToServerTime(),
        quota = quota,
        lastCancelTime = lastCancelableHoursBefore,
        isRequiredAppointment = isRequiredAppointment,
        price = price,
        participantType = participantType,
        participants = participantsIds
    )
}

internal fun LessonDTO.toLessonDomain(): Lesson {
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
        iconId = lessonIcon,
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

internal fun List<LessonDTO>.toLessonDomainList(): List<Lesson> = map { it.toLessonDomain() }

internal fun ScheduledLessonDetailDTO.toScheduledLessonDetail(): ScheduledLessonDetail {
    return ScheduledLessonDetail(
        lessonId = lessonId,
        title = typeName,
        startDate = startDate.parseServerToDateOnly(),
        endDate = endDate.parseServerToDateOnly(),
        startTime = startTime.parseServerToHHMMTime(),
        endTime = endTime.parseServerToHHMMTime(),
        trainerFullName = "$trainerName $trainerSurname",
        facilityName = gymName,
        trainerNote = trainerNote,
        participantCount = totalParticipants,
        status = status,
        statusName = statusName
    )
}