package com.vurgun.skyfit.core.data.v1.data.trainer.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainerProfileDTO(
    val userId: Int,
    val username: String,
    val trainerId: Int,
    val profilePhoto: String?,
    val backgroundImage: String?,
    val bio: String,
    val name: String,
    val surname: String,
    val gymId: Int?,
    val gymName: String?,
    val postCount: Int? = null,
    val lessonCount: Int? = null,
    val followerCount: Int? = null,
    val point: Float? = null
)

@Serializable
data class FacilityTrainerProfileDTO(
    val trainerId: Int,
    val userId: Int,
    val name: String,
    val surname: String,
    val bio: String,
    val height: Int,
    val weight: Int,
    val birthday: String,
    val genderName: String,
    val bodyTypeId: Int,
    val typeName: String,
    val profilePhoto: String?,
    val backgroundImage: String?,
    val lessonTypeCount: Int,
    val followerCount: Int,
    val videoCount: Int,
    val point: Float,
)

@Serializable
data class GetTrainerProfileRequestDTO(val trainerId: Int)

@Serializable
data class GetFacilityTrainerProfilesRequestDTO(val gymId: Int)

@Serializable
data class UpdateTrainerProfileRequestDTO(
    val trainerId: Int,
    val profilePhoto: ByteArray?,
    val backgroundImage: ByteArray?,
    val username: String,
    val name: String,
    val surname: String,
    val bio: String,
    val profileTags: List<Int>
)