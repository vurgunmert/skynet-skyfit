package com.vurgun.skyfit.core.data.v1.domain.chatbot

import com.vurgun.skyfit.core.data.v1.data.chatbot.ChatbotResponseDTO

interface ChatbotApiUseCase {
    suspend fun submitChatQuery(question: String): ChatbotResponseDTO
}