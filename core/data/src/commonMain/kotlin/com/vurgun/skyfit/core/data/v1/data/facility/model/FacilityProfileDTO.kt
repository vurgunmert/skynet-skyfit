package com.vurgun.skyfit.core.data.v1.data.facility.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacilityProfileDTO(
    val userId: Int,
    val gymId: Int,
    val username: String,
    val profilePhoto: String?,
    val backgroundImage: String?,
    val gymName: String,
    @SerialName(value = "gymAdress") val address: String,
    val bio: String,
    val gymTrainerCount: Int,
    val gymMemberCount: Int,
    val point: Float?,
)

@Serializable
data class GetFacilityProfileRequest(val gymId: Int)


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