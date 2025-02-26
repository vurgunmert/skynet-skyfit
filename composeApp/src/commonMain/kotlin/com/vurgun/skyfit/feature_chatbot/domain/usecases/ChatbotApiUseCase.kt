package com.vurgun.skyfit.feature_chatbot.domain.usecases

interface ChatbotApiUseCase {
    suspend fun queryChat(question: String): String
}