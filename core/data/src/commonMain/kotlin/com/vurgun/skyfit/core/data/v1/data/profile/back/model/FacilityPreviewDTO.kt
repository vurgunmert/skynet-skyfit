package com.vurgun.skyfit.core.data.v1.profile.back.model

data class FacilityPreviewDTO(
    val id: String,
    val name: String,
    val address: String,
    val location: String,
    val imageUrlPath: String? = null
)
