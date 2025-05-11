package com.vurgun.skyfit.core.data.wellbeing.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class PostureType(val orientation: String) {
    Front("front"), Back("back"), Left("left"), Right("right")
}