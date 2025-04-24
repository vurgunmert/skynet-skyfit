package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.core.storage.TokenManager
import com.vurgun.skyfit.data.network.DispatcherProvider
import com.vurgun.skyfit.data.network.utils.ioResult
import com.vurgun.skyfit.data.network.utils.mapOrThrow
import com.vurgun.skyfit.data.user.domain.FacilityProfile
import com.vurgun.skyfit.data.user.domain.TrainerProfile
import com.vurgun.skyfit.data.user.domain.UserProfile
import com.vurgun.skyfit.data.user.mappers.ProfileMapper.toDomain
import com.vurgun.skyfit.data.user.model.GetFacilityProfileRequest
import com.vurgun.skyfit.data.user.model.GetTrainerProfileRequest
import com.vurgun.skyfit.data.user.model.GetUserProfileRequest
import com.vurgun.skyfit.data.user.service.ProfileApiService

class ProfileRepositoryImpl(
    private val apiService: ProfileApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : ProfileRepository {

    override suspend fun getUserProfile(normalUserId: Int): Result<UserProfile> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetUserProfileRequest(normalUserId)
        apiService.getUserProfile(request, token).mapOrThrow { it.toDomain() }
    }

    override suspend fun getTrainerProfile(trainerId: Int): Result<TrainerProfile> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetTrainerProfileRequest(trainerId)
        apiService.getTrainerProfile(request, token).mapOrThrow { it.toDomain() }
    }

    override suspend fun getFacilityProfile(facilityId: Int): Result<FacilityProfile> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetFacilityProfileRequest(facilityId)
        apiService.getFacilityProfile(request, token).mapOrThrow { it.toDomain() }
    }
}