package com.vurgun.skyfit.data.bodyanalysis.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class PostureFinding(
    val detected: Boolean? = null,
    val englishExplanation: String,
    val turkishExplanation: String
)

@Serializable
data class FrontPostureResponse(
    val genuVarum: PostureFinding,
    val patellarLateralTiltRight: PostureFinding,
    val patellarLateralTiltLeft: PostureFinding,
    val shoulderElevationRight: PostureFinding,
    val cervicalLateralTiltRight: PostureFinding,
    val lateralSIASPelvicDropLeft: PostureFinding,
    val shoulderProtraction: PostureFinding
) {
    companion object {
        fun fromJsonObject(obj: JsonObject): FrontPostureResponse {
            return FrontPostureResponse(
                genuVarum = obj.extractFinding("Genu varum (Bow-legged deformity)"),
                patellarLateralTiltRight = obj.extractFinding("Patellar lateral tilt (Right)"),
                patellarLateralTiltLeft = obj.extractFinding("Patellar lateral tilt (Left)"),
                shoulderElevationRight = obj.extractFinding("Shoulder elevation (Right)"),
                cervicalLateralTiltRight = obj.extractFinding("Cervical lateral tilt (Right)"),
                lateralSIASPelvicDropLeft = obj.extractFinding("Lateral SIAS pelvic drop (Left)"),
                shoulderProtraction = obj.extractFinding("Shoulder protraction")
            )
        }
    }
}

@Serializable
data class BackPostureResponse(
    val shoulderElevationRight: PostureFinding,
    val pelvicRotationLeft: PostureFinding
) {
    companion object {
        fun fromJsonObject(obj: JsonObject): BackPostureResponse {
            return BackPostureResponse(
                shoulderElevationRight = obj.extractFinding("Shoulder elevation (Right)"),
                pelvicRotationLeft = obj.extractFinding("Pelvic rotation (Left)")
            )
        }
    }
}

@Serializable
data class LeftPostureResponse(
    val cervicalRotationLeft: PostureFinding,
    val hipExtensionRestriction: PostureFinding
) {
    companion object {
        fun fromJsonObject(obj: JsonObject): LeftPostureResponse {
            return LeftPostureResponse(
                cervicalRotationLeft = obj.extractFinding("Cervical rotation (Left)"),
                hipExtensionRestriction = obj.extractFinding("Hip extension restriction")
            )
        }
    }
}

@Serializable
data class RightPostureResponse(
    val cervicalRotationRight: PostureFinding,
    val hipExtensionRestriction: PostureFinding,
    val decreasedLordosis: PostureFinding,
    val decreasedKyphosis: PostureFinding,
    val anteriorCervicalTilt: PostureFinding
) {
    companion object {
        fun fromJsonObject(obj: JsonObject): RightPostureResponse {
            return RightPostureResponse(
                cervicalRotationRight = obj.extractFinding("Cervical rotation (Right)"),
                hipExtensionRestriction = obj.extractFinding("Hip extension restriction"),
                decreasedLordosis = obj.extractFinding("Decreased lordosis"),
                decreasedKyphosis = obj.extractFinding("Decreased kyphosis"),
                anteriorCervicalTilt = obj.extractFinding("Anterior cervical tilt")
            )
        }
    }
}

private fun JsonObject.extractFinding(key: String): PostureFinding {
    val item = this[key]?.jsonObject ?: error("Missing key: $key")
    return PostureFinding(
        detected = item["detected"]?.jsonPrimitive?.booleanOrNull,
        englishExplanation = item["English_explanation"]?.jsonPrimitive?.content.orEmpty(),
        turkishExplanation = item["Turkish_explanation"]?.jsonPrimitive?.content.orEmpty()
    )
}