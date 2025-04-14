package com.vurgun.skyfit.data.bodyanalysis.model

import kotlinx.serialization.Serializable

@Serializable
data class PostureFinding(
    val detected: Boolean? = null,
    val explanation: String
)

@Serializable
data class PostureFindingString(
    val detected: String,
    val explanation: String
)


@Serializable
data class FrontPostureResponse(
    val genu_varum: PostureFinding,
    val left_patellar_tilt: PostureFinding,
    val right_patellar_tilt: PostureFinding,
    val right_shoulder_elevated: PostureFinding,
    val left_shoulder_elevated: PostureFinding,
    val right_cervical_tilt: PostureFinding,
    val left_cervical_tilt: PostureFinding,
    val left_pelvic_drop: PostureFinding,
    val right_pelvic_drop: PostureFinding,
    val shoulder_protraction: PostureFinding,
    val shoulder_tightness: PostureFindingString, // special case
    val pelvic_rotation: PostureFindingString     // special case
)

@Serializable
data class BackPostureResponse(
    val decreased_lordosis: PostureFinding,
    val decreased_kyphosis: PostureFinding
)

@Serializable
data class LeftPostureResponse(
    val cervical_rotation_left: PostureFinding
)

@Serializable
data class RightPostureResponse(
    val cervical_rotation_right: PostureFinding
)

