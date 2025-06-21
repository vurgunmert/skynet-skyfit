package com.vurgun.skyfit.core.data.v1.data.global.model

data class UserChallengeDTO(
    val challengeId: String,
    val userId: String,
    val joinedDate: String,
    val progressValue: Float,
    val completed: Boolean,
    val completedDate: String? = null
)
