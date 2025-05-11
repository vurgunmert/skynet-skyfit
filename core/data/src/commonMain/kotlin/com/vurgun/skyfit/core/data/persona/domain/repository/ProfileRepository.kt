package com.vurgun.skyfit.core.data.persona.domain.repository

import com.vurgun.skyfit.core.data.persona.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.persona.domain.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.data.persona.domain.model.TrainerProfile
import com.vurgun.skyfit.core.data.persona.domain.model.UserProfile

interface ProfileRepository {
    suspend fun fetchImageBytes(url: String): ByteArray

    suspend fun getUserProfile(normalUserId: Int): Result<UserProfile>
    suspend fun getTrainerProfile(trainerId: Int): Result<TrainerProfile>
    suspend fun getFacilityProfile(facilityId: Int): Result<FacilityProfile>
    suspend fun getFacilityTrainerProfiles(facilityId: Int): Result<List<FacilityTrainerProfile>>

    suspend fun updateUserProfile(
        normalUserId: Int,
        username: String,
        profileImageBytes: ByteArray?,
        backgroundImageBytes: ByteArray?,
        name: String,
        surname: String,
        height: Int,
        weight: Int,
        bodyTypeId: Int
    ): Result<Unit>

    suspend fun updateTrainerProfile(
        trainerId: Int,
        username: String,
        profileImageBytes: ByteArray?,
        backgroundImageBytes: ByteArray?,
        firstName: String,
        lastName: String,
        bio: String,
        profileTags: List<Int>
    ): Result<Unit>

    suspend fun updateFacilityProfile(
        gymId: Int,
        backgroundImageBytes: ByteArray?,
        name: String,
        address: String,
        bio: String,
        profileTags: List<Int>
    ): Result<Unit>
}