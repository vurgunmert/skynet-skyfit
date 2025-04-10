package com.vurgun.skyfit.data.messaging

interface ChatbotApiUseCase {
    suspend fun queryChat(question: String): String
}