package com.vurgun.skyfit.core.data.v1.data.chatbot

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatbotQueryRequestDTO(
    val question: String,
    val sessionId: String
)

@Serializable
data class ChatbotQueryResponseDTO(
    val answer: String,
    val sessionId: String,
)


@Serializable
data class ChatbotHistoryItemDTO(
    val role: String,
    val content: String,
    @SerialName("created_at") val createdAt: String
)