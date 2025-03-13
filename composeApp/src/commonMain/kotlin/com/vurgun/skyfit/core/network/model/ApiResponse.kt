package com.vurgun.skyfit.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    @SerialName("status") val status: String,
    @SerialName("title") val title: String? = null,
    @SerialName("message") val message: String? = null,
    @SerialName("data") val data: T? = null,
    @SerialName("errorInputs") val errorInputs: List<String>? = emptyList(),
    @SerialName("logout") val logout: Int? = 0
)
