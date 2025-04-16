package com.vurgun.skyfit.data.courses.repository

import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.courses.CourseApiService
import com.vurgun.skyfit.data.courses.domain.model.Lesson
import com.vurgun.skyfit.data.courses.domain.repository.CourseRepository
import com.vurgun.skyfit.data.courses.mapper.toDomain
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class CourseRepositoryImpl(
    private val storage: Storage,
    private val dispatchers: DispatcherProvider,
    private val apiService: CourseApiService
) : CourseRepository {

    private val userToken = storage.getAsFlow(UserRepository.UserAuthToken)

    private suspend fun requireToken(): String = userToken.firstOrNull() ?: throw MissingTokenException

    override suspend fun getLessons(gymId: Int, startDate: String, endDate: String?): Result<List<Lesson>> =
        withContext(dispatchers.io) {
            runCatching {
                val token = requireToken()
                when (val result = apiService.getLessons(gymId, startDate, endDate, token)) {
                    is ApiResult.Success -> result.data.toDomain()
                    is ApiResult.Error -> throw IllegalStateException(result.message)
                    is ApiResult.Exception -> throw result.exception
                }
            }
        }
}