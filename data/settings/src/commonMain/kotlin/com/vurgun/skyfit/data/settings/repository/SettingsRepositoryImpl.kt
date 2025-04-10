package com.vurgun.skyfit.data.settings.repository

import com.vurgun.skyfit.data.core.model.MissingTokenException
import com.vurgun.skyfit.data.core.storage.LocalSettingsStore
import com.vurgun.skyfit.data.network.ApiResult
import com.vurgun.skyfit.data.network.DispatcherProvider
import com.vurgun.skyfit.data.settings.model.Member
import com.vurgun.skyfit.data.settings.model.toDomain
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    private val settingsStore: LocalSettingsStore,
    private val apiService: SettingsApiService,
    private val dispatchers: DispatcherProvider
) : SettingsRepository {

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

    //TODO: load onto generic function above
    private fun requireToken(): String {
        val token = settingsStore.getToken()
        if (token == null) {
            settingsStore.clearAll()
            throw MissingTokenException
        }
        return token
    }

    override suspend fun addGymUser(gymId: Int, userId: Int): Result<Boolean> =
        apiCallWithToken({ true }) { token ->
            apiService.addGymMember(gymId, userId, token)
        }


//    override suspend fun addGymUser(gymId: Int, userId: Int): Result<Boolean> = withContext(dispatchers.io) {
//        try {
//            val token = requireToken()
//
//            when (val response = apiService.addGymMember(gymId, userId, token)) {
//                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
//                is ApiResult.Exception -> Result.failure(response.exception)
//                is ApiResult.Success -> {
//                    return@withContext Result.success(true)
//                }
//            }
//
//        } catch (e: MissingTokenException) {
//            Result.failure(e)
//        }
//    }

    override suspend fun getGymMembers(gymId: Int): Result<List<Member>> =
        apiCallWithToken({ it.toDomain() }) { token ->
            apiService.getGymMembers(gymId, token)
        }

//    override suspend fun getGymMembers(gymId: Int): Result<List<Member>> = withContext(dispatchers.io) {
//        try {
//            val token = requireToken()
//
//            when (val response = apiService.getGymMembers(gymId, token)) {
//                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
//                is ApiResult.Exception -> Result.failure(response.exception)
//                is ApiResult.Success -> {
//                    return@withContext Result.success(response.data.map { it.toDomain() })
//                }
//            }
//
//        } catch (e: MissingTokenException) {
//            Result.failure(e)
//        }
//    }

    override suspend fun deleteGymMember(gymId: Int, userId: Int): Result<Boolean> = withContext(dispatchers.io) {
        try {
            val token = requireToken()

            when (val response = apiService.deleteGymMember(gymId, userId, token)) {
                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
                is ApiResult.Exception -> Result.failure(response.exception)
                is ApiResult.Success -> {
                    return@withContext Result.success(true)
                }
            }

        } catch (e: MissingTokenException) {
            Result.failure(e)
        }
    }

    override suspend fun getPlatformMembers(gymId: Int): Result<List<Member>> = withContext(dispatchers.io) {
        try {
            val token = requireToken()

            when (val response = apiService.getPlatformMembers(gymId, token)) {
                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
                is ApiResult.Exception -> Result.failure(response.exception)
                is ApiResult.Success -> {
                    return@withContext Result.success(response.data.toDomain())
                }
            }

        } catch (e: MissingTokenException) {
            Result.failure(e)
        }
    }

    override suspend fun addGymTrainer(gymId: Int, userId: Int): Result<Boolean> = withContext(dispatchers.io) {
        try {
            val token = requireToken()

            when (val response = apiService.addGymTrainer(gymId, userId, token)) {
                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
                is ApiResult.Exception -> Result.failure(response.exception)
                is ApiResult.Success -> {
                    return@withContext Result.success(true)
                }
            }

        } catch (e: MissingTokenException) {
            Result.failure(e)
        }
    }

    override suspend fun getGymTrainers(gymId: Int): Result<List<Member>> = withContext(dispatchers.io) {
        try {
            val token = requireToken()

            when (val response = apiService.getGymTrainers(gymId, token)) {
                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
                is ApiResult.Exception -> Result.failure(response.exception)
                is ApiResult.Success -> {
                    return@withContext Result.success(response.data.toDomain())
                }
            }

        } catch (e: MissingTokenException) {
            Result.failure(e)
        }
    }

    override suspend fun deleteGymTrainer(gymId: Int, userId: Int): Result<Boolean> = withContext(dispatchers.io) {
        try {
            val token = requireToken()

            when (val response = apiService.deleteGymTrainer(gymId, userId, token)) {
                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
                is ApiResult.Exception -> Result.failure(response.exception)
                is ApiResult.Success -> {
                    return@withContext Result.success(true)
                }
            }

        } catch (e: MissingTokenException) {
            Result.failure(e)
        }
    }

    override suspend fun getPlatformTrainers(): Result<List<Member>> = withContext(dispatchers.io) {
        try {
            val token = requireToken()

            when (val response = apiService.getPlatformTrainers(token)) {
                is ApiResult.Error -> Result.failure(IllegalStateException(response.message))
                is ApiResult.Exception -> Result.failure(response.exception)
                is ApiResult.Success -> {
                    return@withContext Result.success(response.data.toDomain())
                }
            }

        } catch (e: MissingTokenException) {
            Result.failure(e)
        }
    }

}