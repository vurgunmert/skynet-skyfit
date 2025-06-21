package com.vurgun.skyfit.core.data.v1.data.workout.model

import kotlinx.serialization.Serializable

@Serializable
class WorkoutCategoryDTO(
    val id: Int,
    val code: String,
    val name: String
)