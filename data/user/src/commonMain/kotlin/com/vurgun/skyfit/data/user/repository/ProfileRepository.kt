package com.vurgun.skyfit.data.user.repository

import com.vurgun.skyfit.data.user.domain.FacilityProfile
import com.vurgun.skyfit.data.user.domain.FacilityTrainerProfile
import com.vurgun.skyfit.data.user.domain.TrainerProfile
import com.vurgun.skyfit.data.user.domain.UserProfile

interface ProfileRepository {
    suspend fun getUserProfile(normalUserId: Int): Result<UserProfile>
    suspend fun getTrainerProfile(trainerId: Int): Result<TrainerProfile>
    suspend fun getFacilityProfile(facilityId: Int): Result<FacilityProfile>
    suspend fun getFacilityTrainerProfiles(facilityId: Int): Result<List<FacilityTrainerProfile>>
}