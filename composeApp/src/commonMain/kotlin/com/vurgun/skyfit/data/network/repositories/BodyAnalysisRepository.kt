package com.vurgun.skyfit.data.network.repositories

import com.vurgun.skyfit.data.network.api.commonHttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

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
