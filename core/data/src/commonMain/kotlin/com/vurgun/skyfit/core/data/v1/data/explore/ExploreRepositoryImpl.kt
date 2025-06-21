package com.vurgun.skyfit.core.data.v1.data.explore

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.explore.service.ExploreApiService
import com.vurgun.skyfit.core.data.v1.data.facility.mapper.FacilityDataMapper.toDomainFacilityProfiles
import com.vurgun.skyfit.core.data.v1.data.trainer.mapper.TrainerDataMapper.toDomainTrainerProfiles
import com.vurgun.skyfit.core.data.v1.domain.explore.ExploreRepository
import com.vurgun.skyfit.core.data.v1.domain.facility.model.FacilityProfile
import com.vurgun.skyfit.core.data.v1.domain.trainer.model.TrainerProfile
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow

class ExploreRepositoryImpl(
    private val apiService: ExploreApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : ExploreRepository {

    override suspend fun getAllFacilities(): Result<List<FacilityProfile>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getFacilities(token).mapOrThrow { it.toDomainFacilityProfiles() }
    }

    override suspend fun getAllTrainers(): Result<List<TrainerProfile>> = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getTrainers(token).mapOrThrow { it.toDomainTrainerProfiles() }
    }

}