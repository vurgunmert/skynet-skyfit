package com.vurgun.skyfit.core.data.v1.domain.posture.repository

import com.vurgun.skyfit.core.data.v1.data.posture.model.PostureAnalyseRequestDTO
import com.vurgun.skyfit.core.data.v1.data.posture.model.PostureTypeDTO
import com.vurgun.skyfit.core.data.v1.domain.posture.model.PostureFinding
import com.vurgun.skyfit.core.data.v1.domain.posture.model.toPostureFindingList
import com.vurgun.skyfit.core.network.commonHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.encodeBase64
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject

class PostureAnalysisRepository {

    private val apiUrl =
        "https://qibpmgrdfnxaq4sylqmiwqu47q0yoent.lambda-url.us-west-2.on.aws/analyze"

    private suspend fun requestAnalysis(
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

    suspend fun getPostureFindingList(
        imageBytes: ByteArray,
        postureType: PostureTypeDTO
    ): List<PostureFinding> {
        val json = requestAnalysis(imageBytes, postureType.orientation)
        return json.toPostureFindingList()
    }
}
