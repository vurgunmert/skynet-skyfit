package com.vurgun.skyfit.core.data.v1.domain.chatbot

import com.vurgun.skyfit.core.data.utility.humanizeAgo
import com.vurgun.skyfit.core.data.utility.now
import kotlinx.datetime.LocalDateTime

data class ChatbotMessage(
    val role: String = "user", // user - ai
    val content: String,
    val sessionId: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val isError: Boolean = false
) {
    val isUser: Boolean = role == "user"
    val isAi: Boolean = !isUser
    val timeAgo = createdAt.humanizeAgo()

    companion object {
        fun user(content: String, sessionId: String): ChatbotMessage {
            return ChatbotMessage(role = "user", content = content, sessionId = sessionId)
        }
        fun bot(content: String, sessionId: String, isError: Boolean = false): ChatbotMessage {
            return ChatbotMessage(role = "ai", content = content, sessionId = sessionId, isError = isError)
        }
    }
}