package com.vurgun.skyfit.core.data.v1.domain.workout.repository

import com.vurgun.skyfit.core.data.v1.domain.workout.model.WorkoutCategory

interface WorkoutRepository {

    suspend fun getCategories(): Result<List<WorkoutCategory>>
}