package com.vurgun.skyfit.data.settings.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainerDto(
    val userId: Int,
    val trainerId: Int,
    val profilePhotoPath: String? = null,
    val username: String,
    val name: String,
    val surname: String
)