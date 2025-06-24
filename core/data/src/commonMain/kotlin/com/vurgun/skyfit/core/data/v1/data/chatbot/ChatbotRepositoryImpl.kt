package com.vurgun.skyfit.core.data.v1.data.chatbot

import com.vurgun.skyfit.core.data.storage.ChatBotSessionStorage
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.data.utility.parseServerToLocalDateTime
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotMessage
import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotRepository
import com.vurgun.skyfit.core.network.commonHttpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.LocalDateTime
import kotlinx.io.IOException

class ChatbotRepositoryImpl(
    private val apiService: ChatbotApiService,
    private val sessionStorage: ChatBotSessionStorage
) : ChatbotRepository {

    override suspend fun sendQuery(message: ChatbotMessage): ChatbotMessage {
        try {
            val request = ChatbotQueryRequestDTO(message.content, message.sessionId)
            val response = apiService.sendQuery(request)
            val message =  ChatbotMessage(
                role = "ai",
                content = response.answer,
                sessionId = response.sessionId,
                createdAt = LocalDateTime.now()
            )
            sessionStorage.saveSession(message.sessionId)
            return message
        } catch (e: IOException) {
            throw ChatbotException.Network(e.message ?: "Network error")
        } catch (e: Exception) {
            throw ChatbotException.Unknown(e.message ?: "Unexpected error")
        }
    }

    override suspend fun getSessionHistory(sessionId: String): List<ChatbotMessage> {
        try {
            val response = apiService.getSessionHistory(sessionId)
            return response.map {
                ChatbotMessage(
                    role = it.role,
                    content = it.content,
                    sessionId = sessionId,
                    createdAt = it.createdAt.parseServerToLocalDateTime()
                )
            }

        } catch (e: IOException) {
            throw ChatbotException.Network(e.message ?: "Network error")
        } catch (e: Exception) {
            throw ChatbotException.Unknown(e.message ?: "Unexpected error")
        }
    }
    override suspend fun getLastSessions(take: Int): Map<String, List<ChatbotMessage>> {
        val result = mutableMapOf<String, List<ChatbotMessage>>()

        for (sessionId in sessionStorage.getSessionIds()) {
            if (result.size >= take) break

            val messages = try {
                getSessionHistory(sessionId)
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }

            if (messages.isNotEmpty()) {
                result[sessionId] = messages
            }
        }

        return result
    }

}

sealed class ChatbotException(message: String) : Exception(message) {
    class Network(message: String) : ChatbotException(message)
    class Unknown(message: String) : ChatbotException(message)
}