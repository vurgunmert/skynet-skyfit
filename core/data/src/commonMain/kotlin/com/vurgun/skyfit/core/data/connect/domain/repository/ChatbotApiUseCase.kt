package com.vurgun.skyfit.core.data.connect.domain.repository

interface ChatbotApiUseCase {
    suspend fun queryChat(question: String): String
}