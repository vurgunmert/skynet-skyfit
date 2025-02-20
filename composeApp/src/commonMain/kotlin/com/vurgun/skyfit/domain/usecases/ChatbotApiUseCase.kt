package com.vurgun.skyfit.domain.usecases

interface ChatbotApiUseCase {
    suspend fun queryChat(question: String): String
}