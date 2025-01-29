package com.vurgun.skyfit.data.network.models

import kotlinx.serialization.Serializable

@Serializable
enum class BookingStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    COMPLETED,
    ABSENT
}