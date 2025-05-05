package com.vurgun.skyfit.core.data.mappers

import com.vurgun.skyfit.core.data.domain.model.BodyType
import com.vurgun.skyfit.core.data.domain.model.HeightUnitType
import com.vurgun.skyfit.core.data.domain.model.WeightUnitType
import com.vurgun.skyfit.core.data.utility.parseServerToDateOnly
import com.vurgun.skyfit.core.data.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.domain.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.data.domain.model.LessonParticipant
import com.vurgun.skyfit.core.data.domain.model.TrainerProfile
import com.vurgun.skyfit.core.data.domain.model.UserProfile
import com.vurgun.skyfit.core.data.model.FacilityProfileDto
import com.vurgun.skyfit.core.data.model.FacilityTrainerProfileDTO
import com.vurgun.skyfit.core.data.model.LessonParticipantDTO
import com.vurgun.skyfit.core.data.model.TrainerProfileDTO
import com.vurgun.skyfit.core.data.model.UserProfileDTO

object ProfileMapper {

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
            memberGymJoinedAt = gymJoinDate?.parseServerToDateOnly()
        )
    }

    fun TrainerProfileDTO.toDomainTrainerProfile(): TrainerProfile {
        return TrainerProfile(
            userId = userId,
            username = username,
            trainerId = trainerId,
            profileImageUrl = serverImageFromPath(profilePhoto),
            backgroundImageUrl = serverImageFromPath(backgroundImage),
            bio = bio,
            firstName = name,
            lastName = surname,
            gymId = gymId,
            gymName = gymName,
            postCount = postCount,
            lessonCount = lessonCount,
            followerCount = followerCount,
            point = point ?: 0f
        )
    }

    fun List<TrainerProfileDTO>.toTrainerProfiles(): List<TrainerProfile> = this.map { it.toDomainTrainerProfile() }

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

    fun FacilityProfileDto.toDomainFacilityProfile(): FacilityProfile {
        return FacilityProfile(
            userId = userId,
            gymId = gymId,
            username = username,
            profileImageUrl = serverImageFromPath(profilePhoto),
            backgroundImageUrl = serverImageFromPath(backgroundImage),
            facilityName = gymName,
            gymAddress = gymAdress,
            bio = bio,
            trainerCount = gymTrainerCount,
            memberCount = gymMemberCount,
            point = point
        )
    }

    fun LessonParticipantDTO.toDomainLessonParticipant(): LessonParticipant {
        return LessonParticipant(
            lpId = lpId,
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
}