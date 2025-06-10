package com.vurgun.skyfit.core.data.v1.domain.explore

import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile

interface ExploreRepository {
    suspend fun getAllFacilities(): Result<List<FacilityProfile>>
    suspend fun getAllTrainers(): Result<List<TrainerProfile>>
}