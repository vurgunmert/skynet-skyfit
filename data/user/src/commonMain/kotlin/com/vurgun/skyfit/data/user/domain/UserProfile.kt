package com.vurgun.skyfit.data.user.domain

import com.vurgun.skyfit.data.core.domain.model.BodyType
import com.vurgun.skyfit.data.core.domain.model.HeightUnitType
import com.vurgun.skyfit.data.core.domain.model.WeightUnitType

data class UserProfile(
    val userId: Int,
    val normalUserId: Int,
    val profileImageUrl: String?,
    val backgroundImageUrl: String?,
    val height: Int,
    val heightUnit: HeightUnitType,
    val weight: Int,
    val weightUnit: WeightUnitType,
    val bodyType: BodyType,
    val bodyTypeName: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val memberGymId: Int?
) {
    val fullName = "$firstName $lastName"
}
