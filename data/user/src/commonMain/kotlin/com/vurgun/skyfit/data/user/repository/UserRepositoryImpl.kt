package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.model.BaseUserDetail
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.core.model.MissingUserTypeException
import com.vurgun.skyfit.data.core.model.UnknownServerException
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.core.storage.TokenManager
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import com.vurgun.skyfit.data.network.utils.ioResult
import com.vurgun.skyfit.data.network.utils.mapOrThrow
import com.vurgun.skyfit.data.user.mappers.UserDetailMapper.toDomain
import com.vurgun.skyfit.data.user.model.SelectUserTypeResponse
import com.vurgun.skyfit.data.user.service.UserApiService
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val apiService: UserApiService,
    private val dispatchers: DispatcherProvider,
    private val storage: Storage,
    private val tokenManager: TokenManager
) : UserRepository {

    private suspend fun getUserDetails(): Result<BaseUserDetail> = ioResult(dispatchers) {
        tokenManager.waitUntilTokenReady()
        val token = tokenManager.getTokenOrThrow()
        apiService.getDetails(token).mapOrThrow { it.toDomain() }
    }

    override suspend fun getSecureUserDetails(): Result<BaseUserDetail> = withContext(dispatchers.io) {
        try {
            val token = tokenManager.getTokenOrThrow()
            when (val response = apiService.getDetails(token)) {
                is ApiResult.Success -> return@withContext Result.success(response.data.toDomain())

                is ApiResult.Error -> {
                    val message = response.message.orEmpty()

                    if (message.contains("#A00007")) {
                        println("⚠️ Missing user type. Attempting fallback resolution.")

                        val userTypes = getRegisteredAccountTypes().getOrElse {
                            return@withContext Result.failure(it)
                        }

                        val fallbackType = userTypes.lastOrNull()
                            ?: return@withContext Result.failure(MissingUserTypeException)

                        println("🧭 Selecting fallback user type: ${fallbackType.typeName}")

                        val selectionResult = selectUserType(fallbackType.typeId)
                        if (selectionResult.isFailure) {
                            return@withContext Result.failure(selectionResult.exceptionOrNull() ?: UnknownServerException)
                        }

                        return@withContext getUserDetails()
                    }

                    if (message.contains("#T00001", ignoreCase = true)) {
                        storage.clearAll()
                        return@withContext Result.failure(MissingTokenException)
                    }

                    return@withContext Result.failure(IllegalStateException(message))
                }

                is ApiResult.Exception -> return@withContext Result.failure(response.exception)
            }

        } catch (e: Exception) {
            return@withContext Result.failure(e)
        }
    }

    override suspend fun selectUserType(typeId: Int): Result<SelectUserTypeResponse> = withContext(dispatchers.io) {
        runCatching {
            val token = tokenManager.getTokenOrThrow()

            when (val response = apiService.selectUserType(typeId, token)) {
                is ApiResult.Success -> {
                    tokenManager.setToken(response.data.token)
                    tokenManager.waitUntilTokenReady()
                    response.data
                }

                is ApiResult.Error -> throw IllegalStateException(response.message)
                is ApiResult.Exception -> throw response.exception
            }
        }
    }

    override suspend fun getRegisteredAccountTypes(): Result<List<UserAccountType>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getAccountTypes(token).mapOrThrow { it.toDomain() }
    }

}