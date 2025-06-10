package com.vurgun.skyfit.core.data.v1.data.chatbot

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

//@Serializable
//data class ChatbotRequest(val question: String)

@Serializable
data class ChatbotResponseDTO(
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
data class ChatbotRequestDTO(
    val question: String,
    val overrideConfig: OverrideConfigDTO? = null
)

@Serializable
data class OverrideConfigDTO(
    val sessionId: String
)
