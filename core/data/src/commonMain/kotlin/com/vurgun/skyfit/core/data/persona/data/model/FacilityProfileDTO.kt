package com.vurgun.skyfit.core.data.persona.data.model

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
data class ExploreFacilityProfileDTO(
    val gymId: Int,
    val profilePhoto: String?,
    val backgroundImage: String?,
    val gymName: String,
    @SerialName(value = "gymAdress") val address: String,
    val bio: String,
    val trainerCount: Int,
    val memberCount: Int,
    val point: Float?,
)