package com.vurgun.skyfit.core.data.persona.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrainerDTO(
    val userId: Int,
    val trainerId: Int,
    @SerialName(value = "profilePhoto") val profilePhotoPath: String? = null,
    val username: String,
    val name: String,
    val surname: String
)