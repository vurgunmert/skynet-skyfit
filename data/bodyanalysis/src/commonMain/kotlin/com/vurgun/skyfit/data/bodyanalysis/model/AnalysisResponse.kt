package com.vurgun.skyfit.data.bodyanalysis.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class PostureFinding(
    val key: String,
    val detected: Boolean,
    val englishExplanation: String,
    val turkishExplanation: String,
)
//
//@Serializable
//data class FrontPostureResponse(
//    val genuVarum: PostureFinding,
//    val patellarLateralTiltRight: PostureFinding,
//    val patellarLateralTiltLeft: PostureFinding,
//    val shoulderElevationRight: PostureFinding,
//    val cervicalLateralTiltRight: PostureFinding,
//    val lateralSIASPelvicDropLeft: PostureFinding,
//    val shoulderProtraction: PostureFinding
//) {
//    companion object {
//        fun fromJsonObject(obj: JsonObject): FrontPostureResponse {
//            return FrontPostureResponse(
//                genuVarum = obj.extractFinding(frontPostureKeyMap.getValue("genuVarum")),
//                patellarLateralTiltRight = obj.extractFinding(frontPostureKeyMap.getValue("patellarLateralTiltRight")),
//                patellarLateralTiltLeft = obj.extractFinding(frontPostureKeyMap.getValue("patellarLateralTiltLeft")),
//                shoulderElevationRight = obj.extractFinding(frontPostureKeyMap.getValue("shoulderElevationRight")),
//                cervicalLateralTiltRight = obj.extractFinding(frontPostureKeyMap.getValue("cervicalLateralTiltRight")),
//                lateralSIASPelvicDropLeft = obj.extractFinding(frontPostureKeyMap.getValue("lateralSIASPelvicDropLeft")),
//                shoulderProtraction = obj.extractFinding(frontPostureKeyMap.getValue("shoulderProtraction"))
//            )
//        }
//    }
//}
//
//
//@Serializable
//data class BackPostureResponse(
//    val shoulderElevationRight: PostureFinding,
//    val pelvicRotationLeft: PostureFinding
//) {
//    companion object {
//        fun fromJsonObject(obj: JsonObject): BackPostureResponse {
//            return BackPostureResponse(
//                shoulderElevationRight = obj.extractFinding("Shoulder elevation (Right)"),
//                pelvicRotationLeft = obj.extractFinding("Pelvic rotation (Left)")
//            )
//        }
//    }
//}
//
//@Serializable
//data class LeftPostureResponse(
//    val cervicalRotationLeft: PostureFinding,
//    val hipExtensionRestriction: PostureFinding
//) {
//    companion object {
//        fun fromJsonObject(obj: JsonObject): LeftPostureResponse {
//            return LeftPostureResponse(
//                cervicalRotationLeft = obj.extractFinding("Cervical rotation (Left)"),
//                hipExtensionRestriction = obj.extractFinding("Hip extension restriction")
//            )
//        }
//    }
//}
//
//@Serializable
//data class RightPostureResponse(
//    val cervicalRotationRight: PostureFinding,
//    val hipExtensionRestriction: PostureFinding,
//    val decreasedLordosis: PostureFinding,
//    val decreasedKyphosis: PostureFinding,
//    val anteriorCervicalTilt: PostureFinding
//) {
//    companion object {
//        fun fromJsonObject(obj: JsonObject): RightPostureResponse {
//            return RightPostureResponse(
//                cervicalRotationRight = obj.extractFinding("Cervical rotation (Right)"),
//                hipExtensionRestriction = obj.extractFinding("Hip extension restriction"),
//                decreasedLordosis = obj.extractFinding("Decreased lordosis"),
//                decreasedKyphosis = obj.extractFinding("Decreased kyphosis"),
//                anteriorCervicalTilt = obj.extractFinding("Anterior cervical tilt")
//            )
//        }
//    }
//}

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
