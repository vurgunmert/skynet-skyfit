package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.model.BaseUserDetail
import com.vurgun.skyfit.data.core.domain.model.UserAccountType
import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.core.model.MissingUserTypeException
import com.vurgun.skyfit.data.core.model.UnknownServerException
import com.vurgun.skyfit.data.core.storage.Storage
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import com.vurgun.skyfit.data.network.utils.ioResult
import com.vurgun.skyfit.data.network.utils.mapOrThrow
import com.vurgun.skyfit.data.user.mappers.UserDetailMapper.toDomain
import com.vurgun.skyfit.data.user.model.SelectUserTypeResponse
import com.vurgun.skyfit.data.user.service.UserApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
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

    private suspend fun getUserDetails(newToken: String?): Result<BaseUserDetail> = withContext(dispatchers.io) {
        try {
            val token = newToken ?: requireToken()
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

    override suspend fun getSecureUserDetails(): Result<BaseUserDetail> = withContext(dispatchers.io) {
        try {
            val token = requireToken()
            when (val response = apiService.getDetails(token)) {
                is ApiResult.Success -> {
                    return@withContext Result.success(response.data.toDomain())
                }

                is ApiResult.Error -> {
                    val message = response.message.orEmpty()

                    if (message.contains("#A00007")) {
                        println("⚠️ Missing user type. Attempting fallback resolution.")

                        // Step 1: Get available user types
                        val userTypesResult = getUserTypes()
                        if (userTypesResult.isFailure) {
                            return@withContext Result.failure(
                                userTypesResult.exceptionOrNull()
                                    ?: IllegalStateException("Failed to get user types.")
                            )
                        }

                        val fallbackType = userTypesResult.getOrNull()?.lastOrNull()
                            ?: return@withContext Result.failure(
                                IllegalStateException("No user types available to fallback.")
                            )

                        println("🧭 Fallback user type: ${fallbackType.typeName} (${fallbackType.typeId})")

                        // Step 2: Select that user type and ensure token is updated
                        val selectionResult = selectUserType(fallbackType.typeId)
                        if (selectionResult.isFailure) {
                            return@withContext Result.failure(
                                selectionResult.exceptionOrNull()
                                    ?: IllegalStateException("Failed to select fallback user type.")
                            )
                        }

                        val selectResponse = selectionResult.getOrNull()
                            ?: return@withContext Result.failure(
                                IllegalStateException("No response after selecting user type.")
                            )

                        println("🔑 Token from selection: ${selectResponse} (Already saved)")
                        println("🔁 Retrying getUserDetails with updated token...")

                        // Step 3: Retry after token has been updated inside selectUserType
                        return@withContext getUserDetails(selectResponse.token)
                    }

                    if (message.contains("403", ignoreCase = true)) {
                        println("❌ Token expired or unauthorized. Clearing local storage.")
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


    override suspend fun getUserTypes(): Result<List<UserAccountType>> = ioResult(dispatchers) {
        val token = requireToken()
        apiService.getAccountTypes(token).mapOrThrow { it.toDomain() }
    }

    override suspend fun selectUserType(typeId: Int): Result<SelectUserTypeResponse> = withContext(dispatchers.io) {
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
                    return@withContext Result.success(response.data)
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