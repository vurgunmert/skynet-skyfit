package com.vurgun.skyfit.core.data.v1.domain.measurement.repository

import com.vurgun.skyfit.core.data.v1.domain.measurement.model.Measurement
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.MeasurementCategory

interface MeasurementRepository {
    suspend fun getMeasurementCategories(): Result<List<MeasurementCategory>>
    suspend fun getLatestMeasurements(): Result<List<Measurement>>
    suspend fun getMeasurementHistory(measurementCategoryId: Int): Result<List<Measurement>>
    suspend fun addMeasurement(categoryId: Int, value: Int, unitId: Int? = null): Result<Unit>
    suspend fun updateMeasurement(measurementId: Int, value: Int, unitId: Int? = null): Result<Unit>
    suspend fun deleteMeasurement(measurementId: Int): Result<Unit>
}