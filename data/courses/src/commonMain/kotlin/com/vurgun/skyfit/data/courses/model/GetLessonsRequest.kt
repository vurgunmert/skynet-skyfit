package com.vurgun.skyfit.data.courses.model

import kotlinx.serialization.Serializable

@Serializable
data class GetLessonsRequest(
    val gymId: Int,
    val startFilterDate: String, // datetime türünde gönderilecek
    val endFilterDate: String? = null
)

