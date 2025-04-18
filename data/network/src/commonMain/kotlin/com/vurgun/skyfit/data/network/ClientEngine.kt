package com.vurgun.skyfit.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.native.concurrent.ThreadLocal

const val BASE_HOST_URL = "http://skyfit.mertbeta.xyz" //TODO: SECURE URL
const val BASE_IMAGE_URL = "http://skyfit.mertbeta.xyz/" //TODO: SECURE URL
private const val FULL_BASE_URL = "${BASE_HOST_URL}:8084/api/v1/" //TODO: SECURE URL

expect fun httpClientEngine(): HttpClientEngine

@ThreadLocal
val commonHttpClient = HttpClient(httpClientEngine()) {

    val constants = getNetworkConstants()

    // Default Headers
    defaultRequest {
        contentType(ContentType.Application.Json)
        header("client-id", constants.clientId)
        header("os-id", constants.osId)
        url(FULL_BASE_URL)
    }

    // JSON Serialization
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
            prettyPrint = true
        })
    }

    // Logging Plugin
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println("🔍 Ktor Log: $message") // Replace with Timber or other logger if needed
            }
        }
    }
}
