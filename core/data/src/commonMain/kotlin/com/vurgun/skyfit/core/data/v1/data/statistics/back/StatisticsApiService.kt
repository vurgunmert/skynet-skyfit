package com.vurgun.skyfit.core.data.v1.statistics.back

import com.vurgun.skyfit.core.data.v1.statistics.back.model.StatisticsDTO
import com.vurgun.skyfit.core.network.ApiClient
import com.vurgun.skyfit.core.network.ApiResult
import io.ktor.client.request.*
import io.ktor.http.*

class StatisticsApiService(private val apiClient: ApiClient) {

    private object Endpoint {
        const val GET_STATISTICS = "statistics/get"
    }

    suspend fun getStatistics(userId: String, token: String): ApiResult<StatisticsDTO> {
        return apiClient.safeApiCall {
            method = HttpMethod.Post
            bearerAuth(token)
            url(Endpoint.GET_STATISTICS)
            setBody(mapOf("userId" to userId))
        }
    }
}
