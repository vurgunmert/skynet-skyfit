package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.domain.model.UserDetail
import com.vurgun.skyfit.data.core.domain.repository.UserRepository
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
): UserRepository {


    override suspend fun getUserDetails(): Result<UserDetail> =
        withContext(dispatchers.io) {
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

}