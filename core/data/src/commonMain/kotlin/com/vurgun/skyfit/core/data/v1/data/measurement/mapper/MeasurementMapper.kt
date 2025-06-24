package com.vurgun.skyfit.core.data.v1.data.measurement.mapper

import com.vurgun.skyfit.core.data.utility.parseServerToLocalDateTime
import com.vurgun.skyfit.core.data.v1.data.measurement.model.MeasurementCategoryDTO
import com.vurgun.skyfit.core.data.v1.data.measurement.model.MeasurementDTO
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.Measurement
import com.vurgun.skyfit.core.data.v1.domain.measurement.model.MeasurementCategory

internal fun MeasurementDTO.toDomain(categoryId: Int): Measurement {
    return Measurement(
        measurementId = measurementId,
        categoryId = categoryId,
        value = value,
        unitId = unitId,
        unitName = unitName,
        createdDate = createdDate.parseServerToLocalDateTime()
    )
}

internal fun List<MeasurementDTO>.toDomainList(categoryId: Int): List<Measurement> = map { it.toDomain(categoryId) }

internal fun MeasurementCategoryDTO.toDomainCategory(): MeasurementCategory? {
    return MeasurementCategory.from(categoryId)
}

internal fun List<MeasurementCategoryDTO>.toDomainList(): List<MeasurementCategory> {
    return mapNotNull { it.toDomainCategory() }
}