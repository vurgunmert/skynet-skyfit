package com.vurgun.skyfit.core.data.v1.domain.posture.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.*

@Serializable
data class PostureFinding(
    val key: String,
    val detected: Boolean,
    val englishExplanation: String,
    val turkishExplanation: String,
)

fun JsonObject.toPostureFindingList(): List<PostureFinding> {
    return this.entries.mapNotNull { (key, value) ->
        val obj = value.jsonObject

        val detected = obj["detected"]?.jsonPrimitive?.booleanOrNull
        val english = obj["English_explanation"]?.jsonPrimitive?.contentOrNull
        val turkish = obj["Turkish_explanation"]?.jsonPrimitive?.contentOrNull

        if (detected != null && english != null && turkish != null) {
            PostureFinding(
                key = key,
                detected = detected,
                englishExplanation = english,
                turkishExplanation = turkish
            )
        } else null // skip invalid entries
    }
}

