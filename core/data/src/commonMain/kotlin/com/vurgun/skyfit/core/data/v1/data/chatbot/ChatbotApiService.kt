package com.vurgun.skyfit.core.data.v1.data.chatbot

import com.vurgun.skyfit.core.network.commonHttpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.io.IOException

class ChatbotApiService {

    private val apiUrl = "https://jjkebio6ufutyic7movsbppijy0mmppq.lambda-url.us-west-2.on.aws"

    suspend fun sendQuery(request: ChatbotQueryRequestDTO): ChatbotQueryResponseDTO {
        try {
            val response = commonHttpClient.post("$apiUrl/chat") {
                contentType(ContentType.Application.Json)
                setBody(request)

                timeout {
                    requestTimeoutMillis = 50_000
                    connectTimeoutMillis = 15_000
                    socketTimeoutMillis = 50_000
                }
            }.body<ChatbotQueryResponseDTO>()
            return response

        } catch (e: IOException) {
            throw ChatbotException.Network(e.message ?: "Network error")
        } catch (e: Exception) {
            throw ChatbotException.Unknown(e.message ?: "Unexpected error")
        }
    }

    suspend fun getSessionHistory(sessionId: String): List<ChatbotHistoryItemDTO> {
        try {
            val response = commonHttpClient.get("$apiUrl/history/$sessionId") {
                contentType(ContentType.Application.Json)
                timeout {
                    requestTimeoutMillis = 30_000
                    connectTimeoutMillis = 15_000
                    socketTimeoutMillis = 30_000
                }
            }.body<List<ChatbotHistoryItemDTO>>()
            return response

        } catch (e: IOException) {
            throw ChatbotException.Network(e.message ?: "Network error")
        } catch (e: Exception) {
            throw ChatbotException.Unknown(e.message ?: "Unexpected error")
        }
    }
}