package com.vurgun.skyfit.data.bodyanalysis.repository

import com.vurgun.skyfit.data.network.commonHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

class PostureAnalysisRepository {

    private val apiUrl = "https://qibpmgrdfnxaq4sylqmiwqu47q0yoent.lambda-url.us-west-2.on.aws/analyze"

    @Serializable
    data class PostureRequest(
        val image: String,
        val orientation: String // "left", "front", "back", "right"
    )

    @Serializable
    data class PostureAnalysisResponse(
        val success: Boolean,
        val data: Map<String, String>? = null,
        val message: String? = null
    )

    suspend fun analyzePosture(base64Image: String, orientation: String = "front"): PostureAnalysisResponse {
        val requestBody = PostureRequest(
            image = base64Image,
            orientation = orientation
        )

        val response = commonHttpClient.post(apiUrl) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(PostureRequest.serializer(), requestBody))
        }

        return Json.decodeFromString(PostureAnalysisResponse.serializer(), response.body())
    }
}
