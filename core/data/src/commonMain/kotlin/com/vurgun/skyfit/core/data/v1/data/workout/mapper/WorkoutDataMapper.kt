package com.vurgun.skyfit.core.data.v1.data.workout.mapper

import com.vurgun.skyfit.core.data.v1.data.workout.model.WorkoutCategoryDTO
import com.vurgun.skyfit.core.data.v1.domain.workout.model.WorkoutCategory

object WorkoutDataMapper {

    fun WorkoutCategoryDTO.toDomain(): WorkoutCategory {
        return WorkoutCategory(id, code, name)
    }

    fun List<WorkoutCategoryDTO>.toDomain(): List<WorkoutCategory> = this.map { it.toDomain() }
}