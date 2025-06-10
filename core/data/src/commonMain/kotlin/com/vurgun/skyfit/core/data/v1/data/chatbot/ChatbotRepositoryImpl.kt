package com.vurgun.skyfit.core.data.v1.data.chatbot

import com.vurgun.skyfit.core.data.v1.domain.chatbot.ChatbotApiUseCase
import com.vurgun.skyfit.core.data.utility.appSessionId
import com.vurgun.skyfit.core.data.utility.now
import com.vurgun.skyfit.core.network.commonHttpClient
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.datetime.LocalDateTime
import kotlinx.io.IOException

class ChatbotRepositoryImpl : ChatbotApiUseCase {

    private val apiUrl = "https://qqzjme1d.rpcl.host/api/v1/prediction/a978b860-b4b6-4013-b80b-e016a04a2f5c"
    private val apiKey = "vvgrlZKNXJaBe0Y8QsSQXL6fTTur-zxtVoEBwG6IXEo"

    override suspend fun submitChatQuery(question: String): ChatbotResponseDTO {
        val requestStartTime = LocalDateTime.now()
        val sessionId = appSessionId

        val request = ChatbotRequestDTO(
            question = question,
            overrideConfig = OverrideConfigDTO(
                sessionId = sessionId
            )
        )

        try {
            val response = commonHttpClient.post(apiUrl) {
                contentType(ContentType.Application.Json)
                header("Authorization", "Bearer $apiKey")
                setBody(request)

                timeout {
                    requestTimeoutMillis = 30_000
                    connectTimeoutMillis = 15_000
                    socketTimeoutMillis = 30_000
                }
            }.body<ChatbotResponseDTO>()

            val requestEndTime = LocalDateTime.now()

            response.copy(
                startTime = requestStartTime,
                endTime = requestEndTime
            )
            return response

        } catch (e: IOException) {
            throw ChatbotException.Network(e.message ?: "Network error")
        } catch (e: Exception) {
            throw ChatbotException.Unknown(e.message ?: "Unexpected error")
        }
    }

}

sealed class ChatbotException(message: String) : Exception(message) {
    class Network(message: String) : ChatbotException(message)
    class Unknown(message: String) : ChatbotException(message)
}