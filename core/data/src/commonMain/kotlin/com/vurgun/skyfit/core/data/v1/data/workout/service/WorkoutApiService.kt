package com.vurgun.skyfit.core.data.v1.data.workout.service

import com.vurgun.skyfit.core.data.v1.data.workout.model.WorkoutCategoryDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.http.*

class WorkoutApiService(private val apiClient: ApiClient) {

    private companion object Endpoints {
        const val GET_WORKOUT_CATEGORY_ALL = "workout/category/all"
    }

    suspend fun getWorkoutCategories(token: String? = null): ApiResult<List<WorkoutCategoryDTO>> {
        return apiClient.safeApiCall<List<WorkoutCategoryDTO>> {
            method = HttpMethod.Post
            token?.let { bearerAuth(it) }
            url(Endpoints.GET_WORKOUT_CATEGORY_ALL)
        }
    }
}