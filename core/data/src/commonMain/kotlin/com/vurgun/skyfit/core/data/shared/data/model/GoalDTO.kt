package com.vurgun.skyfit.core.data.shared.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GoalDTO(
    val goalId: Int,
    val goalName: String,
)
