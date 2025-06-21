package com.vurgun.skyfit.core.data.v1.data.trainer.mapper

import com.vurgun.skyfit.core.data.serverImageFromPath
import com.vurgun.skyfit.core.data.v1.data.trainer.model.TrainerDTO
import com.vurgun.skyfit.core.data.v1.data.trainer.model.TrainerProfileDTO
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerPreview
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile

internal object TrainerDataMapper {

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
            postCount = postCount ?: 0,
            lessonCount = lessonCount ?: 0,
            followerCount = followerCount ?: 0,
            point = point ?: 0f
        )
    }

    fun List<TrainerProfileDTO>.toDomainTrainerProfiles(): List<TrainerProfile> = this.map { it.toDomainTrainerProfile() }

    fun TrainerDTO.toTrainerDomain(): TrainerPreview {
        return TrainerPreview(
            userId = userId,
            trainerId = trainerId,
            profileImageUrl = serverImageFromPath(profilePhotoPath),
            username = username,
            fullName = "$name $surname"
        )
    }
    fun List<TrainerDTO>.toTrainerDomainList(): List<TrainerPreview> = map { it.toTrainerDomain() }

}