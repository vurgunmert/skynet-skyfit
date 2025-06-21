package com.vurgun.skyfit.core.data.v1.data.user.model

import com.vurgun.skyfit.core.data.v1.data.facility.model.FacilityMemberPackageDTO
import kotlinx.serialization.Serializable

@Serializable
data class UserProfileDTO(
    val userId: Int,
    val nmId: Int,
    val profilePhoto: String?,
    val backgroundImage: String?,
    val height: Int,
    val heightUnit: Int,
    val weight: Int,
    val weightUnit: Int,
    val bodyTypeId: Int,
    val typeName: String,
    val name: String,
    val surname: String,
    val username: String,
    val gymId: Int? = null,
    val gymName: String? = null,
    val gymJoinDate: String? = null,
    val membershipPackage: FacilityMemberPackageDTO? = null,
    val usedLessonCount: Int? = null
)

@Serializable
data class GetUserProfileRequestDTO(val nmId: Int)

@Serializable
data class UpdateUserProfileRequestDTO(
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
