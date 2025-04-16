package com.vurgun.skyfit.data.courses

import com.vurgun.skyfit.data.courses.model.GetLessonsRequest
import com.vurgun.skyfit.data.courses.model.LessonDTO
import com.vurgun.skyfit.data.network.ApiClient
import com.vurgun.skyfit.data.network.ApiResult
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.HttpMethod

class CourseApiService(private val apiClient: ApiClient) {

    internal suspend fun getLessons(gymId: Int, startDate: String, endDate: String?, token: String): ApiResult<List<LessonDTO>> {
        val request = GetLessonsRequest(gymId, startDate, endDate)
        return apiClient.safeApiCall<List<LessonDTO>> {
            method = HttpMethod.Post
            bearerAuth(token)
            url("get/lessons/gym")
            setBody(request)
        }
    }
}