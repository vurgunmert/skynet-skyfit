package com.vurgun.skyfit.core.data.v1.data.explore.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacilityPreviewDTO(
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