package com.vurgun.skyfit.core.data.v1.domain.chatbot

interface ChatbotRepository {
    suspend fun sendQuery(message: ChatbotMessage): ChatbotMessage
    suspend fun getSessionHistory(sessionId: String): List<ChatbotMessage>
    suspend fun getLastSessions(take: Int = 3): Map<String, List<ChatbotMessage>>
}