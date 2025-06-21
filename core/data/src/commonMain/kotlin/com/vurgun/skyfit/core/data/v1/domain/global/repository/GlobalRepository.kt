package com.vurgun.skyfit.core.data.v1.domain.global.repository

import com.vurgun.skyfit.core.data.v1.data.workout.WorkoutEvent
import com.vurgun.skyfit.core.data.v1.domain.global.model.ProfileTag
import com.vurgun.skyfit.core.data.v1.domain.global.model.UserGoal

interface GlobalRepository {

    suspend fun getProfileTags(): Result<List<ProfileTag>>
    suspend fun getGoals(): Result<List<UserGoal>>
    suspend fun getWorkoutEvents(): Result<List<WorkoutEvent>>
}