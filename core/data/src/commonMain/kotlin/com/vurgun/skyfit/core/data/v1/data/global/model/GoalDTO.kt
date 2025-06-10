package com.vurgun.skyfit.core.data.v1.data.global.model

import kotlinx.serialization.Serializable

@Serializable
data class GoalDTO(
    val goalId: Int,
    val goalName: String,
)
