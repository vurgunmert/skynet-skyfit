package com.vurgun.skyfit.core.data.v1.domain.global.model

import kotlinx.datetime.Instant

data class UserChallenge(
    val challengeId: String,
    val userId: String,
    val joinedDate: Instant,
    val progressValue: Float,
    val completed: Boolean,
    val completedDate: Instant? = null
)