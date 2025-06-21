package com.vurgun.skyfit.core.data.v1.measurement.mapper

import com.vurgun.skyfit.core.data.v1.measurement.back.model.CreateMeasurementRequestDTO
import com.vurgun.skyfit.core.data.v1.measurement.back.model.MeasurementDTO
import com.vurgun.skyfit.core.data.v1.measurement.front.Measurement
import com.vurgun.skyfit.core.data.v1.measurement.front.MeasurementFormUiData
import com.vurgun.skyfit.core.data.v1.measurement.front.MeasurementType
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

fun MeasurementDTO.toDomain(): Measurement {
    return Measurement(
        type = MeasurementType.fromCode(typeCode) ?: error("Unknown typeCode: $typeCode"),
        value = value,
        unit = unit ?: MeasurementType.fromCode(typeCode)?.defaultUnit.orEmpty(),
        recordedAt = Instant.parse(recordedAt)
    )
}

fun Measurement.toCreateMeasurementRequestDTO(userId: String): CreateMeasurementRequestDTO {
    return CreateMeasurementRequestDTO(
        userId = userId,
        typeCode = type.code,
        value = value,
        unitOverride = unit,
        recordedAt = recordedAt.toString()
    )
}

fun MeasurementFormUiData.toDomainMeasurements(): List<Measurement> {
    val now = Clock.System.now()

    return listOfNotNull(
        waistHipRatio.toDoubleOrNull()?.let { Measurement(MeasurementType.WAIST_HIP_RATIO, it, "ratio", now) },
        sitAndReach.toDoubleOrNull()?.let { Measurement(MeasurementType.SIT_AND_REACH, it, "cm", now) },
        bmi.toDoubleOrNull()?.let { Measurement(MeasurementType.BMI, it, "bmi", now) },
        height.toDoubleOrNull()?.let { Measurement(MeasurementType.HEIGHT, it, "cm", now) },
        weight.toDoubleOrNull()?.let { Measurement(MeasurementType.WEIGHT, it, "kg", now) },
        shoulder.toDoubleOrNull()?.let { Measurement(MeasurementType.SHOULDER, it, "cm", now) },
        chest.toDoubleOrNull()?.let { Measurement(MeasurementType.CHEST, it, "cm", now) },
        waist.toDoubleOrNull()?.let { Measurement(MeasurementType.WAIST, it, "cm", now) },
        hip.toDoubleOrNull()?.let { Measurement(MeasurementType.HIP, it, "cm", now) },
        leftArm.toDoubleOrNull()?.let { Measurement(MeasurementType.LEFT_ARM, it, "cm", now) },
        rightArm.toDoubleOrNull()?.let { Measurement(MeasurementType.RIGHT_ARM, it, "cm", now) },
        leftLeg.toDoubleOrNull()?.let { Measurement(MeasurementType.LEFT_LEG, it, "cm", now) },
        rightLeg.toDoubleOrNull()?.let { Measurement(MeasurementType.RIGHT_LEG, it, "cm", now) }
    )
}
