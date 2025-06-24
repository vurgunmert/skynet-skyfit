package com.vurgun.skyfit.core.data.v1.data.measurement.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeasurementCategoryDTO(
    @SerialName("measurementId") val categoryId: Int,
    @SerialName("title") val categoryName: String,
)

@Serializable
data class AddMeasurementRequestDTO(
    @SerialName("measurementId") val categoryId: Int,
    @SerialName("value") val value: Int,
    @SerialName("unitId") val unitId: Int? = null,
)

@Serializable
data class UpdateMeasurementRequestDTO(
    @SerialName("userMeasurementsId") val measurementId: Int,
    @SerialName("value") val value: Int,
    @SerialName("unitId") val unitId: Int? = null,
)

@Serializable
data class DeleteMeasurementRequestDTO(
    @SerialName("userMeasurementsId") val measurementId: Int
)

@Serializable
data class GetMeasurementHistoryRequestDTO(
    @SerialName("measurementId") val categoryId: Int,
)

@Serializable
data class MeasurementDTO(
    @SerialName("userMeasurementsId") val measurementId: Int,
    @SerialName("value") val value: Int,
    @SerialName("unitId") val unitId: Int? = null,
    @SerialName("unitName") val unitName: String? = null,
    @SerialName("createdDate") val createdDate: String,
)
