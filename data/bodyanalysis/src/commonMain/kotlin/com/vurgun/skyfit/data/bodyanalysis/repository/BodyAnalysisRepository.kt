package com.vurgun.skyfit.data.bodyanalysis.repository

import com.vurgun.skyfit.core.network.commonHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
//import kotlinx.serialization.json.Json

class BodyAnalysisRepository {

    // TODO: Secure Keys
    private val apiUrl = "https://detect.roboflow.com/s-lkmds/1"
    private val apiKey = "mm91UfMfBQu9ZprhPJd4"

    suspend fun sendBase64EncodedImage(image: String): String {
        val response: String = commonHttpClient.post(apiUrl) {
            url {
                parameters.append("api_key", apiKey)
            }
            contentType(ContentType.Application.FormUrlEncoded)
            header("Content-Type", "application/x-www-form-urlencoded")
            setBody("data=$image") // API'nin istediği şekilde gönderiyoruz
        }.body()
        return response
    }
}


class BodyTypeAnalysisRepository() {

    private val apiUrl = "https://detect.roboflow.com/infer/workflows/skyf/custom-workflow"
    private val apiKey = "mm91UfMfBQu9ZprhPJd4"

    @Serializable
    data class RequestPayload(
        val api_key: String,
        val inputs: Map<String, ImageInput>
    )

    @Serializable
    data class ImageInput(
        val type: String,
        val value: String
    )

    @Serializable
    data class BodyTypeAnalysisResponse(
        val outputs: List<Output>,
        @SerialName("profiler_trace")
        val profilerTrace: List<String?>
    )

    @Serializable
    data class Output(
        val predictions: Predictions
    )

    @Serializable
    data class Predictions(
        @SerialName("inference_id")
        val inferenceId: String,
        val time: Double,
        val image: Image,
        val predictions: List<Prediction>,
        val top: String,
        val confidence: Double,
        @SerialName("prediction_type")
        val predictionType: String,
        @SerialName("parent_id")
        val parentId: String,
        @SerialName("root_parent_id")
        val rootParentId: String
    )

    @Serializable
    data class Image(
        val width: Long,
        val height: Long
    )

    @Serializable
    data class Prediction(
        @SerialName("class")
        val classField: String,
        @SerialName("class_id")
        val classId: Long,
        val confidence: Double
    )

//    suspend fun sendImageUrl(imageUrl: String): BodyTypeAnalysisResponse {
//        val requestBody = RequestPayload(
//            api_key = apiKey,
//            inputs = mapOf("image" to ImageInput(type = "url", value = imageUrl))
//        )
//
//        val response: HttpResponse = commonHttpClient.post(apiUrl) {
//            contentType(ContentType.Application.Json)
//            setBody(Json.encodeToString(requestBody))
//        }
//
//        return Json.decodeFromString(response.body())
//    }
}