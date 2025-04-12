package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.core.storage.LocalSettingsStore
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import com.vurgun.skyfit.data.user.mappers.UserDetailMapper.toDomain
import com.vurgun.skyfit.data.user.service.UserApiService
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val settingsStore: LocalSettingsStore,
    private val apiService: UserApiService,
    private val dispatchers: DispatcherProvider
) : UserRepository {

    private suspend fun <T, R> apiCallWithToken(
        map: (T) -> R,
        block: suspend (String) -> ApiResult<T>
    ): Result<R> = withContext(dispatchers.io) {
        runCatching {
            val token = requireToken()
            when (val response = block(token)) {
                is ApiResult.Success -> map(response.data).let { Result.success(it) }
                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
                is ApiResult.Exception -> Result.failure(response.exception)
            }
        }.getOrElse { Result.failure(it) }
    }

    private fun requireToken(): String {
        val token = settingsStore.getToken()
        if (token == null) {
            settingsStore.clearAll()
            throw MissingTokenException
        }
        return token
    }

    override suspend fun getUserDetails(): Result<UserDetail> = withContext(dispatchers.io) {
        val token = settingsStore.getToken() ?: kotlin.run {
            settingsStore.clearAll()
            return@withContext Result.failure(IllegalStateException("Missing token!"))
        }

        when (val response = apiService.getDetails(token)) {
            is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
            is ApiResult.Exception -> Result.failure(response.exception)
            is ApiResult.Success -> {
                val user = response.data.toDomain()
                return@withContext Result.success(user)
            }
        }
    }

    override suspend fun getUserTypes(): Result<List<UserAccountType>> =
        apiCallWithToken({ it.toDomain() }) { token ->
            apiService.getAccountTypes(token)
        }

    override suspend fun selectUserType(typeId: Int): Result<Boolean> = withContext(dispatchers.io) {
        val token = settingsStore.getToken() ?: kotlin.run {
            settingsStore.clearAll()
            return@withContext Result.failure(IllegalStateException("Missing token!"))
        }

        when (val response = apiService.selectUserType(typeId, token)) {
            is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
            is ApiResult.Exception -> Result.failure(response.exception)
            is ApiResult.Success -> {
                val newToken = response.data.token
                settingsStore.saveToken(newToken)
                return@withContext Result.success(true)
            }
        }
    }
}