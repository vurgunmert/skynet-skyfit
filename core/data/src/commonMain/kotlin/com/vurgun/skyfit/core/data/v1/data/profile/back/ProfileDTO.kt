package com.vurgun.skyfit.core.data.v1.data.profile.back

import com.vurgun.skyfit.core.data.v1.profile.back.model.CharacterDTO
import com.vurgun.skyfit.core.data.v1.profile.back.model.FacilityPreviewDTO
import com.vurgun.skyfit.core.data.v1.data.global.model.TypeDescriptionDTO
import com.vurgun.skyfit.core.data.v1.data.global.model.UnitDescriptionDTO

data class ProfileDTO(
    val accountId: String? = null,
    val userId: String? = null,
    val trainerId: String? = null,
    val facilityId: String? = null,
    val selectedRoleId: Int = 1,

    val memberedFacilityPreview: FacilityPreviewDTO? = null,
    val memberedFacilityJoinDate: String? = null, // ISO-8601 string or convert to Instant

    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val fullName: String? = null,

    val backgroundImagePath: String? = null,
    val profileImagePath: String? = null,

    val character: CharacterDTO? = null,

    val height: Float? = null,
    val heightUnit: UnitDescriptionDTO? = null,

    val weight: Float? = null,
    val weightUnit: UnitDescriptionDTO? = null,

    val gender: TypeDescriptionDTO? = null,
    val bodyType: TypeDescriptionDTO? = null,

    val bio: String? = null,
    val location: String? = null,

    val selectedTrophyIds: List<Int> = emptyList(),
    val selectedGoalIds: List<Int> = emptyList(),

    val followerCount: Int? = null,
    val followingCount: Int? = null,
    val lessonTypeCount: Int? = null,
    val videoCount: Int? = null,
    val postCount: Int? = null,
    val rating: Double? = null
)