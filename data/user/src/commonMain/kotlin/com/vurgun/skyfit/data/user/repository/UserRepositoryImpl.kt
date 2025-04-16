package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.repository.UserRepository
import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.core.model.MissingUserTypeException
import com.vurgun.skyfit.data.core.model.UnknownServerException
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import com.vurgun.skyfit.data.user.mappers.UserDetailMapper.toDomain
import com.vurgun.skyfit.data.user.service.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val apiService: UserApiService,
    private val dispatchers: DispatcherProvider,
    private val storage: Storage
) : UserRepository {

    private val userToken = storage.getAsFlow(UserRepository.UserAuthToken)

    override val userTypeId: Flow<Int?> = storage.getAsFlow(UserRepository.UserTypeIdKey)

    private suspend fun requireToken(): String = userToken.firstOrNull() ?: throw MissingTokenException

    private suspend fun requireUpdatedToken(after: suspend () -> Unit): String {
        after() // run update
        return userToken
            .filter { !it.isNullOrBlank() }
            .first() ?: throw MissingTokenException
    }


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


    override suspend fun getUserDetails(): Result<UserDetail> = withContext(dispatchers.io) {
        try {
            val token = requireToken()
            when (val response = apiService.getDetails(token)) {
                is ApiResult.Error -> {
                    return@withContext response.message
                        .takeUnless { it.isNullOrEmpty() }
                        ?.let {
                            when {
                                it.contains("#A00007") -> {
                                    Result.failure(MissingUserTypeException)
                                }

                                else -> Result.failure(IllegalStateException(response.message))
                            }
                        } ?: Result.failure(UnknownServerException)
                }

                is ApiResult.Exception -> Result.failure(response.exception)
                is ApiResult.Success -> {
                    val user = response.data.toDomain()
                    return@withContext Result.success(user)
                }
            }
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    override suspend fun getUserTypes(): Result<List<UserAccountType>> =
        apiCallWithToken({ it.toDomain() }) { token ->
            apiService.getAccountTypes(token)
        }

    override suspend fun selectUserType(typeId: Int): Result<Boolean> = withContext(dispatchers.io) {
        try {
            val token = requireToken()
            when (val response = apiService.selectUserType(typeId, token)) {
                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
                is ApiResult.Exception -> Result.failure(response.exception)
                is ApiResult.Success -> {
                    updateUserTypeId(typeId)
                    val newToken = requireUpdatedToken {
                        updateUserToken(response.data.token)
                    }
                    println("✅ Token confirmed updated before any next API call: $newToken")
                    return@withContext Result.success(true)
                }
            }
        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    private suspend fun updateUserTypeId(typeId: Int) {
        storage.writeValue(UserRepository.UserTypeIdKey, typeId)
    }

    private suspend fun updateUserToken(token: String) {
        storage.writeValue(UserRepository.UserAuthToken, token)
    }
}