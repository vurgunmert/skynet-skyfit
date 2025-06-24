package com.vurgun.skyfit.core.data.v1.data.measurement.repository

import com.vurgun.skyfit.core.data.storage.TokenManager
import com.vurgun.skyfit.core.data.v1.data.measurement.mapper.toDomainList
import com.vurgun.skyfit.core.data.v1.data.measurement.model.AddMeasurementRequestDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.model.DeleteMeasurementRequestDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.model.GetMeasurementHistoryRequestDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.model.UpdateMeasurementRequestDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.service.MeasurementApiService
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.Measurement
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.MeasurementCategory
import com.vurgun.skyfit.core.data.v1.domain.measurement.repository.MeasurementRepository
import com.vurgun.skyfit.core.network.DispatcherProvider
import com.vurgun.skyfit.core.network.utils.ioResult
import com.vurgun.skyfit.core.network.utils.mapOrThrow
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class MeasurementRepositoryImpl(
    private val apiService: MeasurementApiService,
    private val dispatchers: DispatcherProvider,
    private val tokenManager: TokenManager
) : MeasurementRepository {

    override suspend fun getMeasurementCategories() = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        apiService.getMeasurementCategories(token).mapOrThrow { it.toDomainList() }
    }

    override suspend fun getLatestMeasurements(): Result<List<Measurement>> = runCatching {
        coroutineScope {
            MeasurementCategory.entries
                .map { category ->
                    async {
                        getMeasurementHistory(category.id).getOrNull()?.firstOrNull()
                    }
                }
                .awaitAll()
                .filterNotNull()
        }
    }

    override suspend fun getMeasurementHistory(measurementCategoryId: Int) = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = GetMeasurementHistoryRequestDTO(measurementCategoryId)
        apiService.getMeasurementHistory(request, token).mapOrThrow { it.toDomainList(measurementCategoryId) }
    }

    override suspend fun addMeasurement(categoryId: Int, value: Int, unitId: Int?) = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = AddMeasurementRequestDTO(categoryId, value, unitId)
        apiService.addMeasurement(request, token).mapOrThrow { }
    }

    override suspend fun updateMeasurement(measurementId: Int, value: Int, unitId: Int?) = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = UpdateMeasurementRequestDTO(measurementId, value, unitId)
        apiService.updateMeasurement(request, token).mapOrThrow { }
    }

    override suspend fun deleteMeasurement(measurementId: Int) = ioResult(dispatchers) {
        val token = tokenManager.getTokenOrThrow()
        val request = DeleteMeasurementRequestDTO(measurementId)
        apiService.deleteMeasurement(request, token).mapOrThrow { }
    }
}