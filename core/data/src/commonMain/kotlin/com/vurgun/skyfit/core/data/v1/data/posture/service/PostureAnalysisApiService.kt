package com.vurgun.skyfit.core.data.v1.data.posture.service

import com.vurgun.skyfit.core.data.v1.data.posture.model.PostureAnalyseRequestDTO
import com.vurgun.skyfit.core.network.commonHttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class PostureAnalysisApiService {

    private val apiUrl = "https://railwayposture-production.up.railway.app/analyze-posture"

    suspend fun sendPosture(
        byteArray: ByteArray,
        orientation: String
    ): JsonObject {
        val base64Image = byteArray.encodeBase64()
        val requestBody = PostureAnalyseRequestDTO(
            image = base64Image,
            orientation = orientation
        )

        val response = commonHttpClient.post(apiUrl) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(PostureAnalyseRequestDTO.serializer(), requestBody))
        }

        val rawJson = response.body<String>()
        return Json.parseToJsonElement(rawJson).jsonObject
    }
}
