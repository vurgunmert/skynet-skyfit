package com.vurgun.skyfit.data.network.models

import kotlinx.serialization.Serializable

@Serializable
data class ChatbotRequest(val question: String)

@Serializable
data class ChatbotResponse(
    val text: String? = null,
    val question: String? = null,
    val chatId: String? = null,
    val chatMessageId: String? = null,
    val isStreamValid: Boolean? = null,
    val sessionId: String? = null,
    val memoryType: String? = null
)

