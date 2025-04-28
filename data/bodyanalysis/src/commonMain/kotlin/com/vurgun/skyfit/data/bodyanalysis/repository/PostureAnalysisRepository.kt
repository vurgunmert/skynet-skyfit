package com.vurgun.skyfit.data.bodyanalysis.repository

import com.vurgun.skyfit.data.bodyanalysis.model.BackPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.model.FrontPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.model.LeftPostureResponse
import com.vurgun.skyfit.data.bodyanalysis.model.PostureAnalysisRequest
import com.vurgun.skyfit.data.bodyanalysis.model.PostureType
import com.vurgun.skyfit.data.bodyanalysis.model.RightPostureResponse
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

    private val apiUrl = "https://qibpmgrdfnxaq4sylqmiwqu47q0yoent.lambda-url.us-west-2.on.aws/analyze"

    private suspend fun requestAnalysis(
        byteArray: ByteArray,
        orientation: String
    ): JsonObject {
        val base64Image = byteArray.encodeBase64()
        val requestBody = PostureAnalysisRequest(
            image = base64Image,
            orientation = orientation
        )

        val response = commonHttpClient.post(apiUrl) {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(PostureAnalysisRequest.serializer(), requestBody))
        }

        val rawJson = response.body<String>()
        return Json.parseToJsonElement(rawJson).jsonObject
    }

    suspend fun analyzeFront(byteArray: ByteArray): FrontPostureResponse {
        val json = requestAnalysis(byteArray, PostureType.Front.orientation)
        return FrontPostureResponse.fromJsonObject(json)
    }

    suspend fun analyzeBack(byteArray: ByteArray): BackPostureResponse {
        val json = requestAnalysis(byteArray, PostureType.Back.orientation)
        return BackPostureResponse.fromJsonObject(json)
    }

    suspend fun analyzeLeft(byteArray: ByteArray): LeftPostureResponse {
        val json = requestAnalysis(byteArray, PostureType.Left.orientation)
        return LeftPostureResponse.fromJsonObject(json)
    }

    suspend fun analyzeRight(byteArray: ByteArray): RightPostureResponse {
        val json = requestAnalysis(byteArray, PostureType.Right.orientation)
        return RightPostureResponse.fromJsonObject(json)
    }
}
