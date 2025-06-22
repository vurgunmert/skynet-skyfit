package com.vurgun.skyfit.core.data.v1.domain.global.model

import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityMemberPackage

data class Member(
    val userId: Int,
    val normalUserId: Int,
    val profileImageUrl: String? = null,
    val username: String,
    val name: String,
    val surname: String,
    val status: Int,
    val statusName: String,
    val membershipPackage: FacilityMemberPackage? = null,
    val usedLessonCount: Int,
) {
    val fullName: String = "$name $surname"
}