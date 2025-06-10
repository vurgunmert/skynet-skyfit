package com.vurgun.skyfit.core.data.v1.data.workout.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.workout.mapper.WorkoutDataMapper.toDomain
import com.vurgun.skyfit.core.data.v1.data.workout.service.WorkoutApiService
import com.vurgun.skyfit.core.data.v1.domain.workout.model.WorkoutCategory
import com.vurgun.skyfit.core.data.v1.domain.workout.repository.WorkoutRepository
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class WorkoutRepositoryImpl(
    private val apiService: WorkoutApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : WorkoutRepository {

    override suspend fun getCategories(): Result<List<WorkoutCategory>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getWorkoutCategories(token).mapOrThrow { it.toDomain() }
    }
}