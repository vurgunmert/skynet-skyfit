package com.vurgun.skyfit.core.data.repository

import com.vurgun.skyfit.core.data.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.domain.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.data.domain.model.TrainerProfile
import com.vurgun.skyfit.core.data.domain.model.UserProfile
import com.vurgun.skyfit.core.data.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.mappers.ProfileMapper.toDomainFacilityProfile
import com.vurgun.skyfit.core.data.mappers.ProfileMapper.toDomainTrainerProfile
import com.vurgun.skyfit.core.data.mappers.ProfileMapper.toDomainUserProfile
import com.vurgun.skyfit.core.data.mappers.ProfileMapper.toFacilityTrainerProfiles
import com.vurgun.skyfit.core.data.model.GetFacilityProfileRequest
import com.vurgun.skyfit.core.data.model.GetFacilityTrainerProfilesRequest
import com.vurgun.skyfit.core.data.model.GetTrainerProfileRequest
import com.vurgun.skyfit.core.data.model.GetUserProfileRequest
import com.vurgun.skyfit.core.data.service.ProfileApiService
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class ProfileRepositoryImpl(
    private val apiService: ProfileApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : ProfileRepository {

    override suspend fun getUserProfile(normalUserId: Int): Result<UserProfile> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetUserProfileRequest(normalUserId)
        apiService.getUserProfile(request, token).mapOrThrow { it.toDomainUserProfile() }
    }

    override suspend fun getTrainerProfile(trainerId: Int): Result<TrainerProfile> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetTrainerProfileRequest(trainerId)
        apiService.getTrainerProfile(request, token).mapOrThrow { it.toDomainTrainerProfile() }
    }

    override suspend fun getFacilityTrainerProfiles(facilityId: Int): Result<List<FacilityTrainerProfile>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetFacilityTrainerProfilesRequest(facilityId)
        apiService.getFacilityTrainerProfiles(request, token).mapOrThrow { it.toFacilityTrainerProfiles() }
    }

    override suspend fun getFacilityProfile(facilityId: Int): Result<FacilityProfile> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetFacilityProfileRequest(facilityId)
        apiService.getFacilityProfile(request, token).mapOrThrow { it.toDomainFacilityProfile() }
    }
}