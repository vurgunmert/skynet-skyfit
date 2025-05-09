package com.vurgun.skyfit.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MemberDto(
    val userId: Int,
    @SerialName("profilePhoto") val profilePhotoPath: String? = null,
    val username: String,
    val name: String,
    val surname: String
)