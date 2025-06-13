package com.vurgun.skyfit.core.data.v1.data.user.mapper

import com.vurgun.skyfit.core.data.v1.domain.user.model.Appointment
import com.vurgun.skyfit.core.data.v1.domain.user.model.AppointmentDetail
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.utility.parseServerToDateOnly
import com.vurgun.skyfit.core.data.utility.parseServerToHHMMTime
import com.vurgun.skyfit.core.data.serverImageFromPath
import com.vurgun.skyfit.core.data.v1.data.user.model.AppointmentDTO
import com.vurgun.skyfit.core.data.v1.data.user.model.AppointmentDetailDTO
import com.vurgun.skyfit.core.data.v1.data.user.model.CalendarEventDTO
import com.vurgun.skyfit.core.data.v1.data.user.model.CalendarWorkoutEventDTO
import com.vurgun.skyfit.core.data.v1.data.user.model.UserProfileDTO
import com.vurgun.skyfit.core.data.v1.domain.global.model.BodyType
import com.vurgun.skyfit.core.data.v1.domain.global.model.HeightUnitType
import com.vurgun.skyfit.core.data.v1.domain.global.model.WeightUnitType
import com.vurgun.skyfit.core.data.v1.domain.user.model.CalendarEvent
import com.vurgun.skyfit.core.data.v1.domain.user.model.UserProfile
import com.vurgun.skyfit.core.data.v1.data.workout.WorkoutEvent
import com.vurgun.skyfit.core.data.v1.statistics.back.model.UserStatisticsDTO
import com.vurgun.skyfit.core.data.v1.data.statistics.front.UserStatistics
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime

internal object UserDataMapper {

    fun UserProfileDTO.toDomainUserProfile(): UserProfile {
        return UserProfile(
            userId = userId,
            normalUserId = nmId,
            profileImageUrl = serverImageFromPath(profilePhoto),
            backgroundImageUrl = serverImageFromPath(backgroundImage),
            height = height,
            heightUnit = HeightUnitType.from(heightUnit),
            weight = weight,
            weightUnit = WeightUnitType.from(weightUnit),
            bodyType = BodyType.fromId(bodyTypeId),
            bodyTypeName = typeName,
            firstName = name,
            lastName = surname,
            username = username,
            memberGymId = gymId,
            memberGymJoinedAt = gymJoinDate?.parseServerToDateOnly(),
            memberDurationDays = gymJoinDate?.parseServerToDateOnly()?.daysUntil(LocalDate.now())
        )
    }

    fun UserStatisticsDTO.toDomainStatistics(): UserStatistics {
        return UserStatistics()
    }

    //appointment
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

    //calendar
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

    fun CalendarWorkoutEventDTO.toDomainWorkoutEvent(): WorkoutEvent {
        return WorkoutEvent(
            id = this.workoutEventId,
            name = this.eventName
        )
    }

    fun List<CalendarWorkoutEventDTO>.toDomainWorkoutEvents(): List<WorkoutEvent> {
        return map { it.toDomainWorkoutEvent() }
    }
}