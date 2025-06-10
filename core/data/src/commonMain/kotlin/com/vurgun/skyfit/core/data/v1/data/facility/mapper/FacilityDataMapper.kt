package com.vurgun.skyfit.core.data.v1.data.facility.mapper

import com.vurgun.skyfit.core.data.serverImageFromPath
import com.vurgun.skyfit.core.data.utility.formatToServerDate
import com.vurgun.skyfit.core.data.utility.formatToServerTime
import com.vurgun.skyfit.core.data.utility.parseServerToDateOnly
import com.vurgun.skyfit.core.data.v1.data.facility.model.CreateLessonRequest
import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityMemberDTO
import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityProfileDTO
import com.vurgun.skyfit.core.data.v1.data.facility.model.UpdateLessonRequest
import com.vurgun.skyfit.core.data.v1.data.lesson.model.LessonParticipantDTO
import com.vurgun.skyfit.core.data.v1.data.trainer.model.FacilityTrainerProfileDTO
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.global.model.BodyType
import com.vurgun.skyfit.core.data.v1.domain.global.model.Member
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonCreationInfo
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonParticipant
import com.vurgun.skyfit.core.data.v1.domain.lesson.model.LessonUpdateInfo
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.FacilityTrainerProfile

internal object FacilityDataMapper {

    fun FacilityTrainerProfileDTO.toDomainFacilityTrainerProfile(): FacilityTrainerProfile {
        return FacilityTrainerProfile(
            trainerId = trainerId,
            userId = userId,
            firstName = name,
            lastName = surname,
            bio = bio,
            height = height,
            weight = weight,
            birthday = birthday.parseServerToDateOnly(),
            genderName = genderName,
            bodyType = BodyType.fromId(bodyTypeId),
            bodyTypeName = typeName,
            profileImageUrl = serverImageFromPath(profilePhoto),
            backgroundImageUrl = serverImageFromPath(backgroundImage),
            lessonTypeCount = lessonTypeCount,
            followerCount = followerCount,
            videoCount = videoCount,
            point = point
        )
    }

    fun List<FacilityTrainerProfileDTO>.toFacilityTrainerProfiles(): List<FacilityTrainerProfile> {
        return this.map { it.toDomainFacilityTrainerProfile() }
    }

    fun FacilityProfileDTO.toDomainFacilityProfile(): FacilityProfile {
        return FacilityProfile(
            userId = userId,
            gymId = gymId,
            username = username,
            profileImageUrl = serverImageFromPath(profilePhoto),
            backgroundImageUrl = serverImageFromPath(backgroundImage),
            facilityName = gymName,
            gymAddress = address,
            bio = bio,
            trainerCount = gymTrainerCount,
            memberCount = gymMemberCount,
            point = point ?: 0f
        )
    }

    fun List<FacilityProfileDTO>.toDomainFacilityProfiles(): List<FacilityProfile> =
        this.map { it.toDomainFacilityProfile() }

    fun LessonParticipantDTO.toDomainLessonParticipant(): LessonParticipant {
        return LessonParticipant(
            lpId = lpId,
            userId = userId,
            lessonId = lessonId,
            firstName = name,
            lastName = surname,
            profileImageUrl = serverImageFromPath(profilePhoto),
            username = username,
            trainerEvaluation = trainerEvaluation
        )
    }

    fun List<LessonParticipantDTO>.toDomainLessonParticipants(): List<LessonParticipant> {
        return this.map { it.toDomainLessonParticipant() }
    }

    fun FacilityMemberDTO.toMemberDomain(): Member {
        return Member(
            userId = userId,
            profileImageUrl = serverImageFromPath(profilePhotoPath),
            username = username,
            name = name,
            surname = surname
        )
    }

    fun List<FacilityMemberDTO>.toMemberDomainList(): List<Member> = map { it.toMemberDomain() }

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
}