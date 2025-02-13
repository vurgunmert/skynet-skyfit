package com.vurgun.skyfit.domain.usecase

interface ChatbotApiUseCase {
    suspend fun queryChat(question: String): String
}