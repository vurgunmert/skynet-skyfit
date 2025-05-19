package com.vurgun.skyfit.core.data.connect.domain.repository

import com.vurgun.skyfit.core.data.connect.data.model.ChatbotResponse

interface ChatbotApiUseCase {
    suspend fun submitChatQuery(question: String): ChatbotResponse
}