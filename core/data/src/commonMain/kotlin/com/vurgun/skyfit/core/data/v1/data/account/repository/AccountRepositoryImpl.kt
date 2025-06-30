package com.vurgun.skyfit.core.data.v1.data.account.repository

import com.mmk.kmpnotifier.notification.NotifierManager
import com.vurgun.skyfit.core.data.storage.Storage
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.account.mapper.AccountDataMapper.toDomain
import com.vurgun.skyfit.core.data.v1.data.account.model.RequestAccountDetailDTO
import com.vurgun.skyfit.core.data.v1.data.account.model.SelectActiveAccountTypeResponseDTO
import com.vurgun.skyfit.core.data.v1.data.account.service.AccountApiService
import com.vurgun.skyfit.core.data.v1.domain.account.model.Account
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountOnboardingFormData
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountOnboardingResult
import com.vurgun.skyfit.core.data.v1.domain.account.model.AccountType
import com.vurgun.skyfit.core.data.v1.domain.account.repository.AccountRepository
import com.vurgun.skyfit.core.data.v1.domain.global.model.MissingTokenException
import com.vurgun.skyfit.core.data.v1.domain.global.model.MissingUserTypeException
import com.vurgun.skyfit.core.data.v1.domain.global.model.UnknownServerException
import com.vurgun.skyfit.core.network.ApiResult
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow
import kotlinx.coroutines.withContext

class AccountRepositoryImpl(
    private val apiService: AccountApiService,
    private val dispatchers: DispatcherProvider,
    private val storage: Storage,
    private val tokenManager: TokenManager,
) : AccountRepository {

    private suspend fun fetchDetails(): Result<Account> = ioResult(dispatchers) {
        tokenManager.waitUntilTokenReady()
        val token = tokenManager.getTokenOrThrow()
        val request = RequestAccountDetailDTO(
            fcmToken = NotifierManager.getPushNotifier().getToken()
        )
        apiService.getDetails(request, token).mapOrThrow { it.toDomain() }
    }

    override suspend fun getAccountDetails(): Result<Account> = withContext(dispatchers.io) {
        try {
            val request = RequestAccountDetailDTO(
                fcmToken = NotifierManager.getPushNotifier().getToken()
            )
            val token = tokenManager.getTokenOrThrow()
            when (val response = apiService.getDetails(request, token)) {
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

                        val selectionResult = selectActiveAccountType(fallbackType.typeId)
                        if (selectionResult.isFailure) {
                            return@withContext Result.failure(
                                selectionResult.exceptionOrNull() ?: UnknownServerException
                            )
                        }

                        return@withContext fetchDetails()
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

    override suspend fun selectActiveAccountType(typeId: Int): Result<SelectActiveAccountTypeResponseDTO> =
        withContext(dispatchers.io) {
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

    override suspend fun getRegisteredAccountTypes(): Result<List<AccountType>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getAccountTypes(token).mapOrThrow { it.toDomain() }
    }

    override suspend fun submitOnboarding(request: AccountOnboardingFormData, isAccountAddition: Boolean) =
        withContext(dispatchers.io) {
            val token = tokenManager.getTokenOrNull() ?: return@withContext AccountOnboardingResult.Unauthorized

            val response = when {
                isAccountAddition -> apiService.onboardingAdditionalAccount(request, token)
                else -> apiService.onboardNewAccount(request, token)
            }
            when (response) {
                is ApiResult.Error -> AccountOnboardingResult.Error(response.message)
                is ApiResult.Exception -> AccountOnboardingResult.Error(response.exception.message)
                is ApiResult.Success -> AccountOnboardingResult.Success
            }
        }
}