package com.vurgun.skyfit.core.data.v1.data.global.service

import com.vurgun.skyfit.core.data.v1.data.global.model.GoalDTO
import com.vurgun.skyfit.core.data.v1.data.global.model.TagDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.http.*

class GlobalApiService(private val apiClient: ApiClient) {

    private companion object Endpoint {
        const val GET_GOALS = "global/goals"
        const val GET_WORKOUT_TAGS = "global/workout-tags"
        const val GET_PROFILE_TAGS = "global/profile-tags"
        const val GET_MEASUREMENT_TYPES = "global/measurement-types"
        const val GET_USER_TROPHIES = "global/user-trophies"
        const val GET_TRAINER_TROPHIES = "global/trainer-trophies"
        const val GET_FACILITY_TROPHIES = "global/facility-trophies"
    }

    suspend fun getAllGoals(token: String): ApiResult<List<GoalDTO>> {
        return apiClient.safeApiCall<List<GoalDTO>> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(GET_GOALS)
        }
    }

    suspend fun getProfileTags(token: String): ApiResult<List<TagDTO>> {
        return apiClient.safeApiCall<List<TagDTO>> {
            method = HttpMethod.Companion.Post
            bearerAuth(token)
            url(GET_PROFILE_TAGS)
        }
    }
}