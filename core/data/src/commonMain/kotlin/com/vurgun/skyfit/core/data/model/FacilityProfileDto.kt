package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FacilityProfileDto(
    val userId: Int,
    val gymId: Int,
    val username: String,
    val profilePhoto: String?,
    val backgroundImage: String,
    val gymName: String,
    val gymAdress: String,
    val bio: String,
    val gymTrainerCount: Int,
    val gymMemberCount: Int,
    val point: Float?,
)
