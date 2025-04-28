package com.vurgun.skyfit.core.data.domain.model

data class FacilityProfile(
    val userId: Int,
    val gymId: Int,
    val username: String,
    val profileImageUrl: String?,
    val backgroundImageUrl: String,
    val facilityName: String,
    val gymAddress: String,
    val bio: String,
    val trainerCount: Int,
    val memberCount: Int,
    val point: Float?,
)

