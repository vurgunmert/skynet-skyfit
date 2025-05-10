package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GetUserProfileRequest(val nmId: Int)

@Serializable
data class GetTrainerProfileRequest(val trainerId: Int)

@Serializable
data class GetFacilityTrainerProfilesRequest(val gymId: Int)

@Serializable
data class GetFacilityProfileRequest(val gymId: Int)

@Serializable
data class UpdateUserProfileRequest(
    val userId: Int,
    val profilePhoto: ByteArray?,
    val backgroundImage: ByteArray?,
    val username: String,
    val name: String,
    val surname: String,
    val height: Int,
    val weight: Int,
    val bodyTypeId: Int
)

@Serializable
data class UpdateTrainerProfileRequest(
    val trainerId: Int,
    val profilePhoto: ByteArray?,
    val backgroundImage: ByteArray?,
    val username: String,
    val name: String,
    val surname: String,
    val bio: String,
    val profileTags: List<Int>
)

@Serializable
data class UpdateFacilityProfileRequest(
    val gymId: Int,
    val backgroundImage: ByteArray?,
    val name: String,
    val address: String,
    val bio: String,
    val profileTags: List<Int>
)

@Serializable
data class DeleteProfileTagRequest(
    val tagId: Int
)