package com.vurgun.skyfit.core.data.v1.measurement.front

import kotlinx.datetime.Instant

data class Measurement(
    val type: MeasurementType,
    val value: Double,
    val unit: String,
    val recordedAt: Instant
)
