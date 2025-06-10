package com.vurgun.skyfit.core.data.v1.domain.trainer.model

data class TrainerPreview(
    val userId: Int,
    val trainerId: Int,
    val profileImageUrl: String? = null,
    val username: String,
    val fullName: String
)