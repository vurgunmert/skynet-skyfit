package com.vurgun.skyfit.core.data.v1.data.facility.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacilityMemberDTO(
    val userId: Int,
    @SerialName("profilePhoto") val profilePhotoPath: String? = null,
    val username: String,
    val name: String,
    val surname: String
)

@Serializable
data class AddGymMemberRequest(val gymId: Int, val userId: Int)

@Serializable
data class GetGymMembersRequest(val gymId: Int)

@Serializable
data class GetPlatformMembersRequest(val gymId: Int)

@Serializable
data class DeleteGymMemberRequest(val gymId: Int, val userId: Int)


@Serializable
data class AddGymTrainerRequest(val gymId: Int, val userId: Int)

@Serializable
data class GetGymTrainersRequest(val gymId: Int)

@Serializable
data class DeleteGymTrainerRequest(val gymId: Int, val userId: Int)

@Serializable
data class GetPlatformTrainersRequest(val gymId: Int)