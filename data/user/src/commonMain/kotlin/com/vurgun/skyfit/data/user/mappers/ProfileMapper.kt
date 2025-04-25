package com.vurgun.skyfit.data.user.mappers

import com.vurgun.skyfit.data.core.domain.model.BodyType
import com.vurgun.skyfit.data.core.domain.model.HeightUnitType
import com.vurgun.skyfit.data.core.domain.model.WeightUnitType
import com.vurgun.skyfit.data.core.utility.parseServerToDateOnly
import com.vurgun.skyfit.data.user.domain.FacilityProfile
import com.vurgun.skyfit.data.user.domain.TrainerProfile
import com.vurgun.skyfit.data.user.domain.UserProfile
import com.vurgun.skyfit.data.user.model.FacilityProfileDto
import com.vurgun.skyfit.data.user.model.TrainerProfileDto
import com.vurgun.skyfit.data.user.model.UserProfileDto

object ProfileMapper {

    fun UserProfileDto.toDomain(): UserProfile {
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

    fun TrainerProfileDto.toDomain(): TrainerProfile {
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
            followerCount = followerCount
        )
    }

    fun FacilityProfileDto.toDomain(): FacilityProfile {
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