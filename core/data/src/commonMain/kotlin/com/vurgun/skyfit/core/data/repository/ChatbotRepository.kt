package com.vurgun.skyfit.core.data.repository

import com.vurgun.skyfit.core.data.domain.repository.ChatbotApiUseCase
import com.vurgun.skyfit.core.data.model.ChatbotRequest
import com.vurgun.skyfit.core.data.model.ChatbotResponse
import com.vurgun.skyfit.core.network.commonHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ChatbotRepository : ChatbotApiUseCase {

    //TODO: Secure Keys
    private val apiUrl = "https://bgmh4dsb.rpcld.net/api/v1/prediction/d50d5475-79ac-4e41-8fc5-89f5315b64fe"
    private val apiKey = "mB6vUWOdnUp6Lg9JwpQXMrwrQh6bIfIzydf-tgeygvY"

    override suspend fun queryChat(question: String): String {
        val response: ChatbotResponse = commonHttpClient.post(apiUrl) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $apiKey")
            setBody(ChatbotRequest(question))
        }.body()
        return response.text.toString()
    }
}