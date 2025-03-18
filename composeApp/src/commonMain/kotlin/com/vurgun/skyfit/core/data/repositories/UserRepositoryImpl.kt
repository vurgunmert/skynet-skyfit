package com.vurgun.skyfit.core.data.repositories

import com.vurgun.skyfit.core.data.mapper.UserDetailMapper.toDomain
import com.vurgun.skyfit.core.data.service.UserApiService
import com.vurgun.skyfit.core.domain.models.UserDetail
import com.vurgun.skyfit.core.domain.repository.UserRepository
import com.vurgun.skyfit.core.network.model.ApiResult
import com.vurgun.skyfit.core.storage.LocalSettingsStore
import com.vurgun.skyfit.core.utils.DispatcherProvider
import com.vurgun.skyfit.feature_dashboard.ui.unmanagedUser
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
                    unmanagedUser = user
                    return@withContext Result.success(user)
                }
            }
        }

}