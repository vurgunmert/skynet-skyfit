package com.vurgun.skyfit.core.data.connect.data.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

//@Serializable
//data class ChatbotRequest(val question: String)

@Serializable
data class ChatbotResponse(
    val text: String? = null,
    val question: String? = null,
    val chatId: String? = null,
    val chatMessageId: String? = null,
    val isStreamValid: Boolean? = null,
    val sessionId: String? = null,
    val memoryType: String? = null,
    val startTime: LocalDateTime? = null,
    val endTime: LocalDateTime? = null,
)


@Serializable
data class ChatbotRequest(
    val question: String,
    val overrideConfig: OverrideConfig? = null
)

@Serializable
data class OverrideConfig(
    val sessionId: String
)
