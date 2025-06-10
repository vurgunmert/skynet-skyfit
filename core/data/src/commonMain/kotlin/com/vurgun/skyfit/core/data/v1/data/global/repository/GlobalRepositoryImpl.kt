package com.vurgun.skyfit.core.data.v1.data.global.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.global.service.GlobalApiService
import com.vurgun.skyfit.core.data.v1.domain.global.model.ProfileTag
import com.vurgun.skyfit.core.data.v1.domain.global.model.UserGoal
import com.vurgun.skyfit.core.data.v1.domain.global.repository.GlobalRepository
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class GlobalRepositoryImpl(
    private val apiService: GlobalApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : GlobalRepository {

    override suspend fun getProfileTags() = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getAllTags(token).mapOrThrow { list -> list.map { ProfileTag(it.tagId, it.tagName) } }
    }

    override suspend fun getGoals() = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getAllGoals(token).mapOrThrow { list -> list.map { UserGoal(it.goalId, it.goalName) } }
    }
}