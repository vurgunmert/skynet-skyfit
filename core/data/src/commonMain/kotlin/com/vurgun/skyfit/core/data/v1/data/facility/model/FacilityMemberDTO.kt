package com.vurgun.skyfit.core.data.v1.data.facility.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacilityMemberDTO(
    val userId: Int,
    val nmId: Int,
    @SerialName("profilePhoto")
    val profilePhotoPath: String? = null,
    val username: String,
    val name: String,
    val surname: String,
    val status: Int,
    val statusName: String,
    val membershipPackage: FacilityMemberPackageDTO? = null,
    val usedLessonCount: Int? = null
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


@Serializable
data class AddPackageToMember(
    val userId: Int,
    val packageId: Int,
    val startDate: String, //"2025-06-19"
    val endDate: String? = null, //"2025-06-19"
    val lessonCount: Int? = null,
)

@Serializable
data class DeleteMemberPackage(
    val userId: Int,
    val packageId: Int
)

@Serializable
data class UpdateMemberPackage(
    val userId: Int,
    val gymId: Int,
    val packageId: Int
)