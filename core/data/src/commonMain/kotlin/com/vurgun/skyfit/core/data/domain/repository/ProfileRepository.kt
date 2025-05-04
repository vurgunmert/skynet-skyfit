package com.vurgun.skyfit.core.data.domain.repository

import com.vurgun.skyfit.core.data.domain.model.FacilityProfile
import com.vurgun.skyfit.core.data.domain.model.FacilityTrainerProfile
import com.vurgun.skyfit.core.data.domain.model.TrainerProfile
import com.vurgun.skyfit.core.data.domain.model.UserProfile

interface ProfileRepository {
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

    suspend fun fetchImageBytes(url: String): ByteArray
}