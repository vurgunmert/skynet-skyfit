package com.vurgun.skyfit.data.courses.repository

import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.courses.CourseApiService
import com.vurgun.skyfit.data.courses.model.LessonDTO
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

interface CourseRepository {
    suspend fun getLessons(gymId: Int, startDate: String, endDate: String?): Result<List<LessonDTO>>
}

class CourseRepositoryImpl(
    private val storage: Storage,
    private val dispatchers: DispatcherProvider,
    private val apiService: CourseApiService
) : CourseRepository {

    private val userToken = storage.getAsFlow(UserRepository.UserAuthToken)

    private suspend fun requireToken(): String = userToken.firstOrNull() ?: throw MissingTokenException

    override suspend fun getLessons(gymId: Int, startDate: String, endDate: String?) = withContext(dispatchers.io) {
        try {
            val token = requireToken()
            when (val response = apiService.getLessons(gymId, startDate, endDate, token)) {
                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))

                is ApiResult.Exception -> Result.failure(response.exception)
                is ApiResult.Success -> {
                    val user = response.data
                    return@withContext Result.success(user)
                }
            }
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }
}