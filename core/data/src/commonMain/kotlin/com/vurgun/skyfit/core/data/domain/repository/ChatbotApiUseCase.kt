package com.vurgun.skyfit.core.data.domain.repository

interface ChatbotApiUseCase {
    suspend fun queryChat(question: String): String
}