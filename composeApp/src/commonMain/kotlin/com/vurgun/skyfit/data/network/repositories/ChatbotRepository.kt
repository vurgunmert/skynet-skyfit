package com.vurgun.skyfit.data.network.repositories

import com.vurgun.skyfit.data.network.api.commonHttpClient
import com.vurgun.skyfit.data.network.models.ChatbotRequest
import com.vurgun.skyfit.data.network.models.ChatbotResponse
import com.vurgun.skyfit.domain.usecase.ChatbotQueryUseCase
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ChatbotRepository : ChatbotQueryUseCase {

    //TODO: Secure Keys
    private val apiUrl = "https://ru1j9wqs.rcld.app/api/v1/prediction/9603f037-8395-4abf-8b87-a6c0f09fc96c"
    private val apiKey = "ktO5bzN1-D3zVg9pgxOCMd3llg1Fl78AuttQhXQry-M"

    override suspend fun queryChat(question: String): String {
        val response: ChatbotResponse = commonHttpClient.post(apiUrl) {
            contentType(ContentType.Application.Json)
            header("Authorization", "Bearer $apiKey")
            setBody(ChatbotRequest(question))
        }.body()
        return response.answer
    }
}