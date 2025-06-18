package com.vurgun.skyfit.core.data.v1.data.lesson.mapper

import com.vurgun.skyfit.core.data.v1.domain.lesson.model.Lesson
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.ScheduledLessonDetail
import com.vurgun.skyfit.core.data.utility.parseServerToDateOnly
import com.vurgun.skyfit.core.data.utility.parseServerToHHMMTime
import com.vurgun.skyfit.core.data.v1.data.lesson.model.LessonDTO
import com.vurgun.skyfit.core.data.v1.data.lesson.model.ScheduledLessonDetailDTO
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

object LessonDataMapper {

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
            participantCount = totalParticipants ?: 0,
            status = status,
            statusName = statusName
        )
    }
}