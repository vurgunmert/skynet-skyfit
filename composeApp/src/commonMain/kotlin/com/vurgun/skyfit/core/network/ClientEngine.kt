package com.vurgun.skyfit.core.network

import com.vurgun.skyfit.core.utils.getPlatform
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Platform-specific engine selection
expect fun httpClientEngine(): HttpClientEngineFactory<*>

val commonHttpClient = HttpClient(httpClientEngine()) {

    val baseUrl = "https://skyfit.mertbeta.xyz:8084/api/v1/"

    defaultRequest {
        url(baseUrl)
        contentType(ContentType.Application.Json)

        header("client-id", getPlatform().clientId)
        header("os-id", getPlatform().osId)
    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
}