package com.vurgun.skyfit.core.data.persona.data.repository

import com.vurgun.skyfit.core.data.persona.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.persona.domain.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.data.persona.domain.model.TrainerProfile
import com.vurgun.skyfit.core.data.persona.domain.model.UserProfile
import com.vurgun.skyfit.core.data.persona.domain.repository.ProfileRepository
import com.vurgun.skyfit.core.data.persona.data.mapper.ProfileMapper.toDomainFacilityProfile
import com.vurgun.skyfit.core.data.persona.data.mapper.ProfileMapper.toDomainTrainerProfile
import com.vurgun.skyfit.core.data.persona.data.mapper.ProfileMapper.toDomainUserProfile
import com.vurgun.skyfit.core.data.persona.data.mapper.ProfileMapper.toFacilityTrainerProfiles
import com.vurgun.skyfit.core.data.persona.data.model.GetFacilityProfileRequest
import com.vurgun.skyfit.core.data.persona.data.model.GetFacilityTrainerProfilesRequest
import com.vurgun.skyfit.core.data.persona.data.model.GetTrainerProfileRequest
import com.vurgun.skyfit.core.data.persona.data.model.GetUserProfileRequest
import com.vurgun.skyfit.core.data.persona.data.model.UpdateFacilityProfileRequest
import com.vurgun.skyfit.core.data.persona.data.model.UpdateTrainerProfileRequest
import com.vurgun.skyfit.core.data.persona.data.model.UpdateUserProfileRequest
import com.vurgun.skyfit.core.data.persona.data.service.ProfileApiService
import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.RemoteImageDataSource
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class ProfileRepositoryImpl(
    private val apiService: ProfileApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager,
    private val remoteImageDataSource: RemoteImageDataSource
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

    override suspend fun updateUserProfile(
        normalUserId: Int,
        username: String,
        profileImageBytes: ByteArray?,
        backgroundImageBytes: ByteArray?,
        name: String,
        surname: String,
        height: Int,
        weight: Int,
        bodyTypeId: Int
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = UpdateUserProfileRequest(
            userId = normalUserId,
            profilePhoto = profileImageBytes,
            backgroundImage = backgroundImageBytes,
            username = username,
            name = name,
            surname = surname,
            height = height,
            weight = weight,
            bodyTypeId = bodyTypeId
        )
        apiService.updateUserProfile(request, token).mapOrThrow { }
    }

    override suspend fun updateTrainerProfile(
        trainerId: Int,
        username: String,
        profileImageBytes: ByteArray?,
        backgroundImageBytes: ByteArray?,
        firstName: String,
        lastName: String,
        bio: String,
        profileTags: List<Int>
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = UpdateTrainerProfileRequest(
            trainerId = trainerId,
            profilePhoto = profileImageBytes,
            backgroundImage = backgroundImageBytes,
            username = username,
            name = firstName,
            surname = lastName,
            bio = bio,
            profileTags = profileTags
        )
        apiService.updateTrainerProfile(request, token).mapOrThrow { }
    }

    override suspend fun updateFacilityProfile(
        gymId: Int,
        backgroundImageBytes: ByteArray?,
        name: String,
        address: String,
        bio: String,
        profileTags: List<Int>
    ): Result<Unit> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = UpdateFacilityProfileRequest(
            gymId = gymId,
            backgroundImage = backgroundImageBytes,
            name = name,
            bio = bio,
            address = address,
            profileTags = profileTags
        )
        apiService.updateFacilityProfile(request, token).mapOrThrow { }
    }

    override suspend fun fetchImageBytes(url: String): ByteArray {
        return remoteImageDataSource.getImageBytes(url)
    }
}