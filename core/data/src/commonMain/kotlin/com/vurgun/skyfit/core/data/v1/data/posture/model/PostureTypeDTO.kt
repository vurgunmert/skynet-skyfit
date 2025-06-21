package com.vurgun.skyfit.core.data.v1.data.posture.model

import kotlinx.serialization.Serializable

@Serializable
enum class PostureTypeDTO(val orientation: String) {
    Front("front"), Back("back"), Left("left"), Right("right")
}