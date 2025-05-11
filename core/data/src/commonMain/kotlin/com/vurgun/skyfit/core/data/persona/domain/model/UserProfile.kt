package com.vurgun.skyfit.core.data.persona.domain.model

import com.vurgun.skyfit.core.data.shared.domain.model.HeightUnitType
import com.vurgun.skyfit.core.data.shared.domain.model.WeightUnitType
import kotlinx.datetime.LocalDate

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
    val memberGymId: Int?,
    val memberGymJoinedAt: LocalDate?,
    val memberDurationDays: Int?
) {
    val fullName = "$firstName $lastName"
}
