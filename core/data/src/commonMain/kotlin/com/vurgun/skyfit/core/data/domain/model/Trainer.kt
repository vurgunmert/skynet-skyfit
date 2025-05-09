package com.vurgun.skyfit.core.data.domain.model

data class Trainer(
    val userId: Int,
    val trainerId: Int,
    val profileImageUrl: String? = null,
    val username: String,
    val fullName: String
)