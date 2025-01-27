package com.vurgun.skyfit.domain.usecase

interface ChatbotQueryUseCase {
    suspend fun queryChat(question: String): String
}