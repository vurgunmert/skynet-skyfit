package com.vurgun.skyfit.data.user.mappers

import com.vurgun.skyfit.data.core.domain.model.BodyType
import com.vurgun.skyfit.data.core.domain.model.HeightUnitType
import com.vurgun.skyfit.data.core.domain.model.WeightUnitType
import com.vurgun.skyfit.data.core.utility.parseServerToDateOnly
import com.vurgun.skyfit.data.user.domain.FacilityProfile
import com.vurgun.skyfit.data.user.domain.FacilityTrainerProfile
import com.vurgun.skyfit.data.user.domain.TrainerProfile
import com.vurgun.skyfit.data.user.domain.UserProfile
import com.vurgun.skyfit.data.user.model.FacilityProfileDto
import com.vurgun.skyfit.data.user.model.FacilityTrainerProfileDto
import com.vurgun.skyfit.data.user.model.TrainerProfileDto
import com.vurgun.skyfit.data.user.model.UserProfileDto

object ProfileMapper {

    fun UserProfileDto.toDomainUserProfile(): UserProfile {
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
            memberGymId = 10, //gymId
            memberGymJoinedAt = gymJoinDate?.parseServerToDateOnly()
        )
    }

    fun TrainerProfileDto.toDomainTrainerProfile(): TrainerProfile {
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

    fun List<TrainerProfileDto>.toTrainerProfiles(): List<TrainerProfile> = this.map { it.toDomainTrainerProfile() }

    fun FacilityTrainerProfileDto.toDomainFacilityTrainerProfile(): FacilityTrainerProfile {
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

    fun List<FacilityTrainerProfileDto>.toFacilityTrainerProfiles(): List<FacilityTrainerProfile> {
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
}