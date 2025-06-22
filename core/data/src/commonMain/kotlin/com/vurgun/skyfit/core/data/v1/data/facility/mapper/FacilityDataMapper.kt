package com.vurgun.skyfit.core.data.v1.data.facility.mapper

import com.vurgun.skyfit.core.data.serverImageFromPath
import com.vurgun.skyfit.core.data.utility.formatToServerDate
import com.vurgun.skyfit.core.data.utility.formatToServerTime
import com.vurgun.skyfit.core.data.utility.parseServerToDateOnly
import com.vurgun.skyfit.core.data.v1.data.facility.model.*
import com.vurgun.skyfit.core.data.v1.data.lesson.mapper.LessonDataMapper.toDomainCategory
import com.vurgun.skyfit.core.data.v1.data.lesson.model.LessonParticipantDTO
import com.vurgun.skyfit.core.data.v1.data.trainer.model.FacilityTrainerProfileDTO
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityMemberPackage
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
            trainerCount = gymTrainerCount ?: 0,
            memberCount = gymMemberCount ?: 0,
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
            normalUserId = nmId,
            profileImageUrl = serverImageFromPath(profilePhotoPath),
            username = username,
            name = name,
            surname = surname,
            status = status,
            statusName = statusName,
            usedLessonCount = usedLessonCount ?: 0,
            membershipPackage = membershipPackage.toDomainPackage()
        )
    }

    fun List<FacilityMemberDTO>.toMemberDomainList(): List<Member> = map { it.toMemberDomain() }

    fun FacilityMemberPackageDTO?.toDomainPackage(): FacilityMemberPackage? {
        return this?.run {
            FacilityMemberPackage(
                memberPackageId = memberPackageId,
                packageId = packageId,
                packageName = packageName,
                startDate = startDate.parseServerToDateOnly(),
                endDate = endDate?.parseServerToDateOnly(),
                lessonCount = lessonCount,
                usedLessonCount = usedLessonCount ?: 0,
                categories = categories?.let { it.map { category -> category.toDomainCategory() } } ?: emptyList()
            )
        }
    }

    internal fun LessonCreationInfo.toCreateLessonRequest(): CreateLessonRequestDTO {
        return CreateLessonRequestDTO(
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
            participantType = participantType,
            categories = categoryIds
        )
    }

    internal fun LessonUpdateInfo.toUpdateLessonRequest(): UpdateLessonRequestDTO {
        return UpdateLessonRequestDTO(
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
            participants = participantsIds,
            categories = categoryIds
        )
    }
}